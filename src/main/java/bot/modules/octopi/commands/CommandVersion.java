package bot.modules.octopi.commands;

import bot.GeekBot;
import bot.modules.discord.Command;
import bot.modules.octopi.OctopiModule;
import bot.modules.octopi.PrinterEnum;
import bot.modules.octopi.models.api.PrinterVersion;
import com.google.common.collect.ImmutableList;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.request.GetRequest;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.channel.MessageChannel;
import org.apache.http.conn.ConnectTimeoutException;
import reactor.core.publisher.Mono;

import java.net.HttpURLConnection;
import java.util.AbstractMap;
import java.util.Arrays;
import java.util.Optional;

/**
 * Command to interact with OctoPrint version endpoint
 *
 * @see <a href="https://docs.octoprint.org/en/master/api/version.html">OctoPrint Version API</a>
 * <p>
 * Created by Robin Seifert on 3/31/2021.
 */
public class CommandVersion extends Command
{
    private static final String SUB_COMMAND_SERVER = "server";
    private static final String SUB_COMMAND_API = "api";

    public CommandVersion()
    {
        super("version");
    }

    private GetRequest newVersionAPICall(PrinterEnum printerEnum)
    {
        return printerEnum.createApiGetRequest("version");
    }

    @Override
    public Mono<Message> handle(Message message, MessageChannel channel, ImmutableList<String> args)
    {
        if (!args.isEmpty() && args.size() <= 2)
        {
            final String subCommand = args.size() == 1 ? SUB_COMMAND_SERVER : args.get(0);
            final String printerString = args.size() == 1 ? args.get(0) : args.get(1);

            final Optional<PrinterEnum> printerOptional = OctopiModule.findPrinter(printerString);
            if (!printerOptional.isPresent())
            {
                return channel.createMessage("No printer found by name `" + printerString + "` while running command `" + message.getContent() + "`");
            }

            final PrinterEnum printer = printerOptional.get();

            try
            {
                //TODO spawn thread to handle waiting on REST endpoint response?
                final HttpResponse<String> request = newVersionAPICall(printer).asString();

                if (request.getStatus() == HttpURLConnection.HTTP_OK)
                {
                    final String response = request.getBody();
                    if (response == null)
                    {
                        return channel.createMessage("Connection timed out while connecting to printer `" + printer.getName() + "`");
                    }

                    final PrinterVersion printerVersion = GeekBot.GSON.fromJson(request.getBody(), PrinterVersion.class);

                    //Match command
                    switch (subCommand)
                    {
                        case SUB_COMMAND_SERVER:
                            return channel.createMessage("Printer `" + printer.getName() + "` is running OctoPrint server version `" + printerVersion.getServer() + "` ");
                        case SUB_COMMAND_API:
                            return channel.createMessage("Printer `" + printer.getName() + "` is running OctoPrint api version `" + printerVersion.getApi() + "` ");
                        default:
                            return channel.createMessage("Unknown command `" + message.getContent() + "`");
                    }
                }
                return channel.createMessage(String.format("Version endpoint for printer `%s` responded with status code `%s`", printer.getName(), request.getStatusText()));
            }
            catch (Exception e)
            {
                GeekBot.MAIN_LOG.error(String.format("Failed to get version for printer `%s`", printer), e);
                if (e.getCause() instanceof ConnectTimeoutException)
                {
                    return channel.createMessage("Connection timed out while connecting to printer `" + printer.getName() + "`");
                }
                return channel.createMessage("Unexpected error while running command `" + message.getContent() + "`");
            }
        }
        else if (args.isEmpty())
        {
            return channel.createEmbed(embedCreateSpec -> {
                Arrays.stream(PrinterEnum.values())
                        .map(printerEnum -> {
                            try
                            {
                                final HttpResponse<String> request = newVersionAPICall(printerEnum).asString();

                                if (request.getStatus() == HttpURLConnection.HTTP_OK)
                                {
                                    final String response = request.getBody();
                                    if (response == null)
                                    {
                                        return new AbstractMap.SimpleEntry(printerEnum.getName(), "nil");
                                    }

                                    final PrinterVersion printerVersion = GeekBot.GSON.fromJson(request.getBody(), PrinterVersion.class);

                                    return new AbstractMap.SimpleEntry(printerEnum.getName(), printerVersion.getText());
                                }
                                return new AbstractMap.SimpleEntry(printerEnum.getName(), request.getStatusText());
                            }
                            catch (Exception e)
                            {
                                GeekBot.MAIN_LOG.error(String.format("Failed to get version for printer `%s`", printerEnum), e);
                                if (e.getCause() instanceof ConnectTimeoutException)
                                {
                                    return new AbstractMap.SimpleEntry(printerEnum.getName(), "Connection timed out");
                                }
                                return new AbstractMap.SimpleEntry(printerEnum.getName(), "Error:" + e.getMessage());
                            }
                        })
                        .forEach(entry -> embedCreateSpec.addField((String) entry.getKey(), (String) entry.getValue(), false));
            });
        }
        return channel.createMessage("Unknown command `" + message.getContent() + "`");
    }
}
