package bot.modules.octopi;

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
    }
}
