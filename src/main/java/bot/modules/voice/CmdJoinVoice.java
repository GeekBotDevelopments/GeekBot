package bot.modules.voice;

import java.util.List;

import bot.modules.discord.Command;
import discord4j.core.object.VoiceState;
import discord4j.core.object.entity.Member;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.channel.MessageChannel;
import discord4j.core.object.entity.channel.VoiceChannel;
import discord4j.voice.AudioReceiver;
import discord4j.voice.VoiceConnection;
import reactor.core.publisher.Mono;

public class CmdJoinVoice extends Command
{

    public CmdJoinVoice(String root) {
        super(root);
    }

    @Override
    public Mono<Message> handle(Message message, MessageChannel channel, List<String> strings) {
        Member member = message.getAuthor().get().asMember(message.getGuild().block().getId()).block();
        VoiceState state = member.getVoiceState().block();
        VoiceChannel chan = state.getChannel().block();
        AudioReceiver receiver;
        VoiceConnection connection;
        if(chan.equals(null)){
            connection = chan.join(spec -> spec.setReceiver(receiver)).block();
        }
        return Mono.empty();
    }
//	private static Logger log = LogManager.getLogger();
//
//	public CmdJoinVoice() {
//		name = "join";
//		hidden = true;
//	}
//
//	@Override
//	protected void execute(CommandEvent event) {
//		AudioManager manager = event.getGuild().getAudioManager();
//
//		VoiceChannel chan = event.getGuild().getVoiceChannelById(359460350988517378l);
//		manager.openAudioConnection(chan);
//		log.info("connected to channel: {}" + chan.getName());
//		//manager.setSendingHandler(new AudioSendHandler().);
//
//		//Voice voice = Voice.newBuilder().addLanguageCodes("en-US").setSsmlGender(SsmlVoiceGender.FEMALE)
//		//		.setName("GeekBot").build();
//		//SynthesisInput input = SynthesisInput.newBuilder().setText("Hello World!").build();
//		//AudioConfig audioConfig = AudioConfig.newBuilder().setAudioEncoding(AudioEncoding.OGG_OPUS).build();
//
//		//TextToSpeechClient ttsClient = TextToSpeechClient.create().synthesizeSpeech(input, voice, audioConfig);
//	}

}
