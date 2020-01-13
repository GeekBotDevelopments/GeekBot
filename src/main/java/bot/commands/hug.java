package bot.commands;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Member;
import discord4j.core.object.util.Snowflake;

public class hug {
	private static Logger log = LogManager.getLogger(hug.class);

	public static String hug(MessageCreateEvent event) {
		String mention = event.getMember().get().getMention();
		String messageToSend = "*hugs* " + mention;
		String messageSent = event.getMessage().getContent().get();
		try {
			if (messageSent.length() >= 8) {
				String userid = messageSent.substring(8);
				Snowflake flake = Snowflake.of(userid);
				Member member = event.getGuild().block().getMemberById(flake).block();
				messageToSend = "*hugs* " + member.getMention();
			}
		} catch (Exception e) {
			log.catching(Level.ERROR, e);
			messageToSend = mention + "incorrect arguments, only member id's are supported not pings";
		}
		return messageToSend;
	}

}
