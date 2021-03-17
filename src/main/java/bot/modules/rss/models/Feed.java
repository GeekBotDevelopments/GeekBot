package bot.modules.rss.models;

import java.util.ArrayList;
import java.util.List;

/*
 * Stores an RSS feed
 */
public class Feed {

  final String rss;
  final String channel;
  final String title;
  final String link;
  final String description;
  final String language;
  final String copyright;
  final String pubDate;

  final List<FeedMessage> entries = new ArrayList<>();

  public Feed(
    String rss,
    String channel,
    String title,
    String link,
    String description,
    String language,
    String copyright,
    String pubDate
  ) {
    this.rss = rss;
    this.channel = channel;
    this.title = title;
    this.link = link;
    this.description = description;
    this.language = language;
    this.copyright = copyright;
    this.pubDate = pubDate;
  }

  public List<FeedMessage> getMessages() {
    return entries;
  }

  public String getTitle() {
    return title;
  }

  public String getLink() {
    return link;
  }

  public String getDescription() {
    return description;
  }

  public String getLanguage() {
    return language;
  }

  public String getCopyright() {
    return copyright;
  }

  public String getPubDate() {
    return pubDate;
  }
}
