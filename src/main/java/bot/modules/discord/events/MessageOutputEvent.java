package bot.modules.discord.events;

import bot.modules.discord.DiscordModule;
import discord4j.core.event.domain.Event;

/**
 * Created by Robin Seifert on 4/6/2021.
 */
public class MessageOutputEvent extends Event
{
    public final String output;
    public final long guildID;
    public final long channelID;

    public MessageOutputEvent(long guildID, long channelID, String output)
    {
        super(DiscordModule.discordClient, null);
        this.guildID = guildID;
        this.channelID = channelID;
        this.output = output;
    }
}
