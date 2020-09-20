package bot.commands;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

import bot.GeekBot;
import bot.events.EventStarboudServerReset;

public class CmdStarBoundRestart extends Command {
	private static Logger log = LogManager.getLogger();
	
	public CmdStarBoundRestart() {
		name = "starset";
		hidden = true;
	}

	@Override
	protected void execute(CommandEvent event) {
		log.info("Restarting server");
		GeekBot.task.run();
		event.getChannel().sendMessage("Server reset").submit();
		log.info("Server Restarted");
	}

}
