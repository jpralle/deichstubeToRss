package de.cloneworks.deichstubeToRss;

import com.rometools.rome.io.FeedException;
import de.cloneworks.deichstubeToRss.exceptions.InvalidParameterException;
import de.cloneworks.deichstubeToRss.logging.MyLogger;
import de.cloneworks.deichstubeToRss.parsing.DeichstubeNewsParser;
import de.cloneworks.deichstubeToRss.parsing.NewsDataItem;
import de.cloneworks.deichstubeToRss.rss.RssGenerator;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Controller
public class DeichstubeToRssController {

    private static final MyLogger LOGGER = new MyLogger(DeichstubeToRssController.class);

    private static String lastRss = "";
    private static Date lastRssGenerationTime = null;

    @RequestMapping("/deichstuberss")
    @ResponseBody
    public String deichstuberss(HttpServletRequest request) {
        String ret = "error";

        if(cacheIsStillValid()) {
            return lastRss;
        }

        try {

            DeichstubeNewsParser parser = new DeichstubeNewsParser();
            parser.setBaseUrl(request.getLocalAddr() + "/deichstuberss");
            StringBuilder sb = new StringBuilder(parser.getNumberOfItems() + " items haven been found: \n");

            for(NewsDataItem item : parser) {
                sb.append(item);
                sb.append("\n");
            }

            LOGGER.debug(sb.toString());

            ret = RssGenerator.generateFrom(parser);

            saveRss(ret);

        } catch (InvalidParameterException e) {
            LOGGER.error("Error creating RSS. Reason: " + e, e);
        } catch (FeedException e) {
            LOGGER.error("Error writing RSS to String. Reason: " + e, e);
        }

        return ret;
    }

    private boolean cacheIsStillValid() {
        boolean ret = false;

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

    private void saveRss(String rss) {
        lastRssGenerationTime = Calendar.getInstance().getTime();
        lastRss = rss;
        LOGGER.info("Generated new cache");
    }


}
