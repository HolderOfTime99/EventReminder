package api;

import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

/**
 * This class interfaces with the newsapi.org API to retrieve the specified
 * categories of news articles and sources.
 */
public class NewsAPIGetter implements APIGetter<APIResult> {

    private String key;
    private String endpoint;
    public static final String API_URL = "http://newsapi.org";
    public static final int PAGE_SIZE = 5;
    public static final String DEFAULT_PARAMETER = "?q=coronavirus";

    /**
     * Returns an NewsAPIGetter that will be initialized with the given
     * authentication key, and will be hooked up to the specified endpoint.
     *
     * @param key A valid authentication key from https://newsapi.org
     * @param endpoint A String endpoint from the newsapi website.
     */
    public NewsAPIGetter(String key, String endpoint) {
        this.endpoint = endpoint;
        this.key = key;
    }

    /**
     * Queries the given endpoint of newsapi.org based on the given parameters.
     *
     * @param parameters A Map from newsapi.org parameter keywords to their values.
     * @return An array of Articles that are the results from querying the api with
     *         the given parameters.
     * @throws Exception throws an IllegalArgumentException when the parameters are
     *         not formatted to the newsapi.org requirements.
     */
    public Article[] query(Map<String, String> parameters) throws Exception {
        String paramString = paramString(parameters);
        URL url = new URL(API_URL + endpoint + paramString);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("X-Api-Key", key);
        InputStreamReader reader;
        try {
            reader = new InputStreamReader(con.getInputStream());
        } catch (Exception e) {
            con.disconnect();
            throw new IllegalArgumentException("The parameters passed in were not valid");
        }
        BufferedReader in = new BufferedReader(reader);
        String json = readQuery(in);
        return getArticleArray(json);
    }

    private Article[] getArticleArray(String jsonString) throws ParseException {
        JSONParser parser = new JSONParser();
        JSONObject obj = (JSONObject) parser.parse(jsonString);
        JSONArray arr = (JSONArray) obj.get("articles");
        int arrSize = Math.min(PAGE_SIZE, arr.size()); // just in case there are less than five entries
        Article[] result = new Article[arrSize];
        for (int i = 0; i < arrSize; i++) {
            JSONObject element = (JSONObject) arr.get(i);
            String author = (String) element.get("author");
            String url = (String) element.get("url");
            String title = (String) element.get("title");
            String description = (String) element.get("description");
            JSONObject sourceObject = (JSONObject) element.get("source");
            String source = (String) sourceObject.get("name");
            String dateTime = (String) element.get("publishedAt");
            result[i] = new Article(title, description, url, author, source, dateTime);
        }
        return result;
    }

    /**
     * Formats the parameters according to the newsapi.org requirements to be appended
     * to the API query URL.
     *
     * @param parameters A Map from newsapi.org parameter keywords to their values.
     * @return formatted String of parameters to be appended to the API URL
     */
    public String paramString(Map<String, String> parameters) {
        if (parameters.size() == 0) {
            return DEFAULT_PARAMETER;
        }
        String params = "?";
        int i = parameters.keySet().size();
        for (String type : parameters.keySet()) {
            params += type + "=" + parameters.get(type);
            if (i != 1) { params += "&"; }
            i--;
        }
        return params;
    }

    /**
     * Changes the authentication key for the newsapi.org API.
     *
     * @param newKey a String valid key from newsapi.org.
     */
    public void changeKey(String newKey) { this.key = newKey; }

    /**
     * Changes the endpoint to be queried from newsapi.org API.
     *
     * @param newEnd a String endpoint from newsapi.org.
     */
    public void setEndpoint(String newEnd) { this.endpoint = newEnd; }

}