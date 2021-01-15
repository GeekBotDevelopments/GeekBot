package bot.modules.octopi.commands;

import com.google.gson.Gson;
import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import bot.modules.octopi.PrinterEnum;
import bot.modules.octopi.PrinterUtilities;

public class CmdEnderJob extends Command {
	Gson gson = new Gson();
	private static Logger log = LogManager.getLogger(CmdEnderJob.class);

	public CmdEnderJob() {
		name = "ender-job";
		hidden = true;
	}

	@Override
	protected void execute(CommandEvent event) {
		event.getChannel().sendMessage(PrinterUtilities.PrinterJob(PrinterEnum.ENDER).build()).submit();

	}

}
