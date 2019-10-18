package de.cloneworks.rssGenerator.webPageToRss;

public class RssDataConfiguration {

	public static final RssDataConfiguration EMPTY = new RssDataConfiguration("", false, "", "", "", "", "", "", "", "", "");

	public static final RssDataConfiguration DEICHSTUBE_NEWS_CONFIG =
			new RssDataConfiguration("https://www.deichstube.de/news",
					false,
					"Inoffizieller Deichstube-News-RSS-Feed",
					"Dieser Feed zeigt die News von der Deichstube als RSS-Feed an. Dieser Dienst ist " +
							"inoffiziell und in keiner Weise mit der Deichstube selbst verbunden. Updates " +
							"stündlich. Geschrieben und betreut von Jan Pralle, https://twitter.com/derprallejan.",
					".id-Teaser-el--prio",
					".id-Teaser-el-content-headline-text",
					".id-Teaser-el-content-text-text",
					".id-Teaser-el-image img",
					".id-LinkOverlay-link",
					".id-DateTime",
					"/deichstuberss");

	public static final RssDataConfiguration SUBSTAGE_CONCERT_CONFIG =
			new RssDataConfiguration("https://substage.de/programm/",
					true,
					"Inoffizieller Substage-Konzerte-RSS-Feed",
					"Dieser Feed zeigt die Konzerte des Substage Karlsruhe als RSS-Feed an. Dieser Dienst ist " +
							"inoffiziell und in keiner Weise mit dem Substage selbst verbunden. Updates " +
							"stündlich. Geschrieben und betreut von Jan Pralle, https://twitter.com/derprallejan.",
					"div.row.mod_kalender_element",
					"div.mod_kalender_title_txt h2",
					"div.EventKurzinfo",
					"div.coverImage-sizer",
					"div.row a",
					"<none>",
				    "/substageconcertsrss");

	private final String pageUrl;
	private final boolean needsJavaScriptSupport;

	private final String feedTitle;
	private final String feedDescription;

	private final String newsItemCssSelector;
	private final String titleCssSelector;
	private final String descriptionCssSelector;
	private final String imageCssSelector;
	private final String linkCssSelector;
	private final String publishTimestampCssSelector;

	private final String localBaseUrl;

	public RssDataConfiguration(String pageUrl,
								boolean needsJavaScriptSupport,
								String feedTitle,
								String feedDescription,
								String newsItemCssSlector,
								String titleCssSelector,
								String descriptionCssSelector,
								String imageCssSelector,
								String linkCssSelector,
								String publishTimestampCssSelector,
								String localBaseUrl) {

		this.pageUrl = pageUrl;
		this.needsJavaScriptSupport = needsJavaScriptSupport;
		this.feedTitle = feedTitle;
		this.feedDescription = feedDescription;
		this.newsItemCssSelector = newsItemCssSlector;
		this.titleCssSelector = titleCssSelector;
		this.descriptionCssSelector = descriptionCssSelector;
		this.imageCssSelector = imageCssSelector;
		this.linkCssSelector = linkCssSelector;
		this.publishTimestampCssSelector = publishTimestampCssSelector;
		this.localBaseUrl = localBaseUrl;
	}

	public String getFeedTitle() {
		return feedTitle;
	}

	public boolean needsJavaScriptSupport() {
		return needsJavaScriptSupport;
	}

	public String getFeedDescription() {
		return feedDescription;
	}

	public String getPageUrl() {
		return pageUrl;
	}

	public String getNewsItemCssSelector() {
		return newsItemCssSelector;
	}

	public String getTitleCssSelector() {
		return titleCssSelector;
	}

	public String getDescriptionCssSelector() {
		return descriptionCssSelector;
	}

	public String getImageCssSelector() {
		return imageCssSelector;
	}

	public String getLinkCssSelector() {
		return linkCssSelector;
	}

	public String getPublishTimestampCssSelector() {
		return publishTimestampCssSelector;
	}

	public String getLocalBaseUrl() {
		return localBaseUrl;
	}
}
