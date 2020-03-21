package events;

import java.util.*;
import java.io.*;
import java.net.*;

public class EventfulAPIGetter {

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
        String params = "?app_key=" + key + "&";
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
        EventfulAPIGetter events = new EventfulAPIGetter("zDsLqMh4NJdQtWsw", "/rest/events/search");
        Map<String, String> parameters = new HashMap<>();
        parameters.put("keywords", "books");
        parameters.put("location", "San+Diego");
        System.out.println(events.query(parameters));
    }

}
