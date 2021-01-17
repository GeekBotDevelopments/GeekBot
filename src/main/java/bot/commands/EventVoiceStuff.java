package bot.commands;

import bot.GeekBot;
import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.StreamSpeechRecognizer;

public class EventVoiceStuff extends Thread {

	StreamSpeechRecognizer speech;
	Configuration config;

	public EventVoiceStuff() {
		config = GeekBot.getConfig();
		speech = GeekBot.getSpeechRecognizer();
	}
}
