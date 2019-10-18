package de.cloneworks.rssGenerator.webPageToRss.webPageParsing;

import de.cloneworks.rssGenerator.webPageToRss.RssDataConfiguration;
import de.cloneworks.rssGenerator.webPageToRss.webPageParsing.document.DocumentElement;
import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class RssItemIterator implements Iterator<RssDataItem> {

    private final RssDataConfiguration config;
    private final List<DocumentElement> newsElements;
    private int currentIndex = -1;

    public RssItemIterator(RssDataConfiguration config, List<DocumentElement> newsElements) {
        this.config = config;
        this.newsElements = newsElements;
    }

    public int getNumberOfItems() {

        if(newsElements == null) {
            return 0;
        } else {
            return newsElements.size();
        }
    }

    @Override
    public boolean hasNext() {
        return currentIndex+1 < newsElements.size();
    }

    @Override
    public RssDataItem next() {
        currentIndex++;

        if(currentIndex >= newsElements.size()) {
            throw new NoSuchElementException("Item not found at Index " + currentIndex + " (list size: " + newsElements.size()+ ").");
        }

        RssDataItemFactory itemFactory = new RssDataItemFactory(config);
        return itemFactory.produceFrom(newsElements.get(currentIndex));
    }
}
