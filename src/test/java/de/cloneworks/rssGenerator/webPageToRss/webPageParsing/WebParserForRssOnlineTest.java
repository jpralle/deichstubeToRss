package de.cloneworks.rssGenerator.webPageToRss.webPageParsing;

import de.cloneworks.rssGenerator.webPageToRss.RssDataConfiguration;
import de.cloneworks.rssGenerator.webPageToRss.util.InvalidParameterException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest
public class WebParserForRssOnlineTest {

	@Test
	public void countNewsItemsInHtml() throws InvalidParameterException {

		ClassLoader classLoader = getClass().getClassLoader();
		File htmlFile = new File(classLoader.getResource("deichstube-html/DeichStube_news_20171218.html").getFile());

		WebParserForRss subject = new WebParserForRss(htmlFile, RssDataConfiguration.DEICHSTUBE_NEWS_CONFIG);

		assertThat(subject.getNumberOfItems(), is(9));
	}

	@Test
	public void readItemsFromHtml() throws InvalidParameterException {

		WebParserForRss subject = new WebParserForRss(RssDataConfiguration.DEICHSTUBE_NEWS_CONFIG);

		assertThat(subject.getNumberOfItems(), is(9));

		for(RssDataItem item : subject) {
			assertThat(item.title.length(), is(greaterThan(5)));
			assertThat(item.imageUrl, startsWith("https://www.deichstube.de/bilder/"));
			assertThat(item.imageUrl, endsWith(".jpg"));
			assertThat(item.description.length(), is(greaterThan(20)));
			assertThat(item.link, startsWith("https://www.deichstube.de/"));
			assertThat(item.link, endsWith(".html"));
		}

	}

}
