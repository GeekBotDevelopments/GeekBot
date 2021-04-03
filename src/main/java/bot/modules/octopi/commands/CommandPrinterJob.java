package bot.modules.octopi.commands;

import bot.modules.discord.Command;
import bot.modules.octopi.PrinterJobOutputGen;
import com.google.common.collect.ImmutableList;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.channel.MessageChannel;
import reactor.core.publisher.Mono;

public class CommandPrinterJob extends Command
{
    public CommandPrinterJob()
    {
        super("job");
    }

    @Override
    public Mono<Message> handle(Message message, MessageChannel channel, ImmutableList<String> args)
    {
        if (!args.isEmpty())
        {
            return PrinterCommandHelpers.printerCommand(message, channel, args, 0,
                    printer -> channel
                    .createEmbed(spec -> PrinterJobOutputGen.createPrinterOutput(spec, printer))
                    .onErrorResume(err -> channel.createMessage("Error: " + err.getMessage())));
        }
        return channel.createMessage("Unknown command `" + message.getContent() + "`");
    }
}
