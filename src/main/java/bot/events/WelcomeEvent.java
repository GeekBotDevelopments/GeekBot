package bot.events;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRemoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nonnull;

public class WelcomeEvent extends ListenerAdapter
{
    static final Logger logger = LogManager.getLogger();

    public WelcomeEvent()
    {
    }

    @Override
    public void onGuildMemberJoin(@Nonnull GuildMemberJoinEvent event)
    {
        final TextChannel channel = event.getGuild().getSystemChannel();
        if (channel != null)
        {
            final Member member = event.getMember();
            final String memberName = member.getEffectiveName();
            final String serverName = event.getGuild().getName();

            final String output = String.format("Welcome **%s** to %s", memberName, serverName);
            channel.sendMessage(output).submit();
        }
        else
        {
            sendChannelMissingError(event.getGuild());
        }
    }

    @Override
    public void onGuildMemberRemove(@Nonnull GuildMemberRemoveEvent event)
    {
        //Get channel and member
        final TextChannel channel = event.getGuild().getSystemChannel();
        if (channel != null)
        {
            //Get member name
            final Member member = event.getMember();
            final User user = event.getUser();
            final String memberName = member != null ? member.getEffectiveName() : user.getName();

            //Output to channel
            final String output = String.format("member %s has left", memberName);
            channel.sendMessage(output).submit();
        }
        else
        {
            sendChannelMissingError(event.getGuild());
        }
    }

    //TODO move to error helper class
    void sendChannelMissingError(@Nonnull Guild guild)
    {
        final String serverName = guild.getName();
        final String output = String.format("Failed to get system channel for server  %s", serverName);
        logger.error(output);
    }
}
