package bot.commands;

import java.util.Arrays;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;

public class CmdHug extends Command {

	Logger log = LogManager.getLogger(this.getClass());

	public CmdHug() {
		name = "hug";
		help = "hugs you with lots of love";
		arguments = "<user id or ping> - optional";
	}

	@Override
	protected void execute(CommandEvent event) {
		String message = event.getMessage().getContentRaw().toString();
		int messageLength = message.split(" ").length;
		Member userPinged;
//		String newMessage = new StringBuilder().append(message.split(" ")[messageLength - 1])
//				.append(message.split(" ")[messageLength - 2])
//				.append(message.split(" ")[messageLength - 3])
//				.toString();

		log.info("Length of message: {}", messageLength);
		log.info("Message: {}", message);
		
		if (messageLength >= 3) {
			if (message.split(" ")[messageLength - 1].startsWith("<@")) {
				event.getChannel().sendMessage("i hug you " + message.split(" ")[messageLength - 1]).submit();
			} else {
				userPinged = event.getGuild().getMemberById(message.split(" ")[messageLength - 1]);
				event.getChannel().sendMessage("i hug you " + userPinged.getAsMention()).submit();
			}

		} else {
			event.getChannel().sendMessage("aww... feel bad " + event.getMember().getEffectiveName()
					+ "? i'll hug you.. *hugs " + event.getMember().getAsMention() + "*").submit();
		}
	}

}
