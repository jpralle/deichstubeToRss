package de.cloneworks.rssGenerator.webPageToRss.webPageParsing;

import com.google.common.base.Predicate;
import de.cloneworks.rssGenerator.webPageToRss.RssDataConfiguration;
import de.cloneworks.rssGenerator.webPageToRss.util.InvalidParameterException;
import de.cloneworks.rssGenerator.webPageToRss.util.MyLogger;
import de.cloneworks.rssGenerator.webPageToRss.webPageParsing.document.WebDocument;
import de.cloneworks.rssGenerator.webPageToRss.webPageParsing.selenium.WebDriverFactory;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

public class WebParserForRss implements Iterable<RssDataItem> {

    private static final MyLogger LOGGER = new MyLogger(WebParserForRss.class);

    private static final int SITE_TIMEOUT_MILLIS = 20000;
    private final RssDataConfiguration config;

    private final WebDocument doc;
    private String baseUrl = "default url";

    public WebParserForRss(RssDataConfiguration config) throws InvalidParameterException {
        this.config = config;

        if(config.needsJavaScriptSupport()) {
            doc = createDocumentWithJavaScriptSupportFrom(config.getPageUrl());

        } else {
            doc = createDocumentWithoutJavaScriptSupportFrom(config.getPageUrl());
        }
    }

    public WebParserForRss(File htmlFile, RssDataConfiguration config) throws InvalidParameterException {
        this.config = config;
        doc = createDocumentWithoutJavaScriptSupportFrom(htmlFile, config.getPageUrl());
    }

    private WebDocument createDocumentWithoutJavaScriptSupportFrom(String siteUrl) throws InvalidParameterException {
        try {

            Document document = Jsoup.connect(siteUrl).timeout(SITE_TIMEOUT_MILLIS).get();//.validateTLSCertificates(false).get();
            WebDocument ret = new WebDocument(document);
            return ret;

        } catch (IOException e) {
            String msg = "Could not parse HTML from URL. URL: " + siteUrl + "Reason: " + e.getMessage();
            LOGGER.warn(msg, e);

            InvalidParameterException invalidParameterException = new InvalidParameterException(
                                            "Could not start parser, error webPageParsing file. Reason: " + msg, e);
            throw invalidParameterException;
        }
    }

    private WebDocument createDocumentWithJavaScriptSupportFrom(String siteUrl) throws InvalidParameterException {

        WebDriver driver = WebDriverFactory.produceChromeDriver();
        driver.get(siteUrl);
        waitForPageLoad(driver);

        WebDocument ret = new WebDocument(driver);

        return ret;

    }

    private boolean waitForPageLoad(WebDriver driver) {

        WebDriverWait wait = new WebDriverWait(driver, 30);

        // wait for jQuery to load
        ExpectedCondition<Boolean> jQueryLoad = new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver driver) {
                try {
                    return ((Long)((JavascriptExecutor)driver).executeScript("return jQuery.active") == 0);
                }
                catch (Exception e) {
                    return true;
                }
            }
        };

        // wait for Javascript to load
        ExpectedCondition<Boolean> jsLoad = new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver driver) {
                return ((JavascriptExecutor)driver).executeScript("return document.readyState").toString().equals("complete");
            }
        };

        return wait.until(jQueryLoad) && wait.until(jsLoad);


    }

    private WebDocument createDocumentWithoutJavaScriptSupportFrom(File htmlFile, String siteUrl) throws InvalidParameterException {
        try {

            Document document = Jsoup.parse(htmlFile, "UTF-8", siteUrl);
            WebDocument ret = new WebDocument(document);
            return ret;

        } catch (IOException e) {
            String msg = "Could not parse HTML file. Path: " + htmlFile.getAbsolutePath() + "Reason: " + e.getMessage();
            LOGGER.warn(msg, e);

            InvalidParameterException invalidParameterException = new InvalidParameterException(
                                            "Could not start parser, error webPageParsing file. Reason: " + msg, e);
            throw invalidParameterException;
        }
    }

    public int getNumberOfItems() {
        RssItemIterator iterator = new RssItemIterator(this.config, doc.findElementsByCss(this.config.getNewsItemCssSelector()));
        return iterator.getNumberOfItems();
    }

    @Override
    public Iterator<RssDataItem> iterator() {
        RssItemIterator iterator = new RssItemIterator(this.config, doc.findElementsByCss(this.config.getNewsItemCssSelector()));
        return iterator;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getBaseUrl() {
        return this.baseUrl;
    }

}
