package de.cloneworks.rssGenerator.webPageToRss.webPageParsing.document;

import de.cloneworks.rssGenerator.webPageToRss.util.MyLogger;
import de.cloneworks.rssGenerator.webPageToRss.webPageParsing.RssDataItemFactory;
import org.apache.commons.validator.UrlValidator;
import org.jsoup.internal.StringUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

public class SeleniumDocumentElement implements DocumentElement {
	private static final MyLogger LOGGER = new MyLogger(RssDataItemFactory.class);

	private final WebElement element;

	public SeleniumDocumentElement(WebElement element) {
		this.element = element;
	}

	@Override
	public DocumentElement findFirstElementByCss(String cssSelector) {
		DocumentElement result = null;

		WebElement foundElement = null;

		if(!StringUtil.isBlank(cssSelector) && !"<none>".equals(cssSelector)) {
			try {
				foundElement = element.findElement(By.cssSelector(cssSelector));
			} catch (NoSuchElementException ex) {
				LOGGER.warn(ex.getMessage() + ". HTML: " +
						"\n==================================================\n" +
						element.getAttribute("innerHTML") +
						"\n==================================================\n");
			}
		}

		if(foundElement != null) {
			result = new SeleniumDocumentElement(foundElement);
		}

		return result;
	}

	@Override
	public String getText() {
		return element.getText();
	}

	@Override
	public String getAbsoluteUrlFromAttribute(String attributeKey) {

		String attributeValue = element.getAttribute(attributeKey);
		return validateUrl(attributeValue);
	}

	@Override
	public String getAttributeValueFor(String attributeKey) {
		return element.getAttribute(attributeKey);
	}

	private String validateUrl(String unvalidatedUrl) {
		String result = "";

		String[] schemes = {"http","https"}; // DEFAULT schemes = "http", "https", "ftp"
		UrlValidator urlValidator = new UrlValidator(schemes);

		if (urlValidator.isValid(unvalidatedUrl)) {
			result = unvalidatedUrl;
		}

		return result;
	}
}
