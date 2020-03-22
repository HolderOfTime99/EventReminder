package api;

import java.util.*;
import java.io.*;
import java.net.*;

public class EventfulAPIGetter /*implements APIGetter*/ {

    private String key;
    private String endpoint;
    public static final String API_URL = "http://api.eventful.com";

    // pre: given a valid authentication String and the desired endpoint,
    // post: constructs and returns a interactive client-side object that allows get requests
    //      to said endpoint.
    public EventfulAPIGetter(String key, String endpoint) throws Exception {
        this.key = key;
        this.endpoint = endpoint;
    }

    // pre: given a map of parameters (mapping type to value) for the query that
    //      follows the Eventful api formatting
    // post: returns the XML string of results for the given query
    public String query(Map<String, String> parameters) throws Exception {
        String paramString = paramString(parameters);
        URL url = new URL(API_URL + endpoint + paramString);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        InputStreamReader input;
        try {
            input = new InputStreamReader(con.getInputStream());
        } catch (Exception e) {
            con.disconnect();
            throw new IllegalArgumentException("The parameters passed in were not valid");
        }
        BufferedReader in = new BufferedReader(input);
        con.disconnect();
        return null; //readQuery(in);
    }

    public String paramString(Map<String, String> parameters) {
        String params = "?app_key=" + key + "&";
        int i = parameters.keySet().size();
        for (String type : parameters.keySet()) {
            params += type + "=" + parameters.get(type);
            if (i != 1) { params += "&"; }
            i--;
        }
        return params;
    }

    public void changeKey(String newKey) { key = newKey; }

    public void setEndpoint(String newEndpoint) {
        endpoint = newEndpoint;
    }

    public static void main(String[] args) throws Exception {
        EventfulAPIGetter events = new EventfulAPIGetter("zDsLqMh4NJdQtWsw", "/rest/events/search");
        Map<String, String> parameters = new HashMap<>();
        parameters.put("keywords", "books");
        parameters.put("location", "San+Diego");
        System.out.println(events.query(parameters));
    }

}
