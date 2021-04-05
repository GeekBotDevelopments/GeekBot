package bot.modules.octopi.commands;

import bot.GeekBot;
import bot.modules.configs.ChatOutputConfig;
import bot.modules.octopi.PrinterEnum;
import com.google.common.collect.ImmutableList;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.HttpRequest;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.channel.MessageChannel;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.http.conn.ConnectTimeoutException;
import reactor.core.publisher.Mono;

import java.net.HttpURLConnection;
import java.util.Arrays;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Created by Robin Seifert on 4/3/2021.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PrinterCommandHelpers
{
    /**
     * Finds the printer matching the given name
     *
     * @param name of the printer, case insensitive
     * @return printer found as an optional
     */
    public static Optional<PrinterEnum> findPrinter(String name)
    {
        return Arrays.stream(PrinterEnum.values())
                .filter(p -> p.getName().equalsIgnoreCase(name))
                .findFirst();
    }

    /**
     * Runs a command on a printer if found at the argument index provided. If not found feedback will be provided
     * to the user.
     *
     * @param message  posted by the user
     * @param channel  of the message posted
     * @param args     created from the message
     * @param argIndex index where the printer name should exist
     * @param function to call if a printer is found by the name
     * @return message to post back to the channel
     */
    public static Mono<Message> printerCommand(final Message message, final MessageChannel channel,
                                               final ImmutableList<String> args, final int argIndex,
                                               final Function<PrinterEnum, Mono<Message>> function
    )
    {
        //Get printer
        final String printerString = args.get(argIndex);
        final Optional<PrinterEnum> printerOptional = findPrinter(printerString);

        //Give feedback if not found
        if (!printerOptional.isPresent())
        {
            return channel.createMessage("No printer found by name `" + printerString + "` while running command `" + message.getContent() + "`");
        }

        return function.apply(printerOptional.get());
    }

    /**
     * Handles common errors generated while making REST endpoint calls for OctoPrint API
     * <p>
     * Will generate an error log in {@link GeekBot#MAIN_LOG} so all errors are tracked.
     *
     * @param message     posted by the user
     * @param channel     the message was posted inside
     * @param printerName of the printer the user tried to access
     * @param error       produced, often this is a {@link UnirestException} but is made generate for more flexability
     * @return message to pass back to the user for feedback
     */
    public static Mono<Message> handleConnectionError(final Message message, final MessageChannel channel,
                                                      final String printerName, final Exception error)
    {

        GeekBot.MAIN_LOG.error(String.format("Error running command `%s` on printer `%s`", message.getContent(), printerName), error);

        if (error instanceof ConnectTimeoutException || error.getCause() instanceof ConnectTimeoutException)
        {
            return channel.createMessage(String.format("Connection timed out while connecting to printer `%s`", printerName));
        }
        return channel.createMessage(String.format("Unexpected error while running command `%s`, error:", message.getContent()));
    }

    /**
     * Handles API calls for octoprint API
     *
     * @param message         posted by the user
     * @param channel         the message was posted inside
     * @param printer         being accessed by the command
     * @param requestSupplier providing the request to invoke
     * @param httpOkFunction  to call if the request returns 200-ok
     * @return message created from the function or any error handling
     */
    public static Mono<Message> handleApiCall(final Message message, final MessageChannel channel, PrinterEnum printer,
                                              Supplier<HttpRequest> requestSupplier, Function<String, Mono<Message>> httpOkFunction)
    {
        try
        {
            final HttpResponse<String> request = requestSupplier.get().asString();

            if (request.getStatus() == HttpURLConnection.HTTP_OK)
            {
                final String response = request.getBody();
                if (response == null)
                {
                    return PrinterCommandHelpers.emptyResponseBodyMessage(channel, printer, requestSupplier.get().getUrl());
                }

                return httpOkFunction.apply(response);
            }
            return channel.createMessage(String.format("State endpoint for printer `%s` responded with status code `%s`", printer.getName(), request.getStatusText()));
        }
        catch (UnirestException e)
        {
            return PrinterCommandHelpers.handleConnectionError(message, channel, printer.getName(), e);
        }
    }

    /**
     * Generic endpoint empty response body message to the user
     *
     * @param channel to output the message inside
     * @param printer being accessed
     * @return message to output
     */
    public static Mono<Message> emptyResponseBodyMessage(final MessageChannel channel, final PrinterEnum printer, final String endpoint)
    {
        //TODO add stronger linting or a pass through of just the API url
        final String lintedEndpoint = endpoint.split("\\?")[0].replaceFirst(printer.getUrl(), "");
        return channel.createMessage(String.format("%s Endpoint `%s` on printer `%s` responded with no body text%n%n" +
                        //TODO convert info into a URL link to wiki page, url should say `help` and add a command to get endpoint(3d printer) owner
                        ":pushpin: **Info:** Most commands require data contained in the body text to work... this could be a bug or broken endpoint. Check with the endpoint owner before reporting issues to the developer! %nBot Issues:<%s>",
                ChatOutputConfig.ERROR_PREFIX,
                lintedEndpoint, printer.getName(),
                GeekBot.ISSUES_REPORTING_URL));
    }

    /**
     * Generic error output to channel
     *
     * @param channel   to output the error message
     * @param throwable error thrown
     * @return message to generate
     */
    public static Mono<Message> genericError(final MessageChannel channel, final Throwable throwable)
    {
        return channel.createMessage(String.format("%s %s", ChatOutputConfig.ERROR_PREFIX, throwable.getMessage()));
    }
}
