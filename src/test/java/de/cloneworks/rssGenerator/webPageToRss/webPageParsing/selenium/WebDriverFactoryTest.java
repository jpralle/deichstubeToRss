package de.cloneworks.rssGenerator.webPageToRss.webPageParsing.selenium;

import org.junit.Test;

import static org.junit.Assert.*;

public class WebDriverFactoryTest {

	@Test
	public void testDriverLifecycleForExceptions() {

		WebDriverFactory.produceChromeDriver();
		WebDriverFactory.destroyAllDrivers();
	}

}