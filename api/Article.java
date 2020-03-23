package api;

import java.util.Objects;

public class Article {
    private String title;
    private String description;
    private String url;
    private String author;
    private String source;
    private String date;
    private String time;


    public Article(String title, String description, String url, String author,
                   String source, String date, String time) {
        this.title = title;
        this.description = Objects.requireNonNullElse(description, "N/A");
        this.url = url;
        this.author = Objects.requireNonNullElse(author, "N/A");
        this.source = source;
        this.date = date;
        this.time = time;
    }

    @Override
    public String toString() {
        return "Article: " + title + "\n" +
                "Author: " + author + "\n" +
                "Source: " + source + "\n" +
                "Date: " + date + "\n" +
                "Time: " + time + "\n" +
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
