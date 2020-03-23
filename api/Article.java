package api;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Article {
    private String title;
    private String description;
    private String url;
    private String author;
    private String source;
    private ZonedDateTime time;
    private final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("HH:mm:ss");


    public Article(String title, String description, String url, String author,
                   String source, String time) {
        this.title = title;
        this.description = Objects.requireNonNullElse(description, "N/A");
        this.url = url;
        this.author = Objects.requireNonNullElse(author, "N/A");
        this.source = source;
        this.time = ZonedDateTime.parse(time);
    }

    @Override
    public String toString() {
        return "Article: " + title + "\n" +
                "Author: " + author + "\n" +
                "Source: " + source + "\n" +
                "Date: " + time.format(DATE_FORMAT) + "\n" +
                "Time: " + time.format(TIME_FORMAT) + "\n" +
                "Description: " +  description + "\n" +
                "URL:" + url + "\n";
    }

    public String getAuthor() {
        return author;
    }

    public String getSource() {
        return source;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getUrl() {
        return url;
    }
}
