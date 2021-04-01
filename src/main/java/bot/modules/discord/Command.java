package bot.modules.discord;

import com.google.common.collect.ImmutableList;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.channel.MessageChannel;
import lombok.Data;
import reactor.core.publisher.Mono;

import java.util.function.BiFunction;

/**
 * Created by Robin Seifert on 3/16/2021.
 */
@Data
public abstract class Command implements BiFunction<Message, ImmutableList<String>, Mono<Message>>
{
    public final String name;
    public Boolean ownerOnly;

    private CommandRoot rootCommand;

    protected Command(String root) {
        this.name = root;
        this.ownerOnly = false;
    }

    @Override
    public Mono<Message> apply(final Message message, final ImmutableList<String> args)
    {
        return message.getChannel().flatMap(messageChannel -> handle(message, messageChannel, args));
    }

    /**
     * Called to handle the message
     *
     * @param message sent by the user
     * @param channel the message was from
     * @param args arguments for the command
     * @return weather or not the command was able to successfully execute
     */
    public abstract Mono<Message> handle(final Message message, final MessageChannel channel, final ImmutableList<String> args);

    /**
     * Full command path required to run this command
     *
     * Example: /rootCommand parentCommand thisCommand
     * @return string path
     */
    public String getCommandPath() {
        if(rootCommand != null) {
            return String.format("%s %s", rootCommand.getCommandPath(), name);
        }
        return name;
    }
}
