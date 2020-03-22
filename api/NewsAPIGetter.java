package api;

import java.util.*;
import java.io.*;
import java.net.*;

public class NewsAPIGetter implements APIGetter{

    private String key;
    private String endpoint;
    public static final String API_URL = "http://newsapi.org";

    // pre: given a valid authentication String and the desired endpoint,
    // post: constructs and returns a api.NewsAPIGetter that will be hooked up
    //      to said endpoint.
    public NewsAPIGetter(String key, String endpoint) {
        this.endpoint = endpoint;
        this.key = key;
    }

    public String query(Map<String, String> parameters) throws Exception {
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
        return readQuery(in);
    }

    private String readQuery(BufferedReader in) throws Exception {
        String input = in.readLine();
        StringBuilder content = new StringBuilder();
        while (input != null) {
            content.append(input);
            input = in.readLine();
        }
        in.close();
        return content.toString();
    }

    public String paramString(Map<String, String> parameters) {
        String params = "?";
        int i = parameters.keySet().size();
        for (String type : parameters.keySet()) {
            params += type + "=" + parameters.get(type);
            if (i != 1) { params += "&"; }
            i--;
        }
        return params;
    }

    public void setEndpoint(String newEnd) {
        this.endpoint = newEnd;
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