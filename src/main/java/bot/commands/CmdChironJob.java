package bot.commands;

import bot.printers.Printer;
import bot.printers.PrinterUtilities;
import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

public class CmdChironJob extends Command {
	public CmdChironJob() {
		name = "chiron-job";
		hidden = true;
	}

	@Override
	protected void execute(CommandEvent event) {
		event.getChannel().sendMessage(PrinterUtilities.PrinterJob(Printer.CHIRION).build()).submit();
	}
}
