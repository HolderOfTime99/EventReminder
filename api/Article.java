package api;

import java.util.Objects;

public class Article {
    private String title;
    private String description;
    private String url;
    private String author;
    private String source;
    private String date;


    public Article(String title, String description, String url, String author, String source, String date) {
        this.title = title;
        this.description = Objects.requireNonNullElse(description, "");
        this.url = url;
        this.author = Objects.requireNonNullElse(author, "");
        this.source = source;
        this.date = date;
    }

    @Override
    public String toString() {
        return "Article{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", url='" + url + '\'' +
                ", author='" + author + '\'' +
                ", source='" + source + '\'' +
                '}';
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
