package bot.modules.octopi;

import bot.modules.discord.CommandRoot;
import bot.modules.discord.DiscordModule;
import bot.modules.octopi.commands.CommandPrinterJob;
import bot.modules.octopi.commands.CommandPrinterState;
import bot.modules.octopi.commands.CommandVersion;
import bot.modules.octopi.watcher.ThreadPrinterStateMonitor;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

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
}
