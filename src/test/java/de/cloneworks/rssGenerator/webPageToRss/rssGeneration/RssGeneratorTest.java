package de.cloneworks.rssGenerator.webPageToRss.rssGeneration;

import com.rometools.rome.io.FeedException;
import de.cloneworks.rssGenerator.webPageToRss.RssDataConfiguration;
import de.cloneworks.rssGenerator.webPageToRss.webPageParsing.RssItemIterator;
import de.cloneworks.rssGenerator.webPageToRss.webPageParsing.WebParserForRss;
import de.cloneworks.rssGenerator.webPageToRss.webPageParsing.RssDataItem;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Calendar;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class RssGeneratorTest {

    private static final String EXPECTED_RSS = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n" +
            "<rss xmlns:dc=\"http://purl.org/dc/elements/1.1/\" version=\"2.0\">\r\n" +
            "  <channel>\r\n" +
            "    <title />\r\n" +
            "    <link />\r\n" +
            "    <description />\r\n" +
            "    <atom:link xmlns:atom=\"http://www.w3.org/2005/Atom\" href=\"default base url\" rel=\"self\" type=\"application/webPageToRss+xml\" />\r\n" +
            "    <item>\r\n" +
            "      <title>Item 1 Title</title>\r\n" +
            "      <link>Link to Item 1</link>\r\n" +
            "      <media:content xmlns:media=\"http://search.yahoo.com/mrss/\" medium=\"image\" url=\"Image URL of Item 1\" />\r\n" +
            "      <description>Item 1 Description, which is a bit more lenghty.</description>\r\n" +
            "      <pubDate>Sat, 01 Jan 0001 01:01:00 GMT</pubDate>\r\n" +
            "      <guid isPermaLink=\"false\">Link to Item 1</guid>\r\n" +
            "      <dc:date>0001-01-01T01:01:00Z</dc:date>\r\n" +
            "    </item>\r\n" +
            "    <item>\r\n" +
            "      <title>Item 2 Title</title>\r\n" +
            "      <link>Link to Item 2</link>\r\n" +
            "      <media:content xmlns:media=\"http://search.yahoo.com/mrss/\" medium=\"image\" url=\"Image URL of Item 2\" />\r\n" +
            "      <description>Item 2 Description.</description>\r\n" +
            "      <pubDate>Thu, 02 Feb 0002 02:02:00 GMT</pubDate>\r\n" +
            "      <guid isPermaLink=\"false\">Link to Item 2</guid>\r\n" +
            "      <dc:date>0002-02-02T02:02:00Z</dc:date>\r\n" +
            "    </item>\r\n" +
            "  </channel>\r\n" +
            "</rss>\r\n";

    @Test
    public void testGeneration() throws FeedException {

        RssDataItem item1 = new RssDataItem();
        item1.title = "Item 1 Title";
        item1.imageUrl = "Image URL of Item 1";
        item1.description = "Item 1 Description, which is a bit more lenghty.";
        item1.link = "Link to Item 1";
        Calendar cal1 = Calendar.getInstance();
        cal1.set(1,0,1,2,1,0);
        item1.publishTimestamp = cal1.getTime();

        RssDataItem item2 = new RssDataItem();
        item2.title = "Item 2 Title";
        item2.imageUrl = "Image URL of Item 2";
        item2.description = "Item 2 Description.";
        item2.link = "Link to Item 2";
        Calendar cal2 = Calendar.getInstance();
        cal2.set(2,1,2,3,2,0);
        item2.publishTimestamp = cal2.getTime();

        RssItemIterator iterator = Mockito.mock(RssItemIterator.class);
        Mockito.when(iterator.getNumberOfItems()).thenReturn(2);
        Mockito.when(iterator.hasNext()).thenReturn(true, true, false);
        Mockito.when(iterator.next()).thenReturn(item1, item2, null);

        WebParserForRss parserMock = Mockito.mock(WebParserForRss.class);
        Mockito.when(parserMock.getBaseUrl()).thenReturn("default base url");
        Mockito.when(parserMock.getNumberOfItems()).thenReturn(2);
        Mockito.when(parserMock.iterator()).thenReturn(iterator);

        String actual = RssGenerator.generateFrom(parserMock, RssDataConfiguration.EMPTY);

        assertThat(actual, is(EXPECTED_RSS));

    }
}
