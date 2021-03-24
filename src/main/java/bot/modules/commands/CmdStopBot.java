package bot.modules.commands;

import bot.GeekBot;
import bot.modules.discord.Command;
import bot.modules.discord.DiscordModule;
import com.google.common.collect.ImmutableList;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.channel.MessageChannel;
import discord4j.core.object.reaction.ReactionEmoji;
import reactor.core.publisher.Mono;

public class CmdStopBot extends Command
{
    public CmdStopBot()
    {
        super("stop");
    }

    @Override
    public Mono<Message> handle(Message message, MessageChannel channel, ImmutableList<String> strings)
    {
        //React to user
        message.addReaction(ReactionEmoji.of(null, "\u2705", false).asUnicodeEmoji().get()).block();

        //Close discord client
        DiscordModule.client.logout().block();

        //Close spring client
        GeekBot.springApplicationContext.close();

        //Kill application
        System.exit(0);

        return Mono.empty();
    }
}
