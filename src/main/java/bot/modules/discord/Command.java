package bot.modules.discord;

import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.channel.MessageChannel;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.function.BiFunction;

/**
 * Created by Robin Seifert on 3/16/2021.
 */
public abstract class Command implements BiFunction<Message, List<String>, Mono<Message>>
{
    final String name;
    final Boolean ownerOnly;

    public Command(String root) {
        this.name = root;
        this.ownerOnly = false;
    }
    @Override
    public Mono<Message> apply(Message message, List<String> strings)
    {
        return message.getChannel().flatMap(messageChannel -> handle(message, messageChannel, strings));
    }

    /**
     * Called to handle the message
     *
     * @param message sent by the user
     * @param channel the message was from
     * @param strings arguments for the command
     * @return weather or not the command was able to successfully execute
     */
    public abstract Mono<Message> handle(Message message, MessageChannel channel, List<String> strings);
}
