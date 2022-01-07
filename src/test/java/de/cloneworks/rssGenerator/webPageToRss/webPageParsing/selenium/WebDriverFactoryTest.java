package de.cloneworks.rssGenerator.webPageToRss.webPageParsing.selenium;

import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.*;

public class WebDriverFactoryTest {

	@Test
	@Ignore("Works only with fitting chromedriver version to local chrome, thus is unstable. "
		+ "Use at your own discretion.")
	public void testDriverLifecycleForExceptions() {

		WebDriverFactory.produceChromeDriver();
		WebDriverFactory.destroyAllDrivers();
	}

}
