package bot.modules.discord;

import bot.GeekBot;
import bot.modules.configs.MainConfig;
import bot.modules.octopi.events.ThreadPrinterStateMonitor;
import discord4j.common.util.Snowflake;
import discord4j.core.DiscordClientBuilder;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.lifecycle.ReadyEvent;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.User;
import org.apache.commons.collections4.map.CaseInsensitiveMap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Handles loading the discord module. Other modules will interact with this module to provide commands.
 * <p>
 * <p>
 * Created by Robin Seifert on 3/16/2021.
 */
public final class DiscordModule
{
    private static final CaseInsensitiveMap<String, Command> commandMap = new CaseInsensitiveMap<>();
    public static GatewayDiscordClient client;

    private DiscordModule() {}

    public static void load()
    {
        final String discordToken = MainConfig.getDISCORD_TOKEN();

        client = DiscordClientBuilder.create(discordToken)
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

                    //Get channels for printer
                    ThreadPrinterStateMonitor.outputServer = event.getClient().getGuildById(Snowflake.of(MainConfig.getLABRINTH_ID())).block();
                });

        //Handle messages
        client.getEventDispatcher().on(MessageCreateEvent.class)
                .map(MessageCreateEvent::getMessage)
                //Only respond to non-bot commands
                .filter(message -> message.getAuthor().isPresent() && message.getAuthor().map(user -> !user.isBot()).orElse(false))
                //Only respond to prefix commands TODO add @name as well
                .filter(message -> message.getContent().toLowerCase().startsWith(MainConfig.getBOT_PREFIX() + " ")
                        || message.getContent().equalsIgnoreCase(MainConfig.getBOT_PREFIX())
                        || message.getUserMentionIds().equals(message.getGuild().block().getSelfMember().block().getId()))

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
                    final List<String> args = new ArrayList<>(Arrays.asList(messageContents.split("\\s+")));


                    if (args.size() == 1)
                    {
                        //TODO ping user
                        return message.getChannel().flatMap(messageChannel -> messageChannel.createMessage("No command given"));
                    }

                    //Remove first index "!bot command arg1" -> "command arg1"
                    args.remove(0);

                    final String rootCommand = args.get(0);

                    if (commandMap.containsKey(rootCommand))
                    {
                        //Remove second index "command arg1" -> "arg1"
                        args.remove(0);

                        //Let command handle call
                        return commandMap.get(rootCommand).apply(message, args);
                    }

                    //TODO check for more complex commands that may contain english phrases
                    //      Example: "Wake me up at 10am" -> "Alarm add 10am"
                    //      Example: "I need up at 10am" -> "Alarm add 10am"
                    //     for this we will use a dictionary with fuzzy logic

                    //TODO ping user
                    return message.getChannel().flatMap(messageChannel -> messageChannel.createMessage(String.format("Unknown command `%s`", rootCommand)));
                })
                .subscribe();
    }

    public static void register(Command command)
    {
        GeekBot.MAIN_LOG.info("Registering command: {}", command.name);
        commandMap.put(command.name, command);
    }



}
