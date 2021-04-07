package bot.modules.discord.handlers;

import bot.GeekBot;
import bot.modules.configs.MainConfig;
import bot.modules.discord.DiscordModule;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.User;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Robin Seifert on 4/6/2021.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class MessageEventHandler
{
    public static Flux<Message> handle(Flux<MessageCreateEvent> event)
    {
        return event.map(MessageCreateEvent::getMessage)
                .filter(MessageEventHandler::isAuthorNotBot)
                .filter(MessageEventHandler::isMessageCommand)
                .flatMap(MessageEventHandler::consumeMessage);
    }

    private static Mono<Message> consumeMessage(Message message)
    {
        final User author = message.getAuthor().get();
        final String messageContents = message.getContent();

        //Log message for debug
        GeekBot.MAIN_LOG.info(
                String.format(
                        "%s#%s(%s): %s",
                        author.getUsername(),
                        author.getDiscriminator(),
                        author.getId().asString(),
                        messageContents
                )
        );

        //Break command into string parts
        final List<String> args = new ArrayList<>(
                Arrays.asList(messageContents.split("\\s+"))
        );

        if (args.size() == 1)
        {
            //TODO ping user
            return message
                    .getChannel()
                    .flatMap(
                            messageChannel ->
                                    messageChannel.createMessage("No command given")
                    );
        }

        //Remove first index "!bot command arg1" -> "command arg1"
        args.remove(0);

        final String rootCommand = args.get(0);

        if (DiscordModule.getCommandMap().containsKey(rootCommand))
        {
            //Let command handle call
            return DiscordModule.getCommandMap()
                    .get(rootCommand)
                    .apply(message, DiscordModule.removeFirstArg(args))
                    .onErrorResume(
                            err -> {
                                GeekBot.MAIN_LOG.error(
                                        "Failed to process command `" + messageContents + "`",
                                        err
                                );
                                //TODO display full error message in chat, though lint to avoid leaking secrets
                                return message
                                        .getChannel()
                                        .flatMap(
                                                channel ->
                                                        channel.createMessage("Error processing command")
                                        );
                            }
                    );
        }

        //TODO check for more complex commands that may contain english phrases
        //      Example: "Wake me up at 10am" -> "Alarm add 10am"
        //      Example: "I need up at 10am" -> "Alarm add 10am"
        //     for this we will use a dictionary with fuzzy logic

        //TODO ping user
        return message
                .getChannel()
                .flatMap(
                        messageChannel ->
                                messageChannel.createMessage(
                                        String.format("Unknown command `%s`", rootCommand)
                                )
                );
    }

    private static boolean isAuthorNotBot(Message message)
    {
        return message.getAuthor().isPresent() &&
                message.getAuthor().map(user -> !user.isBot()).orElse(false);
    }

    private static boolean isMessageCommand(Message message)
    {
        return message
                .getContent()
                .toLowerCase()
                .startsWith(MainConfig.getBOT_PREFIX() + " ") ||
                message.getContent().equalsIgnoreCase(MainConfig.getBOT_PREFIX()) ||
                message.getUserMentionIds().equals(message.getGuild().block().getSelfMember().block().getId());
    }
}
