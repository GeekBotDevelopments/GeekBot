package bot.modules.twitch;

import bot.modules.configs.MainConfig;
import com.github.philippheuer.credentialmanager.domain.OAuth2Credential;
import com.github.philippheuer.events4j.core.EventManager;
import com.github.twitch4j.TwitchClient;
import com.github.twitch4j.TwitchClientBuilder;
import com.github.twitch4j.chat.TwitchChat;
import com.github.twitch4j.extensions.TwitchExtensions;
import com.github.twitch4j.graphql.TwitchGraphQL;
import com.github.twitch4j.helix.TwitchHelix;
import com.github.twitch4j.kraken.TwitchKraken;
import com.github.twitch4j.pubsub.TwitchPubSub;
import com.github.twitch4j.tmi.TwitchMessagingInterface;
import java.util.concurrent.ScheduledThreadPoolExecutor;

public class TwitchModule {

  private static EventManager eventManager;
  private static TwitchHelix helix;
  private static TwitchKraken kraken;
  private static TwitchMessagingInterface messagingInterface;
  private static TwitchChat chat;
  private static TwitchPubSub pubsub;
  private static TwitchGraphQL graphql;
  private static TwitchClient client;

  public static void load() {
    client =
      TwitchClientBuilder
        .builder()
        .withDefaultAuthToken(
          new OAuth2Credential("twitch", MainConfig.getTWITCH_ID())
        )
        .build();
    eventManager = (client.getEventManager());
    helix = (client.getHelix());
    kraken = (client.getKraken());
    messagingInterface = (client.getMessagingInterface());
    chat = (client.getChat());
    pubsub = (client.getPubSub());
    graphql = (client.getGraphQL());
  }

  /**
   * @return the graphql
   */
  public static TwitchGraphQL getGraphql() {
    return graphql;
  }

  /**
   * @return the pubsub
   */
  public static TwitchPubSub getPubsub() {
    return pubsub;
  }

  /**
   * @return the chat
   */
  public static TwitchChat getChat() {
    return chat;
  }

  /**
   * @return the messagingInterface
   */
  public static TwitchMessagingInterface getMessagingInterface() {
    return messagingInterface;
  }

  /**
   * @return the kraken
   */
  public static TwitchKraken getKraken() {
    return kraken;
  }

  /**
   * @return the helix
   */
  public static TwitchHelix getHelix() {
    return helix;
  }

  /**
   * @return the eventManager
   */
  public static EventManager getEventManager() {
    return eventManager;
  }
}
