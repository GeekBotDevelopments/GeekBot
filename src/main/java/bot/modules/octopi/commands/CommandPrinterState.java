package bot.modules.octopi.commands;

import bot.GeekBot;
import bot.modules.discord.Command;
import bot.modules.octopi.api.OctoprintEndpoints;
import bot.modules.octopi.api.models.api.state.PrinterStateResponse;
import bot.modules.octopi.api.models.api.state.TemperatureStateValue;
import com.google.common.collect.ImmutableList;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.channel.MessageChannel;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

public class CommandPrinterState extends Command
{
    private static final String URL_QUERY_HISTORY = "history";
    private static final String URL_QUERY_LIMIT = "limit";

    private static final String USER_ARG_HISTORY = "history";

    private static final int PRINTER_INDEX = 0;
    private static final int HISTORY_INDEX = 1;
    private static final int LIMIT_INDEX = 2;

    public CommandPrinterState()
    {
        super("state");
    }

    @Override
    public Mono<Message> handle(final Message message, final MessageChannel channel, final ImmutableList<String> args)
    {
        if (!args.isEmpty())
        {
            return PrinterCommandHelpers.printerCommand(message, channel, args, PRINTER_INDEX,
                    printer -> {

                        //Collect query args
                        final Map<String, Object> parameters = new HashMap<>();
                        parameters.put(URL_QUERY_HISTORY, args.size() > 1 && USER_ARG_HISTORY.equalsIgnoreCase(args.get(HISTORY_INDEX)));
                        if (args.size() > 2)
                        {
                            parameters.put(URL_QUERY_LIMIT, Integer.getInteger(args.get(LIMIT_INDEX)));
                        }

                        return PrinterCommandHelpers.handleApiCall(message, channel, printer,
                                //API call
                                () -> OctoprintEndpoints.newPrinterCall(printer.getPrinter()).queryString(parameters),
                                //API handler
                                (response) -> {
                                    final PrinterStateResponse printerState = GeekBot.GSON.fromJson(response, PrinterStateResponse.class);
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
                        );
                    }
            ).onErrorResume(err -> PrinterCommandHelpers.genericError(channel, err));
        }
        //TODO if no args are provided output all printers
        return channel.createMessage("Unknown command `" + message.getContent() + "`");
    }
}
