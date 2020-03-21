package src.News;

import java.util.*;
import java.io.*;
import java.net.*;

public class NewsAPIGetter {

    private String key;
    private String endpoint;
    public static final String API_URL = "http://newsapi.org";

    // pre: given a valid authentication String and the desired endpoint,
    // post: constructs and returns a NewsAPIGetter that will be hooked up
    //      to said endpoint.
    public NewsAPIGetter(String key, String endpoint) throws Exception {
        this.key = key;
        this.endpoint = endpoint;
    }

    // pre: given a map of parameters (mapping type to value) for the query that
    //      follows the News api formatting
    // post: returns the JSON string of results for the given query
    public String query(Map<String, String> parameters) throws Exception {
        String paramString = paramString(parameters);
        URL url = new URL(API_URL + endpoint + paramString);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("X-Api-Key", key);
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String input = in.readLine();
            StringBuilder content = new StringBuilder();
            while (input != null) {
                content.append(input);
                input = in.readLine();
            }
            in.close();
            return content.toString();
        } catch (Exception e) {
            throw new IllegalArgumentException("The parameters passed in were not valid");
        }
    }

    private String paramString(Map<String, String> parameters) {
        String params = "?";
        int i = parameters.keySet().size();
        for (String type : parameters.keySet()) {
            params += type + "=" + parameters.get(type);
            if (i != 1) { params += "&"; }
            i--;
        }
        return params;
    }

    public void setEndpoint(String newEndpoint) {
        endpoint = newEndpoint;
    }

    public static void main(String[] args) throws Exception {
        NewsAPIGetter news = new NewsAPIGetter("190415b2675d41f6b5397bd6e3484f13", "/v2/top-headlines");
        Map<String, String> parameters = new HashMap<>();
        parameters.put("sources", "bbc-news");
        parameters.put("q", "trump");
        System.out.println(news.query(parameters));
        parameters.remove("sources");
        news.setEndpoint("/v2/everything");
        System.out.println(news.query(parameters));

    }


}