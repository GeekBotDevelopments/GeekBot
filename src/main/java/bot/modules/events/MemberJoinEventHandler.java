package bot.modules.events;

import discord4j.core.event.domain.guild.MemberJoinEvent;
import discord4j.core.object.entity.Guild;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Created by Robin Seifert on 4/6/2021.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class MemberJoinEventHandler
{
    public static void handle(MemberJoinEvent event)
    {
        event
                .getGuild()
                .flatMap(Guild::getSystemChannel)
                .flatMap(channel -> channel.createMessage(
                        String.format(
                                "Welcome %s to %s",
                                event.getMember().getMention(),
                                event.getGuild().map(Guild::getName).block()
                        )
                )).subscribe();
    }
}
