package de.cloneworks.deichstubeToRss.parsing;

import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class DeichstubeNewsIterator implements Iterator<NewsDataItem> {

    protected final ArrayList<Element> newsElements;
    private int currentIndex = -1;

    public DeichstubeNewsIterator(ArrayList<Element> newsElements) {
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
    public NewsDataItem next() {
        currentIndex++;

        if(currentIndex >= newsElements.size()) {
            throw new NoSuchElementException("Item not found at Index " + currentIndex + " (list size: " + newsElements.size()+ ").");
        }

        return NewsDataItemFactory.produceFrom(newsElements.get(currentIndex));
    }
}
