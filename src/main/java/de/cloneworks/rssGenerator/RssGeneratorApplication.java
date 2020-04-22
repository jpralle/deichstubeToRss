package de.cloneworks.rssGenerator;

import de.cloneworks.rssGenerator.webPageToRss.util.MyLogger;
import de.cloneworks.rssGenerator.webPageToRss.webPageParsing.WebParserForRss;
import de.cloneworks.rssGenerator.webPageToRss.webPageParsing.selenium.WebDriverFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;
import java.util.Optional;

@SpringBootApplication
public class RssGeneratorApplication implements ApplicationRunner {
	private static final MyLogger LOGGER = new MyLogger(RssGeneratorApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(RssGeneratorApplication.class, args);
		WebDriverFactory.destroyAllDrivers();
	}

	public void run(ApplicationArguments args) throws Exception {
		if (args != null && args.getOptionValues("chrome.binary.path") != null) {

			// To use this ferature, start with:
			// mvn spring-boot:run -Dspring-boot.run.arguments=--chrome.binary.path=testmepath
			String chromeBinaryPath = args.getOptionValues("chrome.binary.path").get(0);

			if (!chromeBinaryPath.equalsIgnoreCase("none")) {
				WebDriverFactory.chromeBinaryPath = Optional.of(chromeBinaryPath);
			} else {
				WebDriverFactory.chromeBinaryPath = Optional.empty();
			}

		}
	}

}
