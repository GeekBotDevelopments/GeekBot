package bot.modules.octopi.commands;

import bot.modules.octopi.PrinterEnum;
import bot.modules.octopi.PrinterUtilities;
import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

public class CommandPrinterJob extends Command
{
    private final PrinterEnum printerEnum;

    public CommandPrinterJob(PrinterEnum printerEnum)
    {
        this.printerEnum = printerEnum;
        this.name = printerEnum.getName() + "-job";
        this.hidden = true;
    }

    @Override
    protected void execute(CommandEvent event)
    {
        event.getChannel().sendMessage(PrinterUtilities.createPrinterOutput(printerEnum).build()).submit();
    }
}
