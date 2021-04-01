package bot.modules.octopi;

import bot.modules.octopi.commands.CommandPrinterJob;
import bot.modules.discord.DiscordModule;
import bot.modules.octopi.events.ThreadPrinterStateMonitor;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Created by Robin Seifert on 3/16/2021.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class OctopiModule
{
    public static ThreadPrinterStateMonitor printerStateMonitor;

    public static void load() {
        printerStateMonitor = new ThreadPrinterStateMonitor();
        printerStateMonitor.start();

        //Discord commands
        DiscordModule.register(new CommandPrinterJob(PrinterEnum.ENDER)); //TODO replace with command that gets name as arg
        DiscordModule.register(new CommandPrinterJob(PrinterEnum.CHIRION));
    }
}
