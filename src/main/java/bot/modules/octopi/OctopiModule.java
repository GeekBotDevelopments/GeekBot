package bot.modules.octopi;

import bot.modules.octopi.events.ThreadPrinterStateMonitor;

/**
 * Created by Robin Seifert on 3/16/2021.
 */
public class OctopiModule
{

    public static ThreadPrinterStateMonitor printerStateMonitor;

    public static void load() {
        printerStateMonitor = new ThreadPrinterStateMonitor();
        printerStateMonitor.start();
    }
}
