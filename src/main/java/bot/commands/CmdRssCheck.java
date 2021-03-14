package bot.commands;

import bot.models.RssFeed.Feed;
import bot.modules.rssUtil.RSSFeedParser;
import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import net.dv8tion.jda.api.EmbedBuilder;

public class CmdRssCheck extends Command {

  public CmdRssCheck() {
    name = "rss-check";
    //hidden = true;
    arguments = "url - url of the rss feed to check";
  }

  @Override
  protected void execute(CommandEvent event) {
    String[] message = event.getMessage().getContentRaw().split(" ");
    EmbedBuilder builder = new EmbedBuilder();
    RSSFeedParser feedParser = new RSSFeedParser(message[message.length - 1]);
    Feed feed = feedParser.readFeed();
    feed
      .getMessages()
      .forEach(
        messagefeed -> {
          builder
            .setTitle(messagefeed.getTitle())
            .setAuthor(messagefeed.getAuthor())
            .setDescription(messagefeed.getDescription().toString())
            .addField("Link", messagefeed.getLink(), false);

          event.getChannel().sendMessage(builder.build()).submit();
        }
      );
  }
}
