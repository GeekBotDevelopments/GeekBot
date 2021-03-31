package bot.modules.voice;

import java.util.List;

import com.google.common.collect.ImmutableList;

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

    VoiceConnection connection;
    VoiceChannel chan;
    VoiceState state;
    Member member;

    public CmdJoinVoice(String root) {
        super(root);
    }

    @Override
    public Mono<Message> handle(Message message, MessageChannel channel, ImmutableList<String> strings) {
         member = message.getAuthor().get().asMember(message.getGuild().block().getId()).block();
         state = member.getVoiceState().block();
         chan = state.getChannel().block();
        if(chan.equals(null)){
            chan.sendConnectVoiceState(false, false);
            connection = chan.getVoiceConnection().block();
        }
        return Mono.empty();
    }

    public VoiceConnection getConnection() {
        return this.connection;
    }

}
