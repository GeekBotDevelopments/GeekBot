package bot.events;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberLeaveEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRemoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class WelcomeEvent extends ListenerAdapter
{
    public WelcomeEvent()
    {
        // TODO Auto-generated constructor stub
    }

    @Override
    public void onGuildMemberJoin(GuildMemberJoinEvent event)
    {
        // TODO Auto-generated method stub
        super.onGuildMemberJoin(event);
        TextChannel chan = event.getGuild().getSystemChannel();
        Member member = event.getMember();

        chan.sendMessage("Welcome **" + member.getEffectiveName() + "** to " + event.getGuild().getName()).submit();
    }

    @Override
    public void onGuildMemberRemove(GuildMemberRemoveEvent event)
    {
        super.onGuildMemberRemove(event);
        TextChannel chan = event.getGuild().getSystemChannel();
        Member member = event.getMember();

        chan.sendMessage("member " + member.getEffectiveName() + " has left").submit();
    }
}
