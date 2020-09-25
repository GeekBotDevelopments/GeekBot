package bot.commands;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

import bot.GeekBot;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.managers.AudioManager;

public class CmdJoinVoice extends Command {
	private static Logger log = LogManager.getLogger();
	
	public CmdJoinVoice() {
		name = "join";
		hidden = true;
	}

	@Override
	protected void execute(CommandEvent event) {
		AudioManager manager = event.getGuild().getAudioManager();

					VoiceChannel chan = event.getGuild().getVoiceChannelById(359460350988517378l);
					manager.openAudioConnection(chan);
					log.info("connected to channel: {}" + chan.getName());
			

	}


}
