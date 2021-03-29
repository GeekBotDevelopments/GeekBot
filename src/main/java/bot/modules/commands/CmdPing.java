package bot.modules.commands;

import bot.modules.discord.Command;
import com.google.common.collect.ImmutableList;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.channel.MessageChannel;
import reactor.core.publisher.Mono;

public class CmdPing extends Command
{
	public CmdPing() {
	    super("ping");
	}

    @Override
    public Mono<Message> handle(Message message, MessageChannel channel, ImmutableList<String> strings)
    {
        return channel.createMessage("pong!");
    }
}
