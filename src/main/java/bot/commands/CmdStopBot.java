package bot.commands;

import bot.GeekBot;
import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CmdStopBot extends Command {
	private static Logger log = LogManager.getLogger();

	public CmdStopBot(){
		name = "stop";
		hidden = true;
	}

	@Override
	protected void execute(CommandEvent event) {
		log.info("Shutting down");
		GeekBot.getClient().cancelRequests();
		GeekBot.getClient().shutdown();
		log.info("Shutdown");
		System.exit(0);
		log.info("Should not see thisss");
	}

}
