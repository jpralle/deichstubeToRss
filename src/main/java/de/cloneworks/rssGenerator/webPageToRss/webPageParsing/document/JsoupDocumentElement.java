package de.cloneworks.rssGenerator.webPageToRss.webPageParsing.document;

import org.jsoup.nodes.Element;

public class JsoupDocumentElement implements DocumentElement {

	private final Element element;

	public JsoupDocumentElement(Element element) {
		this.element = element;
	}

	@Override
	public DocumentElement findFirstElementByCss(String cssSelector) {
		DocumentElement result = null;

		Element foundElement = element.selectFirst(cssSelector);

		if(foundElement != null) {
			result = new JsoupDocumentElement(foundElement);
		}

		return result;
	}

	@Override
	public String getText() {
		return element.text();
	}

	@Override
	public String getAbsoluteUrlFromAttribute(String attributeKey) {
		return element.absUrl(attributeKey);
	}

	@Override
	public String getAttributeValueFor(String attributeKey) {
		return element.attr(attributeKey);
	}

}
