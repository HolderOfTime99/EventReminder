package api;

public class APIResult {
    private String title;
    private String url;
    private String description;

    public APIResult(String title, String url, String description) {
        this.title = title;
        this.url = url;
        this.description = assignDescription(description);
    }

    private static String assignDescription(String newDescript) {
        return newDescript == null || newDescript.equals("") || newDescript.startsWith(" <") ?  "N/A" : newDescript;
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
