package api;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * This is an object that represents an article returned by an API call
 */
public class Article extends APIResult {
    private String author;
    private String source;
    private ZonedDateTime time;
    private final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("HH:mm:ss");

    /**
     *
     * @param title Title of the article.
     * @param description A short description of the article.
     * @param url A link to the article on the source's webpage.
     * @param author The author of the article.
     * @param source The news outlet that sourced the article.
     * @param time The time the article was published.
     */
    public Article(String title, String description, String url, String author,
                   String source, String time) {
        super(title, url, description);
        this.author = Objects.requireNonNullElse(author, "N/A");
        this.source = source;
        this.time = ZonedDateTime.parse(time);
    }

    /**
     * Returns a String representation of the article.
     * @return String with all article information formatted on each line.
     */
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

    /**
     * Returns the author of the article.
     * @return returns the String name of the author.
     */
    public String getAuthor() {
        return author;
    }

    /**
     * Returns the source of the article.
     * @return returns the String source name of the article.
     */
    public String getSource() {
        return source;
    }

}
