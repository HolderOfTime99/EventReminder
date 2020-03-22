package api;

public class Article {
    private String title;
    private String description;
    private String url;
    private String author;
    private String source;

    public Article(String title, String description, String url, String author, String source) {
        this.title = title;
        this.description = description;
        this.url = url;
        this.author = author;
        this.source = source;
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
