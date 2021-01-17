package bot.modules.starbound;

import bot.events.EventStarboudServerReset;
import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.TimerTask;

public class CmdStarBoundRestart extends Command {
	private static Logger log = LogManager.getLogger();

	private static TimerTask starboudServerResetTask = new EventStarboudServerReset();

	public CmdStarBoundRestart() {
		name = "starset";
		hidden = true;
	}

	@Override
	protected void execute(CommandEvent event) {
		log.info("Restarting server");
		starboudServerResetTask.run();
		event.getChannel().sendMessage("Server reset").submit();
		log.info("Server Restarted");
	}

}
