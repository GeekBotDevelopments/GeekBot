package bot.commands;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import discord4j.core.event.domain.message.MessageCreateEvent;

public class Settings {
	static Logger log = LogManager.getLogger(Settings.class);
	static String messageToSend = "Invalid arguments for command \"Settings\"";

	public static String settings(MessageCreateEvent event) {
		String sent = event.getMessage().getContent().get();

		if ("set".equals(sent.split("\\s+")[2])) {
			log.info("Hello from the Set if loop");
		} else if ("get".equals(sent.split("\\s+")[2])) {
			log.info("Hello from the Get if loop");
		}
		return messageToSend;
	}

	private String setSetting(MessageCreateEvent event) {

		return null;
	}

}
