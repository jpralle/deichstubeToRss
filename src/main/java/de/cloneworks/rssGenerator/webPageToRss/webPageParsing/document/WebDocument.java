package de.cloneworks.rssGenerator.webPageToRss.webPageParsing.document;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class WebDocument {

	private final boolean useJsoup;
	private final Document jsoupDocument;
	private final WebDriver seleniumDriver;

	public WebDocument(Document doc) {
		this.useJsoup = true;
		this.jsoupDocument = doc;
		this.seleniumDriver = null;
	}

	public WebDocument(WebDriver driver) {
		this.useJsoup = false;
		this.seleniumDriver = driver;
		this.jsoupDocument = null;
	}

	public List<DocumentElement> findElementsByCss(String cssSelector) {
		List<DocumentElement> result = new ArrayList<>();

		if (useJsoup) {
			Elements selectedElements = jsoupDocument.select(cssSelector);

			for (Element element : selectedElements) {
				result.add(new JsoupDocumentElement(element));
			}

		} else {
			List<WebElement> foundElements = seleniumDriver.findElements(By.cssSelector(cssSelector));

			for(WebElement element : foundElements) {
				result.add(new SeleniumDocumentElement(element));
			}
		}

		return result;
	}
}
