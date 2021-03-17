package bot.modules.discord;

import bot.GeekBot;
import bot.modules.configs.MainConfig;
import discord4j.core.DiscordClientBuilder;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.lifecycle.ReadyEvent;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.User;
import org.apache.commons.collections4.map.CaseInsensitiveMap;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;

/**
 * Handles loading the discord module. Other modules will interact with this module to provide commands.
 * <p>
 * <p>
 * Created by Robin Seifert on 3/16/2021.
 */
public final class DiscordModule
{
    private static CaseInsensitiveMap<String, BiFunction<Message, List<String>, Mono<Message>>> commandMap = new CaseInsensitiveMap<>();

    private DiscordModule() {}

    public static void load()
    {
        final String discordToken = MainConfig.getDISCORD_TOKEN();

        GatewayDiscordClient client = DiscordClientBuilder.create(discordToken)
                .build()
                .login()
                .block();

        //Output what we logged in as
        client.getEventDispatcher().on(ReadyEvent.class)
                .subscribe(event -> {
                    final User self = event.getSelf();
                    GeekBot.MAIN_LOG.info(String.format(
                            "Logged in as %s#%s", self.getUsername(), self.getDiscriminator()
                    ));
                });

        //Handle messages
        client.getEventDispatcher().on(MessageCreateEvent.class)
                .map(MessageCreateEvent::getMessage)
                //Only respond to non-bot commands
                .filter(message -> message.getAuthor().isPresent() && message.getAuthor().map(user -> !user.isBot()).orElse(false))
                //Only respond to prefix commands TODO add @name as well
                .filter(message -> message.getContent().toLowerCase().startsWith(MainConfig.getBOT_PREFIX() + " ")
                        || message.getContent().equalsIgnoreCase(MainConfig.getBOT_PREFIX())
                )
                //Consume message
                .flatMap((message) -> {
                    final User author = message.getAuthor().get();
                    final String messageContents = message.getContent();

                    //Log message for debug
                    GeekBot.MAIN_LOG.info(String.format("%s#%s(%s): %s",
                            author.getUsername(),
                            author.getDiscriminator(),
                            author.getId().asString(),
                            messageContents)
                    );

                    //Break command into string parts
                    final List<String> split = new ArrayList<>(Arrays.asList(messageContents.split("\\s+")));


                    if (split.size() == 1)
                    {
                        //TODO ping user
                        return message.getChannel().flatMap(messageChannel -> messageChannel.createMessage("No command given"));
                    }

                    //Remove first index
                    split.remove(0);

                    final String rootCommand = split.get(0);

                    if (commandMap.containsKey(rootCommand))
                    {
                        //Let command handle call
                        return commandMap.get(rootCommand).apply(message, split);
                    }
                    //TODO check for more complex commands that may contain english phrases
                    //      Example: "Wake me up at 10am" -> "Alarm add 10am"
                    //      Example: "I need up at 10am" -> "Alarm add 10am"
                    //     for this we will use a dictionary with fuzzy logic

                    //TODO ping user
                    return message.getChannel().flatMap(messageChannel -> messageChannel.createMessage(String.format("Unknown command `%s`", rootCommand)));
                })
                .subscribe();

        client.onDisconnect().block();
    }
}
