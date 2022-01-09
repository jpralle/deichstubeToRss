package de.cloneworks.rssGenerator;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import de.cloneworks.rssGenerator.webPageToRss.util.MyLogger;
import de.cloneworks.rssGenerator.webPageToRss.webPageParsing.selenium.WebDriverFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.core.io.Resource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

public class RssGeneratorApplicationStartTests {

	@Test
	public void testStartParameters() {
		String chromeBinaryPath = "/chrome/binary/path";
		String chromeDriverPath = "/chromeDriver/path";
		RssGeneratorApplication.main(new String[]{"--chrome.binary.path=" + chromeBinaryPath,
			"--chromeDriver.path=" + chromeDriverPath});

		assertThat(WebDriverFactory.chromeBinaryPath.isPresent(), is(true));
		assertThat(WebDriverFactory.chromeBinaryPath.get(), is(chromeBinaryPath));

		assertThat(WebDriverFactory.chromeDriverPath.isPresent(), is(true));
		assertThat(WebDriverFactory.chromeDriverPath.get(), is(chromeDriverPath));
	}

	@Test
	public void testStartParameterForChromeBinaryPath() {
		String chromeBinaryPath = "/chrome/binary/path";
		RssGeneratorApplication.main(new String[]{"--chrome.binary.path=" + chromeBinaryPath});

		assertThat(WebDriverFactory.chromeBinaryPath.isPresent(), is(true));
		assertThat(WebDriverFactory.chromeBinaryPath.get(), is(chromeBinaryPath));
	}

	@Test
	public void testStartParameterForChromeDriverPath() {
		String chromeDriverPath = "/chromeDriver/path";
		RssGeneratorApplication.main(new String[]{"--chromeDriver.path=" + chromeDriverPath});

		assertThat(WebDriverFactory.chromeDriverPath.isPresent(), is(true));
		assertThat(WebDriverFactory.chromeDriverPath.get(), is(chromeDriverPath));
	}

}
