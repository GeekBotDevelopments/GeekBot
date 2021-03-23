package bot.modules.commands;

import bot.modules.discord.Command;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.channel.MessageChannel;
import reactor.core.publisher.Mono;

import java.util.List;

public class CmdContribute extends Command
{
	public CmdContribute()
	{
		super("contribute");
	}

	@Override
	public boolean handle(Message message, MessageChannel channel, List<String> strings)
	{
		channel.createMessage("to contribute to the bot, or get help with commands, visit the bots dev server here"
				+ " https://discord.gg/ADrTFRZ or create an issue on our github instead here issue on our github instead here"
				+ " <https://github.com/LegendaryGeek/GeekBot/issues>").block();
				return true;
	}
}