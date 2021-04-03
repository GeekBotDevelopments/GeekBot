package bot.modules.octopi.commands;

import bot.GeekBot;
import bot.modules.discord.Command;
import bot.modules.octopi.PrinterEnum;
import bot.modules.octopi.models.api.job.PrintJobResponse;
import com.google.common.collect.ImmutableList;
import com.mashape.unirest.request.GetRequest;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.channel.MessageChannel;
import reactor.core.publisher.Mono;

public class CommandPrinterJob extends Command
{
    private static final String URL_JOB_API = "job";

    private static final int PRINTER_INDEX = 0;

    public CommandPrinterJob()
    {
        super("job");
    }

    private GetRequest newJobAPICall(PrinterEnum printerEnum)
    {
        return printerEnum.createApiGetRequest(URL_JOB_API);
    }

    @Override
    public Mono<Message> handle(Message message, MessageChannel channel, ImmutableList<String> args)
    {
        if (!args.isEmpty())
        {
            //TODO version API call as the data model changed since last iteration of this command
            return PrinterCommandHelpers.printerCommand(message, channel, args, PRINTER_INDEX,
                    printer -> PrinterCommandHelpers.handleApiCall(message, channel, printer,
                            //API call
                            () -> newJobAPICall(printer),
                            //API handler
                            (response) -> {
                                final PrintJobResponse jobState = GeekBot.GSON.fromJson(response, PrintJobResponse.class);

                                return channel.createMessage(String.format(
                                        "Printer `%s` is currently printing file `%s`"
                                                + "%n progress is %.2f"
                                                + "%n estimated time remaining `%s/%s`",
                                        printer.getName(), jobState.getJob().getFile().getPath(),
                                        jobState.getProgress().getCompletion(),
                                        jobState.getProgress().getPrintTimeLeft(), jobState.getProgress().getPrintTime()
                                ));
                            }
                    )
            ).onErrorResume(err -> PrinterCommandHelpers.genericError(channel, err));
        }
        return channel.createMessage("Unknown command `" + message.getContent() + "`");
    }
}
