package api;

import java.util.Objects;

public class APIResult {
    private String title;
    private String url;
    private String description;

    public APIResult(String title, String url, String description) {
        this.title = title;
        this.url = url;
        this.description = Objects.requireNonNullElse(description, "N/A");
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    public String getDescription() {
        return description;
    }
}
