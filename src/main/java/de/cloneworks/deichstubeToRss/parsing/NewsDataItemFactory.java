package de.cloneworks.deichstubeToRss.parsing;

import org.jsoup.nodes.Element;

public class NewsDataItemFactory {

    private static final String TITLE_CSS_SELECTOR = ".id-Teaser-el-content-headline-text";
    private static final String DESCRIPTION_CSS_SELECTOR = ".id-Teaser-el-content-text-text";
    private static final String IMAGE_CSS_SELECTOR = ".id-Teaser-el-image img";
    private static final String LINK_CSS_SELECTOR = ".id-LinkOverlay-link";

    public static NewsDataItem produceFrom(Element newsItemElement) {
        NewsDataItem ret = new NewsDataItem();

        ret.title = getTitle(newsItemElement);
        ret.description = getDescription(newsItemElement);
        ret.imageUrl = getImageUrl(newsItemElement);
        ret.link = getLink(newsItemElement);

        return ret;
    }

    private static String getTitle(Element newsItemElement) {
        Element titleElement = newsItemElement.selectFirst(TITLE_CSS_SELECTOR);
        if(titleElement != null) {
            return titleElement.text();
        } else {
            return "Title not found";
        }
    }

    private static String getDescription(Element newsItemElement) {
        Element descriptionElement = newsItemElement.selectFirst(DESCRIPTION_CSS_SELECTOR);
        if(descriptionElement != null) {
            return descriptionElement.text();
        } else {
            return "Description not found";
        }
    }

    private static String getImageUrl(Element newsItemElement) {
        Element imageElement = newsItemElement.selectFirst(IMAGE_CSS_SELECTOR);
        if(imageElement != null) {
            return imageElement.absUrl("src");
        } else {
            return "Image not found";
        }
    }

    private static String getLink(Element newsItemElement) {
        Element linkElement = newsItemElement.selectFirst(LINK_CSS_SELECTOR);
        if(linkElement != null) {
            return linkElement.absUrl("href");
        } else {
            return "Link not found";
        }
    }
}

//<div class="id-LinkOverlay id-Teaser-el id-Teaser-el--prio  id-Teaser-el--hasimage id-Teaser-el--prio3 id-Teaser-el--even   ">
    // <div class="id-Teaser-el-image-wrap">
        // <div class="id-Teaser-el-image">
            // <div class="id-RatioWrap id-RatioWrap--sixteenByNine ">
                // <div class="id-RatioWrap-content">
                    // <img srcset="//www.deichstube.de/bilder/2017/12/17/9456742/1180665021-florian-bruns-werder-bremen-2JlLzYZcf9.jpg 2200w,
                        //www.deichstube.de/bilder/2017/12/17/9456742/1180665021-florian-bruns-werder-bremen-2JlLzYZcac.jpg 342w,
                        //www.deichstube.de/bilder/2017/12/17/9456742/1180665021-florian-bruns-werder-bremen-2JlLzYZcGI.jpg 467w,
                        //www.deichstube.de/bilder/2017/12/17/9456742/1180665021-florian-bruns-werder-bremen-2JlLzYZc75.jpg 512w,
                        //www.deichstube.de/bilder/2017/12/17/9456742/1180665021-florian-bruns-werder-bremen-2JlLzYZc8e.jpg 640w,
                        //www.deichstube.de/bilder/2017/12/17/9456742/1180665021-florian-bruns-werder-bremen-2JlLzYZcBI.jpg 700w,
                        //www.deichstube.de/bilder/2017/12/17/9456742/1180665021-florian-bruns-werder-bremen-2JlLzYZcNG.jpg 1000w,
                        //www.deichstube.de/bilder/2017/12/17/9456742/1180665021-florian-bruns-werder-bremen-2JlLzYZca7.jpg 1024w,
                        //www.deichstube.de/bilder/2017/12/17/9456742/1180665021-florian-bruns-werder-bremen-2JlLzYZc6b.jpg 1536w" sizes="(min-width:900px) 1004px,
                        //(min-width:640px) 97vw, (min-width:480px) 96vw, 94vw " alt="Hinrunden-Abschluss weniger schlecht als befürchtet"
                        //title="Hinrunden-Abschluss weniger schlecht als befürchtet"
//imgSrc                //src="//www.deichstube.de/bilder/2017/12/17/9456742/1180665021-florian-bruns-werder-bremen-2JlLzYZca7.jpg">
                // </div>
            // </div>
        // </div>
    // </div>
    //<div class="id-Teaser-el-content  ">
        //<ul class="id-Teaser-el-related id-Teaser-el-related--posContentTop ">
            //<li class="id-Teaser-el-related-item id-Teaser-el-related-item-comments">
            //<a href="/news/ueber-winter-platz-16-werder-bremens-lage-nicht-ganz-so-schlimm-9456742.html#idAnchComments"
                //class="id-Teaser-el-related-item-comments-link " title="Kommentare zu diesem Artikel">
                //<span class="id-Teaser-el-related-item-comments-link-value">33</span>
                // <span class="id-Icon-hideText">Kommentare</span>
            // </a>
            //</li>
        //</ul>
        //<div class="id-Teaser-el-content-meta  ">
            //<span class="id-Teaser-el-content-meta-item id-Teaser-el-content-meta-item-date ">
                //<time class="id-DateTime id-DateTime--isBeforeXNotation" datetime="2017-12-18T07:00">vor 3 Stunden</time>
            //</span>
        //</div>
        //<h3 class="id-Teaser-el-content-headline ">
//title     //<span class="id-Teaser-el-content-headline-text">Hinrunden-Abschluss weniger schlecht als befürchtet</span>
        //</h3>
        //<div class="id-Teaser-el-content-text">
//desc      //<span class="id-Teaser-el-content-text-text">Bremen -&nbsp;Die Delegation, die am Morgen des 4. November an Gate A22 des Frankfurter
                //Flughafens erschien, war nicht gerade groß. Sechs, vielleicht sieben Männer, allesamt gekleidet in schwarzen Trainingsanzügen.</span>
        //</div>
    //</div>
//link//<a href="/news/ueber-winter-platz-16-werder-bremens-lage-nicht-ganz-so-schlimm-9456742.html" class="id-LinkOverlay-link "
        //title="Hinrunden-Abschluss weniger schlecht als befürchtet">Hinrunden-Abschluss weniger schlecht als befürchtet</a>
//</div>
