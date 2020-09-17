package de.cloneworks.rssGenerator.webPageToRss.webPageParsing.selenium;

import de.cloneworks.rssGenerator.webPageToRss.util.MyLogger;
import de.cloneworks.rssGenerator.webPageToRss.webPageParsing.RssDataItemFactory;
import org.jsoup.internal.StringUtil;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Value;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class WebDriverFactory {
	private static final MyLogger LOGGER = new MyLogger(WebDriverFactory.class);

	public static Optional<String> chromeBinaryPath = Optional.empty();

	private static final List<WebDriver> allProducedDrivers = new ArrayList<>();

	public static WebDriver produceChromeDriver() {
		WebDriver result = null;

		if(allProducedDrivers.size() > 0) {
			result = allProducedDrivers.get(0);
		} else {

			String osSuffix = getOsSuffix();
			String absolutePathToChromeDriver = System.getProperty("user.dir") + "/chromedriver_85.0.4183.87/chromedriver" + osSuffix;
			LOGGER.info("Using chromedriver at \"" + absolutePathToChromeDriver + "\".");
			System.setProperty("webdriver.chrome.driver", absolutePathToChromeDriver);

			ChromeOptions options = new ChromeOptions();
			options.addArguments("--headless");

			if (chromeBinaryPath.isPresent()) {
				LOGGER.info("Using Chrome binary at \"" + chromeBinaryPath.get() + "\".");
				options.setBinary(chromeBinaryPath.get());
			} else {
				LOGGER.info("No Chrome binary path given, using default Chrome instance");
			}

			result = new ChromeDriver(options);

			allProducedDrivers.add(result);
		}

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
		LOGGER.info("Detected OS name: " + os);
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
