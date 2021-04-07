package bot.modules.discord;

import bot.GeekBot;
import bot.modules.configs.MainConfig;
import bot.modules.events.MemberJoinEventHandler;
import bot.modules.events.MemberLeaveEventHandler;
import bot.modules.events.MessageEventHandler;
import bot.modules.events.ReadyEventHandler;
import com.google.common.collect.Collections2;
import com.google.common.collect.ImmutableList;
import discord4j.core.DiscordClientBuilder;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.guild.MemberJoinEvent;
import discord4j.core.event.domain.guild.MemberLeaveEvent;
import discord4j.core.event.domain.lifecycle.ReadyEvent;
import discord4j.core.event.domain.message.MessageCreateEvent;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.collections4.map.CaseInsensitiveMap;

import java.util.List;
import java.util.Map;

/**
 * Handles loading the discord module. Other modules will interact with this module to provide commands.
 * <p>
 * <p>
 * Created by Robin Seifert on 3/16/2021.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class DiscordModule {

  private static final CaseInsensitiveMap<String, Command> commandMap = new CaseInsensitiveMap<>();
  public static GatewayDiscordClient client;

  public static void load() {
    final String discordToken = MainConfig.getDISCORD_TOKEN();

    client = DiscordClientBuilder.create(discordToken).build().login().block();

    //Setup event handlers
    client.getEventDispatcher().on(ReadyEvent.class).subscribe(ReadyEventHandler::handle);
    client.getEventDispatcher().on(MessageCreateEvent.class).as(MessageEventHandler::handle).subscribe();
    client.getEventDispatcher().on(MemberJoinEvent.class).subscribe(MemberJoinEventHandler::handle);
    client.getEventDispatcher().on(MemberLeaveEvent.class).subscribe(MemberLeaveEventHandler::handle);
  }

  public static Map<String, Command> getCommandMap() {
      return commandMap;
  }

  public static void register(Command command) {
    GeekBot.MAIN_LOG.info("Registering command: {}", command.name);
    commandMap.put(command.name, command);
  }

  public static ImmutableList<String> removeFirstArg(List<String> args) {
    if (args.size() <= 1) {
      return ImmutableList.of();
    }
    return ImmutableList.copyOf(
      Collections2.filter(
        args,
        s -> {
          return s != args.get(0); //NOSONAR
        }
      )
    );
  }
}
