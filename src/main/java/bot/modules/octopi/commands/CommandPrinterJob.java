package bot.modules.octopi.commands;

import bot.modules.discord.Command;
import bot.modules.octopi.OctopiModule;
import bot.modules.octopi.PrinterEnum;
import bot.modules.octopi.PrinterUtilities;
import com.google.common.collect.ImmutableList;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.channel.MessageChannel;
import reactor.core.publisher.Mono;

import java.util.Optional;

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
            final String printerString = args.get(0);

            final Optional<PrinterEnum> printerOptional = OctopiModule.findPrinter(printerString);

            if (!printerOptional.isPresent())
            {
                return channel.createMessage("No printer found by name `" + printerString + "` while running command `" + message.getContent() + "`");
            }

            final PrinterEnum printer = printerOptional.get();

            return channel
                    .createEmbed(spec -> PrinterUtilities.createPrinterOutput(spec, printer))
                    .onErrorResume(err -> channel.createMessage("Error: " + err.getMessage()));
        }
        return channel.createMessage("Unknown command `" + message.getContent() + "`");
    }
}
