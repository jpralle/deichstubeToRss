package de.cloneworks.deichstubeToRss.parsing;

import java.util.Date;
import java.util.Objects;

public class NewsDataItem {

    public String title;
    public String imageUrl;
    public String description;
    public String link;
    public Date publishTimestamp;

    @Override
    public String toString() {
        return "NewsDataItem{" +
                "title='" + title + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", description='" + description + '\'' +
                ", link='" + link + '\'' +
                ", publishTimestamp='" + publishTimestamp + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NewsDataItem that = (NewsDataItem) o;
        return Objects.equals(title, that.title) &&
                Objects.equals(imageUrl, that.imageUrl) &&
                Objects.equals(description, that.description) &&
                Objects.equals(link, that.link) &&
                Objects.equals(publishTimestamp, that.publishTimestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, imageUrl, description, link, publishTimestamp);
    }
}
