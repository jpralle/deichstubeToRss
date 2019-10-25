package de.cloneworks.rssGenerator.webPageToRss.webPageParsing.selenium;

import de.cloneworks.rssGenerator.webPageToRss.util.MyLogger;
import de.cloneworks.rssGenerator.webPageToRss.webPageParsing.RssDataItemFactory;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.ArrayList;
import java.util.List;

public class WebDriverFactory {

	private static final MyLogger LOGGER = new MyLogger(WebDriverFactory.class);

	private static final List<WebDriver> allProducedDrivers = new ArrayList<>();

	public static WebDriver produceChromeDriver() {
		WebDriver result = null;

		String osSuffix = getOsSuffix();
		String absolutePathToChromeDriver = System.getProperty("user.dir") + "/chromedriver77.0.3865.40/chromedriver" + osSuffix;
		LOGGER.info("Using chromedriver at \"" + absolutePathToChromeDriver + "\".");
		System.setProperty("webdriver.chrome.driver", absolutePathToChromeDriver);

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

	private static String getOsSuffix() {
		String result = "_linux64";

		String os = System.getProperty("os.name").toLowerCase();
		if (os.contains("win")) {
			return "win32.exe";
		} else if (os.contains("mac")) {
			return "_mac64";
		} else if (os.contains("nux") || os.contains("nix")) {
			return "_linux64";
		}

		return result;
	}
}
