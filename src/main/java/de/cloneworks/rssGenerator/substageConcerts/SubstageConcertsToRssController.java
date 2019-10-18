package de.cloneworks.rssGenerator.substageConcerts;

import de.cloneworks.rssGenerator.webPageToRss.RssDataConfiguration;
import de.cloneworks.rssGenerator.webPageToRss.util.MyLogger;
import de.cloneworks.rssGenerator.webPageToRss.AbstractRssController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
public class SubstageConcertsToRssController extends AbstractRssController {

    private static final MyLogger LOGGER = new MyLogger(SubstageConcertsToRssController.class);

    @RequestMapping("/substageconcertsrss")
    @ResponseBody
    public String substageconcertsrss(HttpServletRequest request) {
        return rssFor(request, RssDataConfiguration.SUBSTAGE_CONCERT_CONFIG);
    }

}
