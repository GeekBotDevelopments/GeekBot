package bot.modules.commands.rss;

public class CmdRssCheck //extends Command
{

//  public CmdRssCheck() {
//    name = "rss-check";
//    //hidden = true;
//    arguments = "url - url of the rss feed to check";
//  }
//
//  @Override
//  protected void execute(CommandEvent event) {
//    String[] message = event.getMessage().getContentRaw().split(" ");
//
//    RSSFeedParser feedParser = new RSSFeedParser(message[message.length - 1]);
//    Feed feed = feedParser.readFeed();
//    feed
//      .getMessages()
//      .forEach(
//        messagefeed -> {
//          EmbedBuilder builder = new EmbedBuilder();
//          builder
//            .setTitle(messagefeed.getTitle())
//            .setAuthor(messagefeed.getAuthor())
//            .setDescription(messagefeed.getDescription().toString())
//            .addField("Link", messagefeed.getLink(), false);
//
//          event.getChannel().sendMessage(builder.build()).submit();
//        }
//      );
//  }
}
