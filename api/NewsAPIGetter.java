package api;

import java.util.*;
import java.io.*;
import java.net.*;
import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class NewsAPIGetter implements APIGetter<Article> {

    private String key;
    private String endpoint;
    public static final String API_URL = "http://newsapi.org";
    public static final int PAGE_SIZE = 5;

    // pre: given a valid authentication String and the desired endpoint,
    // post: constructs and returns a api.NewsAPIGetter that will be hooked up
    //      to said endpoint.
    public NewsAPIGetter(String key, String endpoint) {
        this.endpoint = endpoint;
        this.key = key;
    }

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
            String author = element.get("author").toString();
            String url = element.get("url").toString();
            String title = element.get("title").toString();
            String description = element.get("description").toString();
            JSONObject sourceObject = (JSONObject) element.get("source");
            String source = sourceObject.get("name").toString();
            result[i] = new Article(title, description, url, author, source);
        }
        return result;
    }

    public String paramString(Map<String, String> parameters) {
        String params = "?pageSize=" + PAGE_SIZE + "&";
        int i = parameters.keySet().size();
        for (String type : parameters.keySet()) {
            params += type + "=" + parameters.get(type);
            if (i != 1) { params += "&"; }
            i--;
        }
        return params;
    }

    public void changeKey(String newKey) { this.key = newKey; }

    public void setEndpoint(String newEnd) { this.endpoint = newEnd; }

    public static void main(String[] args) throws Exception {
        APIGetter news = new NewsAPIGetter("190415b2675d41f6b5397bd6e3484f13", "/v2/top-headlines");
        Map<String, String> parameters = new HashMap<>();
        parameters.put("q", "trump");
        news.setEndpoint("/v2/top-headlines");
        System.out.println(Arrays.toString(news.query(parameters)));
    }


}