package de.cloneworks.deichstubeToRss.rss;

import com.rometools.rome.io.FeedException;
import de.cloneworks.deichstubeToRss.parsing.DeichstubeNewsIterator;
import de.cloneworks.deichstubeToRss.parsing.DeichstubeNewsParser;
import de.cloneworks.deichstubeToRss.parsing.NewsDataItem;
import org.junit.Test;
import org.mockito.Mockito;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class RssGeneratorTest {

    private static final String EXPECTED_RSS = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n" +
            "<rss version=\"2.0\">\r\n" +
            "  <channel>\r\n" +
            "    <title>Inoffizieller Deichstube News RSS-Feed</title>\r\n" +
            "    <link>https://www.deichstube.de/news</link>\r\n" +
            "    <description>Dieser Feed zeigt die News von der Deichstube als RSS-Feed an. Dieser Dienst ist inoffiziell und in keiner Weise mit " +
                                "der Deichstube selbst verbunden. Updates stündlich. Geschrieben und betreut von Jan Pralle, " +
                                "https://twitter.com/derprallejan ." +
                "</description>\r\n" +
            "    <atom:link xmlns:atom=\"http://www.w3.org/2005/Atom\" href=\"default base url\" rel=\"self\" type=\"application/rss+xml\" />\r\n" +
            "    <item>\r\n" +
            "      <title>Item 1 Title</title>\r\n" +
            "      <link>Link to Item 1</link>\r\n" +
            "      <media:content xmlns:media=\"http://search.yahoo.com/mrss/\" medium=\"image\" url=\"Image URL of Item 1\" />\r\n" +
            "      <description>Item 1 Description, which is a bit more lenghty.</description>\r\n" +
            "      <guid isPermaLink=\"false\">Link to Item 1</guid>\r\n" +
            "    </item>\r\n" +
            "    <item>\r\n" +
            "      <title>Item 2 Title</title>\r\n" +
            "      <link>Link to Item 2</link>\r\n" +
            "      <media:content xmlns:media=\"http://search.yahoo.com/mrss/\" medium=\"image\" url=\"Image URL of Item 2\" />\r\n" +
            "      <description>Item 2 Description.</description>\r\n" +
            "      <guid isPermaLink=\"false\">Link to Item 2</guid>\r\n" +
            "    </item>\r\n" +
            "  </channel>\r\n" +
            "</rss>\r\n";

    @Test
    public void testGeneration() throws FeedException {

        NewsDataItem item1 = new NewsDataItem();
        item1.title = "Item 1 Title";
        item1.imageUrl = "Image URL of Item 1";
        item1.description = "Item 1 Description, which is a bit more lenghty.";
        item1.link = "Link to Item 1";

        NewsDataItem item2 = new NewsDataItem();
        item2.title = "Item 2 Title";
        item2.imageUrl = "Image URL of Item 2";
        item2.description = "Item 2 Description.";
        item2.link = "Link to Item 2";

        DeichstubeNewsIterator iterator = Mockito.mock(DeichstubeNewsIterator.class);
        Mockito.when(iterator.getNumberOfItems()).thenReturn(2);
        Mockito.when(iterator.hasNext()).thenReturn(true, true, false);
        Mockito.when(iterator.next()).thenReturn(item1, item2, null);

        DeichstubeNewsParser parserMock = Mockito.mock(DeichstubeNewsParser.class);
        Mockito.when(parserMock.getBaseUrl()).thenReturn("default base url");
        Mockito.when(parserMock.getNumberOfItems()).thenReturn(2);
        Mockito.when(parserMock.iterator()).thenReturn(iterator);

        String actual = RssGenerator.generateFrom(parserMock);

        assertThat(actual, is(EXPECTED_RSS));

    }
}
