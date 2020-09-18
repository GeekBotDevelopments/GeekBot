package bot.commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

public class CmdPing extends Command {

	public CmdPing() {
		name = "ping";
		help = "ping pong ping pong";
	}

	@Override
	protected void execute(CommandEvent event) {
		event.getChannel().sendMessage("pong!").submit();
	}

}
