package api;

/**
 * This is a class that represents a simple result from an API call.
 */
public class APIResult {
    private String title;
    private String url;
    private String description;

    /**
     * Returns an API Result initialized with the given parameters.
     * @param title The title of the API result.
     * @param url The url to the result (must be non-null).
     * @param description A short description of the API result.
     */
    public APIResult(String title, String url, String description) {
        this.title = title;
        this.url = url;
        this.description = assignDescription(description);
    }

    private static String assignDescription(String newDescript) {
        if (newDescript == null) { newDescript = ""; }
        if (newDescript.length() > 150) { newDescript = newDescript.substring(0, 150) + "..."; } // shorter than 30 words
        return newDescript.equals("") || newDescript.startsWith(" <") ?  "N/A" : newDescript;
    }

    /**
     * Returns the title of the API result.
     * @return the String name of the result.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Returns the url link to the result.
     * @return returns a String URL that links to the result.
     */
    public String getUrl() {
        return url;
    }

    /**
     * Returns the description of the result.
     * @return returns a String description of the result.
     */
    public String getDescription() {
        return description;
    }
}
