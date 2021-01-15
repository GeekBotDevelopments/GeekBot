package bot.modules.octopi.commands;

import bot.modules.octopi.PrinterEnum;
import bot.modules.octopi.PrinterUtilities;
import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

public class CmdChironJob extends Command {
	public CmdChironJob() {
		name = "chiron-job";
		hidden = true;
	}

	@Override
	protected void execute(CommandEvent event) {
		event.getChannel().sendMessage(PrinterUtilities.PrinterJob(PrinterEnum.CHIRION).build()).submit();
	}
}
