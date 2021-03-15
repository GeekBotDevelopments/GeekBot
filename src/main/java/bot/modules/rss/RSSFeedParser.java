package bot.modules.rss;

import bot.models.RssFeed.Feed;
import bot.models.RssFeed.FeedMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.xml.XMLConstants;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.XMLEvent;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class RSSFeedParser {

  private static final Logger LOGGER = LogManager.getLogger();

  private static final String TITLE = "title";
  private static final String DESCRIPTION = "description";
  private static final String CHANNEL = "channel";
  private static final String LANGUAGE = "language";
  private static final String COPYRIGHT = "copyright";
  private static final String LINK = "link";
  private static final String AUTHOR = "author";
  private static final String ITEM = "item";
  private static final String PUB_DATE = "pubDate";
  private static final String GUID = "guid";

  final URL url;

  public RSSFeedParser(String feedUrl) {
    try {
      this.url = new URL(feedUrl);
    } catch (MalformedURLException e) {
      throw new RuntimeException("Could not parse URL", e);
    }
  }

  public Feed readFeed() {
    Feed feed = null;
    try {
      boolean isFeedHeader = true;
      // Set header values intial to the empty string
      String description = "";
      String title = "";
      String link = "";
      String language = "";
      String copyright = "";
      String author = "";
      String pubdate = "";
      String guid = "";

      // First create a new XMLInputFactory
      XMLInputFactory inputFactory = XMLInputFactory.newInstance();
      inputFactory.setProperty(XMLConstants.ACCESS_EXTERNAL_DTD, "");
      inputFactory.setProperty(XMLConstants.ACCESS_EXTERNAL_SCHEMA, "");
      // Setup a new eventReader
      InputStream in = read();
      XMLEventReader eventReader = inputFactory.createXMLEventReader(in);
      // read the XML document
      while (eventReader.hasNext()) {
        XMLEvent event = eventReader.nextEvent();
        if (event.isStartElement()) {
          String localPart = event.asStartElement().getName().getLocalPart();
          switch (localPart) {
            case ITEM:
              if (isFeedHeader) {
                isFeedHeader = false;
                feed =
                  new Feed(
                    title,
                    link,
                    description,
                    language,
                    copyright,
                    pubdate
                  );
              }
              event = eventReader.nextEvent();
              break;
            case TITLE:
              title = getCharacterData(event, eventReader);
              break;
            case DESCRIPTION:
              description = getCharacterData(event, eventReader);
              break;
            case LINK:
              link = getCharacterData(event, eventReader);
              break;
            case GUID:
              guid = getCharacterData(event, eventReader);
              break;
            case LANGUAGE:
              language = getCharacterData(event, eventReader);
              break;
            case AUTHOR:
              author = getCharacterData(event, eventReader);
              break;
            case PUB_DATE:
              pubdate = getCharacterData(event, eventReader);
              break;
            case COPYRIGHT:
              copyright = getCharacterData(event, eventReader);
              break;
            default:
              LOGGER.error("Could not parse element \" {} \"", localPart);
          }
        } else if (event.isEndElement()) {
          if (event.asEndElement().getName().getLocalPart() == (ITEM)) {
            FeedMessage message = new FeedMessage();
            message.setAuthor(author);
            message.setDescription(description);
            message.setGuid(guid);
            message.setLink(link);
            message.setTitle(title);
            feed.getMessages().add(message);
            event = eventReader.nextEvent();
            continue;
          }
        }
      }
    } catch (XMLStreamException e) {
      throw new RuntimeException(e);
    }
    return feed;
  }

  private String getCharacterData(XMLEvent event, XMLEventReader eventReader)
    throws XMLStreamException {
    String result = "";
    event = eventReader.nextEvent();
    if (event instanceof Characters) {
      result = event.asCharacters().getData();
    }
    LOGGER.info("xml output: {}", result);
    LOGGER.info("event: {}", event);
    LOGGER.info("event.toSting : {}", event.toString());
    return result;
  }

  private InputStream read() {
    try {
      return url.openStream();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
