package bot.modules.octopi;

import bot.modules.discord.CommandRoot;
import bot.modules.discord.DiscordModule;
import bot.modules.octopi.commands.CommandPrinterJob;
import bot.modules.octopi.commands.CommandPrinterState;
import bot.modules.octopi.commands.CommandVersion;
import bot.modules.octopi.events.ThreadPrinterStateMonitor;
import com.google.common.collect.ImmutableList;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.channel.MessageChannel;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.Optional;
import java.util.function.Function;

/**
 * Created by Robin Seifert on 3/16/2021.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class OctopiModule
{
    public static ThreadPrinterStateMonitor printerStateMonitor;


    public static void load()
    {
        printerStateMonitor = new ThreadPrinterStateMonitor();
        printerStateMonitor.start();

        //New commands
        final CommandRoot commandRoot = new CommandRoot("octoprint");
        DiscordModule.register(commandRoot);

        commandRoot.register(new CommandVersion());
        commandRoot.register(new CommandPrinterJob());
        commandRoot.register(new CommandPrinterState());
    }

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
     * @param message posted by the user
     * @param channel of the message posted
     * @param args created from the message
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
        final Optional<PrinterEnum> printerOptional = OctopiModule.findPrinter(printerString);

        //Give feedback if not found
        if (!printerOptional.isPresent())
        {
            return channel.createMessage("No printer found by name `" + printerString + "` while running command `" + message.getContent() + "`");
        }

        return function.apply(printerOptional.get());
    }
}
