package de.cloneworks.rssGenerator;

import de.cloneworks.rssGenerator.webPageToRss.webPageParsing.selenium.WebDriverFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RssGeneratorApplication {

	public static void main(String[] args) {
		SpringApplication.run(RssGeneratorApplication.class, args);
		WebDriverFactory.destroyAllDrivers();
	}

}
