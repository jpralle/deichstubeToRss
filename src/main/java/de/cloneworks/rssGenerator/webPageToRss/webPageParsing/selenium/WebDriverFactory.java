package de.cloneworks.rssGenerator.webPageToRss.webPageParsing.selenium;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.ArrayList;
import java.util.List;

public class WebDriverFactory {

	private static final List<WebDriver> allProducedDrivers = new ArrayList<>();

	public static WebDriver produceChromeDriver() {
		WebDriver result = null;

		String absoluitePathToChromeDriver = System.getProperty("user.dir") + "/chromedriver77.0.3865.40/chromedriver_mac64";
		System.setProperty("webdriver.chrome.driver", absoluitePathToChromeDriver);

		ChromeOptions options = new ChromeOptions();
		options.addArguments("--headless");

		result = new ChromeDriver(options);

		allProducedDrivers.add(result);
		return result;
	}

	public static void destroyAllDrivers() {

		for(WebDriver driver : allProducedDrivers) {
			driver.quit();
		}
		allProducedDrivers.clear();
	}
}
