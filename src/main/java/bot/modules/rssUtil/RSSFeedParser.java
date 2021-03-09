package bot.modules.rssUtil;

import java.net.MalformedURLException;
import java.net.URL;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import bot.models.RssFeed.Feed;

public class RSSFeedParser {
    
    private static final Logger LOGGER = LogManager.getLogger();

    static final String TITLE = "title";
    static final String DESCRIPTION = "description";
    static final String CHANNEL = "channel";
    static final String LANGUAGE = "language";
    static final String COPYRIGHT = "copyright";
    static final String LINK = "link";
    static final String AUTHOR = "author";
    static final String ITEM = "item";
    static final String PUB_DATE = "pubDate";
    static final String GUID = "guid";

    final URL url;

    public RSSFeedParser(String feedUrl){
        try {
            this.url = new URL(feedUrl);
        } catch (MalformedURLException e){
            LOGGER.catching(e);
            throw new RuntimeException("Could not parse URL", e);
        }
    }

    //public Feed readFeed() {

    //}
}
