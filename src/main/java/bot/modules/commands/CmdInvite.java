package bot.modules.commands;

import bot.modules.discord.Command;
import com.google.common.collect.ImmutableList;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.channel.MessageChannel;
import reactor.core.publisher.Mono;

public class CmdInvite extends Command
{
	//TODO move to config
	public static final String LINK = "https://discordapp.com/api/oauth2/authorize?client_id=426722296816861184&permissions=426722296816861184&scope=bot";

	public CmdInvite() {
		super("invite-bot");

	}

	@Override
	public Mono<Message> handle(Message message, MessageChannel channel, ImmutableList<String> strings)
	{
		return channel.createMessage("have an invite link to invite the bot to your server: " + LINK);
	}
}
