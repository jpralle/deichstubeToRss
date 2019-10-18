package de.cloneworks.rssGenerator.webPageToRss;

import com.rometools.rome.io.FeedException;
import de.cloneworks.rssGenerator.webPageToRss.webPageParsing.RssDataItem;
import de.cloneworks.rssGenerator.webPageToRss.webPageParsing.WebParserForRss;
import de.cloneworks.rssGenerator.webPageToRss.rssGeneration.RssGenerator;
import de.cloneworks.rssGenerator.webPageToRss.util.InvalidParameterException;
import de.cloneworks.rssGenerator.webPageToRss.util.MyLogger;

import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

abstract public class AbstractRssController {

	private static final MyLogger LOGGER = new MyLogger(AbstractRssController.class);

	private static Map<String, String> lastRssMap = new HashMap<>();
	private static Map<String, Date> lastRssGenerationTimeMap = new HashMap<>();

	protected String rssFor(HttpServletRequest request, RssDataConfiguration config) {
		String ret = "error";

		if(cacheIsStillValid(config.getLocalBaseUrl())) {
			return lastRssMap.get(config.getLocalBaseUrl());
		}

		try {

			WebParserForRss parser = new WebParserForRss(config);
			parser.setBaseUrl(request.getLocalAddr() + config.getLocalBaseUrl());
			StringBuilder sb = new StringBuilder(parser.getNumberOfItems() + " items haven been found: \n");

			for(RssDataItem item : parser) {
				sb.append(item);
				sb.append("\n");
			}

			LOGGER.debug(sb.toString());

			ret = RssGenerator.generateFrom(parser, config);

			saveRss(config.getLocalBaseUrl(), ret);

		} catch (InvalidParameterException e) {
			LOGGER.error("Error creating RSS. Reason: " + e, e);
		} catch (FeedException e) {
			LOGGER.error("Error writing RSS to String. Reason: " + e, e);
		}

		return ret;
	}

	private boolean cacheIsStillValid(String cacheKey) {
		boolean ret = false;

		Date lastRssGenerationTime = lastRssGenerationTimeMap.get(cacheKey);
		if(lastRssGenerationTime != null) {
			Date now = Calendar.getInstance().getTime();
			long diffInMillis = now.getTime() - lastRssGenerationTime.getTime();

			if(diffInMillis >= 0 && diffInMillis < 3600000) {
				ret = true;
				DateFormat dateFormat = new SimpleDateFormat();
				LOGGER.info("Using cache from " + dateFormat.format(lastRssGenerationTime));
			}
		}

		return ret;
	}

	private void saveRss(String cacheKey, String rss) {
		lastRssGenerationTimeMap.put(cacheKey, Calendar.getInstance().getTime());
		lastRssMap.put(cacheKey, rss);
		LOGGER.info("Generated new cache for \"" + cacheKey + "\".");
	}
}
