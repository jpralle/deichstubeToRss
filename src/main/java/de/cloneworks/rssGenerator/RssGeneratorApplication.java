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
		logArgs(args);

		SpringApplication.run(RssGeneratorApplication.class, args);
		WebDriverFactory.destroyAllDrivers();
	}

	public void run(ApplicationArguments args) throws Exception {

		if (args != null) {

			if(args.containsOption("chrome.binary.path")) {

				// To use this feature, start with:
				// mvn spring-boot:run -Dspring-boot.run.arguments=--chrome.binary.path=/path/to/chrome/binary
				String chromeBinaryPath = args.getOptionValues("chrome.binary.path").get(0);

				if (!chromeBinaryPath.equalsIgnoreCase("none")) {
					WebDriverFactory.chromeBinaryPath = Optional.of(chromeBinaryPath);
				} else {
					WebDriverFactory.chromeBinaryPath = Optional.empty();
				}
			}

			if(args.containsOption("chromeDriver.path")) {

				// To use this feature, start with:
				// mvn spring-boot:run -Dspring-boot.run.arguments=--chromeDriver.path=/path/to/chromeDriverBinary
				String chromeDriverPath = args.getOptionValues("chromeDriver.path").get(0);

				if (!chromeDriverPath.equalsIgnoreCase("none")) {
					WebDriverFactory.chromeDriverPath = Optional.of(chromeDriverPath);
				} else {
					WebDriverFactory.chromeDriverPath = Optional.empty();
				}
			}
		}
	}

	private static void logArgs(String[] args) {
		StringBuilder argsOutput = new StringBuilder("args: ");
		for (String arg : args) {
			argsOutput.append(arg + "; ");
		}
		LOGGER.info(argsOutput.toString());
	}

}
