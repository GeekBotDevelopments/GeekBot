package bot.modules.discord;

import com.google.common.collect.ImmutableList;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.channel.MessageChannel;
import org.apache.commons.collections4.map.CaseInsensitiveMap;
import reactor.core.publisher.Mono;

import java.util.Map;

/**
 * Created by Robin Seifert on 3/23/2021.
 */
public final class CommandRoot extends Command
{
    public Command zeroArgCommand;

    private Map<String, Command> commandMap = new CaseInsensitiveMap<>();

    public CommandRoot(String root)
    {
        super(root);
    }

    @Override
    public Mono<Message> handle(final Message message, final MessageChannel channel, final ImmutableList<String> args)
    {
        if (args.isEmpty())
        {
            if (zeroArgCommand != null)
            {
                return zeroArgCommand.handle(message, channel, DiscordModule.removeFirstArg(args));
            }
            return channel.createMessage(String.format("Command `%s` requires at least one argument to match sub-commands", name));
        }

        final String commandString = args.get(0);
        final Command command = commandMap.get(commandString);
        if (command != null)
        {
            return command.handle(message, channel, DiscordModule.removeFirstArg(args));
        }
        return channel.createMessage(String.format("Unknown sub-command `%s` for command `%s`", commandString, name));
    }

    public void register(Command command)
    {
        //TODO validate for overlap and name
        //TODO log commands added
        commandMap.put(command.name, command);
        command.setRootCommand(this);
    }
}
