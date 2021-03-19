package bot.modules.commands;

import bot.modules.discord.Command;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.channel.MessageChannel;
import reactor.core.publisher.Mono;

import java.util.List;

public class CmdPing extends Command
{
	public CmdPing() {
	    super("ping");
	}

    @Override
    public void handle(Message message, MessageChannel channel, List<String> strings)
    {
        channel.createMessage("pong!").block();
    }
}
