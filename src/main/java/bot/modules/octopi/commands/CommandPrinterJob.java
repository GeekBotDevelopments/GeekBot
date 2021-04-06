package bot.modules.octopi.commands;

import bot.GeekBot;
import bot.helpers.TimeDisplayHelpers;
import bot.modules.discord.Command;
import bot.modules.octopi.PrinterEnum;
import bot.modules.octopi.api.OctoprintEndpoints;
import bot.modules.octopi.api.models.api.job.PrinterJobProgress;
import bot.modules.octopi.api.models.api.job.PrinterJobResponse;
import com.google.common.collect.ImmutableList;
import com.mashape.unirest.http.HttpResponse;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.channel.MessageChannel;
import reactor.core.publisher.Mono;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CommandPrinterJob extends Command
{
    private static final int PRINTER_INDEX = 0;

    public CommandPrinterJob()
    {
        super("job");
    }

    @Override
    public Mono<Message> handle(Message message, MessageChannel channel, ImmutableList<String> args)
    {
        if (args.size() == 1)
        {
            //TODO version API call as the data model changed since last iteration of this command
            return PrinterCommandHelpers.printerCommand(message, channel, args, PRINTER_INDEX,
                    printer -> PrinterCommandHelpers.handleApiCall(message, channel, printer,
                            //API call
                            () -> OctoprintEndpoints.newJobCall(printer.getPrinter()),
                            //API handler
                            (response) -> {
                                final PrinterJobResponse jobState = GeekBot.GSON.fromJson(response, PrinterJobResponse.class);
                                final String state = jobState.getState();

                                //Offline (Error: No more candidates to test, and no working port/baudrate combination detected.)
                                if (state.toLowerCase().startsWith("offline"))
                                {
                                    return channel.createMessage(String.format("Printer `%s` is currently offline", printer.getName()));
                                }
                                else if ("error".equalsIgnoreCase(state))
                                {
                                    return channel.createMessage(String.format("Printer `%s` is currently in an error state", printer.getName()));
                                }
                                else if ("operational".equalsIgnoreCase(state) && jobState.getJob().getFile().getName() == null)
                                {
                                    return channel.createMessage(String.format("Printer `%s` is operational and waiting for a file", printer.getName()));
                                }
                                else if ("operational".equalsIgnoreCase(state))
                                {
                                    return channel.createMessage(String.format("Printer `%s` is operational and waiting to start file `%s`", printer.getName(), jobState.getJob().getFile().getPath()));
                                }

                                return channel.createMessage(String.format(
                                        "Printer `%s` is currently %s file `%s`"
                                                + "%n progress is `%.2f%%`"
                                                + "%n estimated time remaining `%s`",
                                        printer.getName(), state.toLowerCase(), jobState.getJob().getFile().getPath(),
                                        jobState.getProgress().getCompletion(),
                                        TimeDisplayHelpers.convertSecondsToDisplay(Optional.ofNullable(jobState.getProgress()).orElseGet(PrinterJobProgress::new).getPrintTimeLeft())
                                ));
                            }
                    )
            );
        }
        else if (args.isEmpty())
        {
            return channel.createMessage(Stream.of(PrinterEnum.values()).map(printer -> {
                try
                {
                    HttpResponse<String> response = OctoprintEndpoints.newJobCall(printer.getPrinter()).asString();
                    final PrinterJobResponse jobState = GeekBot.GSON.fromJson(response.getBody(), PrinterJobResponse.class);
                    final String state = jobState.getState();

                    //Offline (Error: No more candidates to test, and no working port/baudrate combination detected.)
                    if (state.toLowerCase().startsWith("offline"))
                    {
                        return String.format("`%s`: Offline", printer.getName());
                    }
                    else if ("operational".equalsIgnoreCase(state) && jobState.getJob().getFile().getName() == null)
                    {
                        return String.format("`%s`: Ready for file", printer.getName());
                    }
                    else if ("operational".equalsIgnoreCase(state))
                    {
                        return String.format("`%s`: Ready to start", printer.getName());
                    }
                    else if (!"printing".equalsIgnoreCase(state))
                    {
                        return String.format("`%s`: %s", printer.getName(), state);
                    }
                    return String.format("`%s`: %s done printing file `%s`", printer.getName(), jobState.getProgress().getCompletion(), jobState.getJob().getFile().getPath());
                }
                catch (Exception e)
                {
                    GeekBot.MAIN_LOG.error("Failed while making call to get printer job data for printer " + printer, e);
                    return "Error: " + e.getMessage();
                }
            }).collect(Collectors.joining("\n")));
        }
        return channel.createMessage("Unknown command `" + message.getContent() + "`");
    }


}
