package de.cloneworks.rssGenerator.webPageToRss.webPageParsing;

import de.cloneworks.rssGenerator.webPageToRss.RssDataConfiguration;
import de.cloneworks.rssGenerator.webPageToRss.util.MyLogger;
import de.cloneworks.rssGenerator.webPageToRss.webPageParsing.document.DocumentElement;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class RssDataItemFactory {

    private static final MyLogger LOGGER = new MyLogger(RssDataItemFactory.class);

    private final RssDataConfiguration config;

    public RssDataItemFactory(RssDataConfiguration config) {
        this.config = config;
    }

    public RssDataItem produceFrom(DocumentElement newsItemElement) {
        RssDataItem ret = new RssDataItem();

        ret.title = getTitle(newsItemElement);
        ret.description = getDescription(newsItemElement);
        ret.imageUrl = getImageUrl(newsItemElement);
        ret.link = getLink(newsItemElement);
        ret.publishTimestamp = getPublishTimestamp(newsItemElement);

        return ret;
    }

    private String getTitle(DocumentElement newsItemElement) {
        DocumentElement titleElement = newsItemElement.findFirstElementByCss(config.getTitleCssSelector());
        if(titleElement != null) {
            return titleElement.getText();
        } else {
            return "Title not found";
        }
    }

    private String getDescription(DocumentElement newsItemElement) {
        DocumentElement descriptionElement = newsItemElement.findFirstElementByCss(config.getDescriptionCssSelector());
        if(descriptionElement != null) {
            return descriptionElement.getText();
        } else {
            return "Description not found";
        }
    }

    private String getImageUrl(DocumentElement newsItemElement) {
        DocumentElement imageElement = newsItemElement.findFirstElementByCss(config.getImageCssSelector());
        if(imageElement != null) {
            return imageElement.getAbsoluteUrlFromAttribute("src");
        } else {
            return "Image not found";
        }
    }

    private String getLink(DocumentElement newsItemElement) {
        DocumentElement linkElement = newsItemElement.findFirstElementByCss(config.getLinkCssSelector());
        if(linkElement != null) {
            return linkElement.getAbsoluteUrlFromAttribute("href");
        } else {
            return "Link not found";
        }
    }

    private Date getPublishTimestamp(DocumentElement newsItemElement) {
        Date result = Calendar.getInstance().getTime();
        DocumentElement timestampElement = newsItemElement.findFirstElementByCss(config.getPublishTimestampCssSelector());

        if(timestampElement != null) {
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm", Locale.GERMAN);

            String timestampString = timestampElement.getAttributeValueFor("datetime");

            try {
                result = format.parse(timestampString);
            } catch (ParseException e) {
                LOGGER.info("Error webPageParsing datetime string \"" + timestampString + "\". Returning \"now\". Message: " + e.getMessage(), e);
            }
        }

        return result;
    }
}

