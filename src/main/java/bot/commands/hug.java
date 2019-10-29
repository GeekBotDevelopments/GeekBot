package bot.commands;

import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Member;
import discord4j.core.object.util.Snowflake;

public class hug {

	public static String hug(MessageCreateEvent event) {
		String mention = event.getMember().get().getMention();
		Snowflake id = event.getMember().get().getId();
		String messageToSend = mention + " *hugs*";
		String messageSent = event.getMessage().getContent().get();
		System.out.println("[hug] message sent: " + messageSent);
		if (messageSent.equals("hug %s")) {
			String message = String.format(messageSent, id);
			Member member = event.getGuild().block().getMemberById(id).block();
			messageToSend = member.getMention() + " *hugs*";
		}
		return messageToSend;
	}

}
