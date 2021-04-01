package bot.modules.commands;

import bot.modules.discord.Command;
import com.google.common.collect.ImmutableList;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.channel.MessageChannel;
import reactor.core.publisher.Mono;

public class CmdContribute extends Command
{
	public CmdContribute()
	{
		super("contribute");
	}

	@Override
	public Mono<Message> handle(Message message, MessageChannel channel, ImmutableList<String> strings)
	{
		return channel.createMessage("to contribute to the bot, or get help with commands, visit the bots dev server here"
				+ " https://discord.gg/ADrTFRZ or create an issue on our github instead here issue on our github instead here"
				+ " <https://github.com/LegendaryGeek/GeekBot/issues>");
	}
}
