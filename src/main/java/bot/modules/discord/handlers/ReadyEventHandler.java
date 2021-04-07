package bot.modules.discord.handlers;

import bot.GeekBot;
import bot.modules.configs.MainConfig;
import bot.modules.octopi.watcher.ThreadPrinterStateMonitor;
import discord4j.common.util.Snowflake;
import discord4j.core.event.domain.lifecycle.ReadyEvent;
import discord4j.core.object.entity.User;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Created by Robin Seifert on 4/6/2021.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ReadyEventHandler
{

    public static void handle(ReadyEvent event) {
        final User self = event.getSelf();
        GeekBot.MAIN_LOG.info(
                String.format(
                        "Logged in as %s#%s",
                        self.getUsername(),
                        self.getDiscriminator()
                )
        );

        //Get channels for printer
        ThreadPrinterStateMonitor.outputServer =
                event
                        .getClient()
                        .getGuildById(Snowflake.of(MainConfig.getLABRINTH_ID()))
                        .block();
    }
}
