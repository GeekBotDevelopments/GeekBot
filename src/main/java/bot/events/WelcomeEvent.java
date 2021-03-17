package bot.events;

public class WelcomeEvent
{
//    static final Logger logger = LogManager.getLogger();
//
//    public WelcomeEvent()
//    {
//    }
//
//    @Override
//    public void onGuildMemberJoin(@Nonnull GuildMemberJoinEvent event)
//    {
//        final TextChannel channel = event.getGuild().getSystemChannel();
//        if (channel != null)
//        {
//            final Member member = event.getMember();
//            final String memberName = member.getEffectiveName();
//            final String serverName = event.getGuild().getName();
//
//            final String output = String.format("Welcome **%s** to %s", memberName, serverName);
//            channel.sendMessage(output).submit();
//        }
//        else
//        {
//            sendChannelMissingError(event.getGuild());
//        }
//    }
//
//    @Override
//    public void onGuildMemberRemove(@Nonnull GuildMemberRemoveEvent event)
//    {
//        //Get channel and member
//        final TextChannel channel = event.getGuild().getSystemChannel();
//        if (channel != null)
//        {
//            //Get member name
//            final Member member = event.getMember();
//            final User user = event.getUser();
//            final String memberName = member != null ? member.getEffectiveName() : user.getName();
//
//            //Output to channel
//            final String output = String.format("member **%s** has left", memberName);
//            channel.sendMessage(output).submit();
//        }
//        else
//        {
//            sendChannelMissingError(event.getGuild());
//        }
//    }
//
//    //TODO move to error helper class
//    void sendChannelMissingError(@Nonnull Guild guild)
//    {
//        final String serverName = guild.getName();
//        final String output = String.format("Failed to get system channel for server  %s", serverName);
//        logger.error(output);
//    }
}
