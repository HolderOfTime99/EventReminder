package api;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Article extends APIResult {
    private String author;
    private String source;
    private ZonedDateTime time;
    private final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("HH:mm:ss");


    public Article(String title, String description, String url, String author,
                   String source, String time) {
        super(title, url, description);
        this.author = Objects.requireNonNullElse(author, "N/A");
        this.source = source;
        this.time = ZonedDateTime.parse(time);
    }

    @Override
    public String toString() {
        return "Article: " + getTitle() + "\n" +
                "Author: " + author + "\n" +
                "Source: " + source + "\n" +
                "Date: " + time.format(DATE_FORMAT) + "\n" +
                "Time: " + time.format(TIME_FORMAT) + "\n" +
                "Description: " +  getDescription() + "\n" +
                "URL:" + getUrl() + "\n";
    }

    public String getAuthor() {
        return author;
    }

    public String getSource() {
        return source;
    }

}
