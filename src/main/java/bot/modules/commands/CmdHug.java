package bot.modules.commands;

public class CmdHug //extends Command
{
//    public CmdHug()
//    {
//        name = "hug";
//        help = "hugs you with lots of love";
//        arguments = "<user id or ping> - optional";
//    }
//
//    @Override
//    protected void execute(CommandEvent event)
//    {
//        String message = event.getMessage().getContentRaw().toString();
//        int messageLength = message.split(" ").length;
//        Member userPinged;
//
//        if (messageLength >= 3)
//        {
//            if (message.split(" ")[messageLength - 1].startsWith("<@"))
//            {
//                event.getChannel().sendMessage("i hug you " + message.split(" ")[messageLength - 1]).submit();
//            }
//            else if (message.split(" ")[messageLength - 1].contentEquals(GeekBot.discordClient.getSelfUser().getAsMention()))
//            {
//                event.getChannel().sendMessage("i cant hug myself... that'd be wierd.").submit();
//            }
//            else
//            {
//                userPinged = event.getGuild().getMemberById(message.split(" ")[messageLength - 1]);
//                event.getChannel().sendMessage("i hug you " + userPinged.getAsMention()).submit();
//            }
//
//        }
//        else
//        {
//            event.getChannel().sendMessage("aww... feel bad " + event.getMember().getEffectiveName()
//                    + "? i'll hug you.. *hugs " + event.getMember().getAsMention() + "*").submit();
//        }
//    }
}
