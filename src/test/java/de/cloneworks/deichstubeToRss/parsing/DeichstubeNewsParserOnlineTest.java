package de.cloneworks.deichstubeToRss.parsing;

import de.cloneworks.deichstubeToRss.exceptions.InvalidParameterException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest
public class DeichstubeNewsParserOnlineTest {

	@Test
	public void countNewsItemsInHtml() throws InvalidParameterException {

		ClassLoader classLoader = getClass().getClassLoader();
		File htmlFile = new File(classLoader.getResource("deichstube-html/DeichStube_news_20171218.html").getFile());

		DeichstubeNewsParser subject = new DeichstubeNewsParser(htmlFile, DeichstubeNewsParser.DEICHSTUBE_URL);

		assertThat(subject.getNumberOfItems(), is(9));
	}

	@Test
	public void readItemsFromHtml() throws InvalidParameterException {

		DeichstubeNewsParser subject = new DeichstubeNewsParser();

		assertThat(subject.getNumberOfItems(), is(9));

		for(NewsDataItem item : subject) {
			assertThat(item.title.length(), is(greaterThan(5)));
			assertThat(item.imageUrl, startsWith("https://www.deichstube.de/bilder/"));
			assertThat(item.imageUrl, endsWith(".jpg"));
			assertThat(item.description.length(), is(greaterThan(20)));
			assertThat(item.link, startsWith("https://www.deichstube.de/"));
			assertThat(item.link, endsWith(".html"));
		}

	}

}
