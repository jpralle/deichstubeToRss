package de.cloneworks.deichstubeToRss.parsing;

import de.cloneworks.deichstubeToRss.exceptions.InvalidParameterException;
import de.cloneworks.deichstubeToRss.logging.MyLogger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

public class DeichstubeNewsParser implements Iterable<NewsDataItem> {

    private static final MyLogger LOGGER = new MyLogger(DeichstubeNewsParser.class);

    public static final String DEICHSTUBE_URL = "https://www.deichstube.de";
    public static final String DEICHSTUBE_NEWS_URL = DEICHSTUBE_URL + "/news";
    private static final String NEWS_ITEM_CSS_SELECTOR = ".id-Teaser-el--prio";
    private static final int SITE_TIMEOUT_MILLIS = 20000;

    private Document doc = null;
    private String baseUrl = "default url";

    public DeichstubeNewsParser(String siteUrl) throws InvalidParameterException {
        doc = createDocument(siteUrl);
    }

    public DeichstubeNewsParser() throws InvalidParameterException {
        this(DEICHSTUBE_NEWS_URL);
    }

    public DeichstubeNewsParser(File htmlFile, String siteUrl) throws InvalidParameterException {
        doc = createDocument(htmlFile, siteUrl);
    }

    private Document createDocument(String siteUrl) throws InvalidParameterException {
        try {

            Document ret = Jsoup.connect(siteUrl).timeout(SITE_TIMEOUT_MILLIS).validateTLSCertificates(false).get();
            return ret;

        } catch (IOException e) {
            String msg = "Could not parse HTML from URL. URL: " + siteUrl + "Reason: " + e.getMessage();
            LOGGER.warn(msg, e);

            InvalidParameterException invalidParameterException = new InvalidParameterException(
                                            "Could not start parser, error parsing file. Reason: " + msg, e);
            throw invalidParameterException;
        }
    }

    private Document createDocument(File htmlFile, String siteUrl) throws InvalidParameterException {
        try {

            Document doc = Jsoup.parse(htmlFile, "UTF-8", siteUrl);
            return doc;

        } catch (IOException e) {
            String msg = "Could not parse HTML file. Path: " + htmlFile.getAbsolutePath() + "Reason: " + e.getMessage();
            LOGGER.warn(msg, e);

            InvalidParameterException invalidParameterException = new InvalidParameterException(
                                            "Could not start parser, error parsing file. Reason: " + msg, e);
            throw invalidParameterException;
        }
    }

    public int getNumberOfItems() {
        DeichstubeNewsIterator iterator = new DeichstubeNewsIterator(doc.select(NEWS_ITEM_CSS_SELECTOR));
        return iterator.getNumberOfItems();
    }

    @Override
    public Iterator<NewsDataItem> iterator() {
        DeichstubeNewsIterator iterator = new DeichstubeNewsIterator(doc.select(NEWS_ITEM_CSS_SELECTOR));
        return iterator;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getBaseUrl() {
        return this.baseUrl;
    }

}
