package bot.commands;

import com.google.cloud.texttospeech.v1.TextToSpeechClient;
import com.google.cloud.texttospeech.v1beta1.AudioConfig;
import com.google.cloud.texttospeech.v1beta1.AudioEncoding;
import com.google.cloud.texttospeech.v1beta1.SsmlVoiceGender;
import com.google.cloud.texttospeech.v1beta1.SynthesisInput;
import com.google.cloud.texttospeech.v1beta1.Voice;
import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.dv8tion.jda.api.audio.AudioSendHandler;
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
		//manager.setSendingHandler(new AudioSendHandler().);

		//Voice voice = Voice.newBuilder().addLanguageCodes("en-US").setSsmlGender(SsmlVoiceGender.FEMALE)
		//		.setName("GeekBot").build();
		//SynthesisInput input = SynthesisInput.newBuilder().setText("Hello World!").build();
		//AudioConfig audioConfig = AudioConfig.newBuilder().setAudioEncoding(AudioEncoding.OGG_OPUS).build();

		//TextToSpeechClient ttsClient = TextToSpeechClient.create().synthesizeSpeech(input, voice, audioConfig);
	}

}
