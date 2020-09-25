package bot.commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

import bot.GeekBot;

public class CmdExitVoice extends Command {

	public CmdExitVoice() {
		name = "exit";
		hidden = true;
	}

	@Override
	protected void execute(CommandEvent event) {
	event.getGuild().getAudioManager().closeAudioConnection();

	}

}
