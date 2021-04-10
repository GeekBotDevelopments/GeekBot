package bot.modules.discord.handlers;

import bot.GeekBot;
import bot.modules.discord.events.MessageOutputEvent;
import discord4j.common.util.Snowflake;
import discord4j.core.object.entity.channel.Channel;
import discord4j.discordjson.json.MessageData;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Created by Robin Seifert on 4/6/2021.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class MessageOutputEventHandler
{
    public static void handle(MessageOutputEvent event)
    {
        event.getClient()
                .getGuildById(Snowflake.of(event.guildID))
                .flatMap(guild -> guild.getChannelById(Snowflake.of(event.channelID)))
                .map(Channel::getRestChannel)
                .map(channel -> channel.createMessage(event.output))
                .subscribe(mono -> {
                    MessageData messageData = mono.block();
                    GeekBot.MAIN_LOG.info(messageData);
                }, GeekBot.MAIN_LOG::error, () -> logMessage(event));
    }

    private static void logMessage(MessageOutputEvent event)
    {
        GeekBot.MAIN_LOG.info(
                String.format(
                        "Sent message to server[%s] & channel[%s] containing: %s",
                        event.guildID,
                        event.channelID,
                        event.output
                )
        );
    }
}
