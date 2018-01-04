package de.cloneworks.deichstubeToRss.rss;

import com.rometools.rome.feed.synd.SyndContentImpl;
import com.rometools.rome.feed.synd.SyndEntryImpl;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.feed.synd.SyndFeedImpl;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedOutput;
import de.cloneworks.deichstubeToRss.parsing.DeichstubeNewsParser;
import de.cloneworks.deichstubeToRss.parsing.NewsDataItem;
import org.jdom2.Element;
import org.jdom2.Namespace;

import java.util.ArrayList;
import java.util.List;

public class RssGenerator {

    public static String generateFrom(DeichstubeNewsParser parser) throws FeedException {
        String ret = "";

        SyndFeed feed = new SyndFeedImpl();
        feed.setFeedType("rss_2.0");
        feed.setTitle("Inoffizieller Deichstube News RSS-Feed");
        feed.setDescription("Dieser Feed zeigt die News von der Deichstube als RSS-Feed an. Dieser Dienst ist inoffiziell und in keiner Weise " +
                "mit der Deichstube selbst verbunden. Updates stündlich. Geschrieben und betreut von Jan Pralle, https://twitter.com/derprallejan .");
        feed.setLink(DeichstubeNewsParser.DEICHSTUBE_NEWS_URL);

        Element atomLink = new Element("link", Namespace.getNamespace("atom", "http://www.w3.org/2005/Atom"));
        atomLink.setAttribute("href", parser.getBaseUrl());
        atomLink.setAttribute("rel", "self");
        atomLink.setAttribute("type", "application/rss+xml");
        feed.getForeignMarkup().add(atomLink);

        List entries = new ArrayList<SyndEntryImpl>();

        for(NewsDataItem item : parser) {

            SyndEntryImpl entry = new SyndEntryImpl();
            entry.setTitle(item.title);
            entry.setLink(item.link);
            entry.setUri(item.title);
            entry.setPublishedDate(item.publishTimestamp);

            SyndContentImpl description = new SyndContentImpl();
            description.setValue(item.description);
            entry.setDescription(description);

            Element image = new Element("content", Namespace.getNamespace("media", "http://search.yahoo.com/mrss/"));
            image.setAttribute("medium", "image");
            image.setAttribute("url", item.imageUrl);
            ((List<Element>)entry.getForeignMarkup()).add(image);

            entries.add(entry);
        }

        feed.setEntries(entries);

        ret = new SyndFeedOutput().outputString(feed);

        return ret;
    }
}
