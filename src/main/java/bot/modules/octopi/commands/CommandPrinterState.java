package bot.modules.octopi.commands;

import bot.GeekBot;
import bot.modules.discord.Command;
import bot.modules.octopi.OctopiModule;
import bot.modules.octopi.PrinterEnum;
import bot.modules.octopi.models.api.state.PrinterStateResponse;
import bot.modules.octopi.models.api.state.TemperatureStateValue;
import com.google.common.collect.ImmutableList;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.GetRequest;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.channel.MessageChannel;
import org.apache.http.conn.ConnectTimeoutException;
import reactor.core.publisher.Mono;

import java.net.HttpURLConnection;

public class CommandPrinterState extends Command
{
    public CommandPrinterState()
    {
        super("state");
    }

    private GetRequest newPrinterAPICall(PrinterEnum printerEnum)
    {
        return printerEnum.createApiGetRequest("printer");
    }

    @Override
    public Mono<Message> handle(final Message message, final MessageChannel channel, final ImmutableList<String> args)
    {
        if (!args.isEmpty())
        {
            return OctopiModule.printerCommand(message, channel, args, 0,
                    printer -> {
                        try
                        {
                            final HttpResponse<String> request = newPrinterAPICall(printer).asString();

                            if (request.getStatus() == HttpURLConnection.HTTP_OK)
                            {
                                final String response = request.getBody();
                                if (response == null)
                                {
                                    return channel.createMessage("Connection timed out while connecting to printer `" + printer.getName() + "`");
                                }

                                final PrinterStateResponse printerState = GeekBot.GSON.fromJson(request.getBody(), PrinterStateResponse.class);
                                final TemperatureStateValue tool0 = printerState.getTemperature().getTool0();
                                final TemperatureStateValue bed = printerState.getTemperature().getBed();

                                //TODO support more than 1 tool head
                                return channel.createMessage(
                                        String.format(
                                                "Printer `%s` is  current `%s`"
                                                        + "%n Tool[0]: %s/%s + %s"
                                                        + "%n Bed[0]: %s/%s + %s",
                                                printer.getName(), printerState.getState().getText(),
                                                tool0.getActual(), tool0.getTarget(), tool0.getOffset(),
                                                bed.getActual(), bed.getTarget(), bed.getOffset()
                                        )
                                );
                            }
                            return channel.createMessage(String.format("Version endpoint for printer `%s` responded with status code `%s`", printer.getName(), request.getStatusText()));
                        }
                        catch (UnirestException e)
                        {
                            GeekBot.MAIN_LOG.error(String.format("Failed to get version for printer `%s`", printer), e);
                            if (e.getCause() instanceof ConnectTimeoutException)
                            {
                                return channel.createMessage("Connection timed out while connecting to printer `" + printer.getName() + "`");
                            }
                            return channel.createMessage("Unexpected error while running command `" + message.getContent() + "`");
                        }
                    }
            ).onErrorResume(err -> channel.createMessage("Error: " + err.getMessage()));
        }
        return channel.createMessage("Unknown command `" + message.getContent() + "`");
    }
}
