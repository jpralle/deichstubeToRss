package de.cloneworks.rssGenerator.webPageToRss.webPageParsing.document;

public interface DocumentElement {

	DocumentElement findFirstElementByCss(String cssSelector);

	String getText();

	String getAbsoluteUrlFromAttribute(String attributeKey);

	String getAttributeValueFor(String attributeKey);

}
