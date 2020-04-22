package de.cloneworks.rssGenerator.deichstubeNews;

import de.cloneworks.rssGenerator.webPageToRss.AbstractRssController;
import de.cloneworks.rssGenerator.webPageToRss.RssDataConfiguration;
import de.cloneworks.rssGenerator.webPageToRss.util.MyLogger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
public class DeichstubeNewsRssController extends AbstractRssController {

    private static final MyLogger LOGGER = new MyLogger(DeichstubeNewsRssController.class);

    @RequestMapping("/deichstuberss")
    @ResponseBody
    public String deichstuberss(HttpServletRequest request) {
        return rssFor(request, RssDataConfiguration.DEICHSTUBE_NEWS_CONFIG);
    }

}
