package bot.commands;

import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.util.Snowflake;

public class hug {

	public static String hug(MessageCreateEvent event) {
		String mention = event.getMember().get().getMention();
//		Snowflake memberId = null;
		String messageToSend = mention + " *hug*";
		String messageSent = event.getMessage().getContent().get();
		System.out.println("[hug] message sent: " + messageSent);
//		if(messageSent.startsWith("!gb hug ")) {
//			
//			
//		}
//		
		return messageToSend;
	}

}
