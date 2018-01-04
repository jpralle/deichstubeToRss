package de.cloneworks.deichstubeToRss;

import de.cloneworks.deichstubeToRss.logging.MyLogger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class DeichstubeToRssApplicationTests {

	private static final MyLogger LOGGER = new MyLogger(DeichstubeToRssApplication.class);

	@Value(value = "classpath:deichstube-html/DeichStube_news_20171218.html")
	private Resource deichstubeNewsHtmlFrom20171218;
	@Autowired
	private MockMvc mockMvc;


	@Test
	public void contextLoads() {
	}


	@Test
	public void testIndex() throws Exception {
		MvcResult result = mockMvc.perform(get("/index.html")).andReturn();
		String content = result.getResponse().getContentAsString();

		assertThat(content, containsString("<h1>Deichstube RSS</h1>"));
		assertThat(content, containsString("<a href=\"/deichstuberss\">"));
	}

	@Test
	public void testForSomeContent() throws Exception {
		MvcResult result = mockMvc.perform(get("/deichstuberss")).andReturn();
		String content = result.getResponse().getContentAsString();

		LOGGER.info("Content: \n" + content);
		assertThat(content, containsString("<rss "));
		assertThat(content, containsString(" version=\"2.0\">"));
		assertThat(content, containsString("<item>"));
	}

}
