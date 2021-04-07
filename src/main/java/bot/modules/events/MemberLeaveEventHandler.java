package bot.modules.events;

import discord4j.core.event.domain.guild.MemberLeaveEvent;
import discord4j.core.object.entity.Guild;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Created by Robin Seifert on 4/6/2021.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class MemberLeaveEventHandler
{
    public static void handle(MemberLeaveEvent event)
    {
        event
                .getGuild()
                .flatMap(Guild::getSystemChannel)
                .flatMap(channel ->
                        channel.createMessage(
                                String.format("Member %s has left",
                                        event.getUser().getUsername()
                                )
                        )
                )
                .subscribe();
    }
}
