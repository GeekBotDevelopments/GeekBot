package bot.events;

import net.dv8tion.jda.api.audio.AudioSendHandler;
import net.dv8tion.jda.api.managers.AudioManager;

import java.nio.ByteBuffer;

public class EventVoice extends Thread {

	AudioManager manager;
	AudioSendHandler handy = new AudioSendHandler() {

		@Override
		public ByteBuffer provide20MsAudio() {

			return null;
		}

		@Override
		public boolean canProvide() {
			// TODO Auto-generated method stub
			return false;
		}
	};

	public EventVoice(AudioManager manager) {
		this.manager = manager;
		this.start();
	}

	public void say(String message) {
		manager.setSendingHandler(handy);
	}

}
