package bot.modules.commands;

import bot.modules.discord.Command;
import com.google.common.collect.ImmutableList;
import discord4j.core.object.entity.Member;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.channel.MessageChannel;
import reactor.core.publisher.Mono;

public class CmdHug extends Command {
  private Member userPinged;

  public CmdHug() {
    super("hug");
  }

@Override
public Mono<Message> handle(Message message, MessageChannel channel, ImmutableList<String> strings) {
    String rawPing = strings.get(0);


  if(rawPing.startsWith("<@")){
    message.getGuild().block().getMembers().toStream().forEach(action -> {
      if(action.getId().asString() == rawPing.substring(3, 21)){
        userPinged = action;
      }
     });
    }else{
      message.getGuild().block().getMembers().toStream().forEach(action2 ->{
        if(action2.getId().asString() == rawPing){
          userPinged = action2;
        }
      });
    }


    return channel.createMessage("i hug you, " + userPinged.getMention());
  }

}

//
//------< JDA CODE >--------
//
//   @Override
//   protected void execute(CommandEvent event) {
//     String message = event.getMessage().getContentRaw().toString();
//     int messageLength = message.split(" ").length;
//     Member userPinged;

//     if (messageLength >= 3) {
//       if (message.split(" ")[messageLength - 1].startsWith("<@")) {
//         event
//           .getChannel()
//           .sendMessage("i hug you " + message.split(" ")[messageLength - 1])
//           .submit();
//       } else if (
//         message
//           .split(" ")[messageLength - 1].contentEquals(
//             GeekBot.discordClient.getSelfUser().getAsMention()
//           )
//       ) {
//         event
//           .getChannel()
//           .sendMessage("i cant hug myself... that'd be wierd.")
//           .submit();
//       } else {
//         userPinged =
//           event.getGuild().getMemberById(message.split(" ")[messageLength - 1]);
//         event
//           .getChannel()
//           .sendMessage("i hug you " + userPinged.getAsMention())
//           .submit();
//       }
//     } else {
//       event
//         .getChannel()
//         .sendMessage(
//           "aww... feel bad " +
//           event.getMember().getEffectiveName() +
//           "? i'll hug you.. *hugs " +
//           event.getMember().getAsMention() +
//           "*"
//         )
//         .submit();
//     }
//   }

