package api;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.util.*;
import java.io.*;



public class EventReminderMain {

    public static final String GMAIL_USERNAME = "samberensohn@gmail.com";
    public static final String GMAIL_PASSWORD = "xxxxxx";
    public static final String NEWS_API_KEY = "190415b2675d41f6b5397bd6e3484f13";
    public static final String NEWS_API_ENDPOINT = "/v2/top-headlines";
    public static final String EVENTFUL_API_KEY = "zDsLqMh4NJdQtWsw";
    public static final String EVENTFUL_API_ENDPOINT = "/rest/events/search";

    public static void main(String[] args) throws Exception {
        List<Map<String, String>> newsParams = getParams(new FileReader("json\\News.json"));
        List<Map<String, String>> eventParams = getParams(new FileReader("json\\Events.json"));


        APIGetter<Article> news = new NewsAPIGetter(NEWS_API_KEY, NEWS_API_ENDPOINT);
        APIGetter<Event> events = new EventfulAPIGetter(EVENTFUL_API_KEY, EVENTFUL_API_ENDPOINT);
        GmailSender mailer = new GmailSender(GMAIL_USERNAME, GMAIL_PASSWORD);

    }

    public static List<Map<String, String>> getParams(FileReader jsonFile) throws Exception {
        JSONParser parser = new JSONParser();
        JSONObject queryCollection = (JSONObject) parser.parse(jsonFile);
        JSONArray queries = (JSONArray) queryCollection.get("queries");
        List<Map<String, String>> result = new LinkedList<>();
        for (int i = 0; i < queries.size(); i++) {
            JSONObject query = (JSONObject) queries.get(i);
            JSONArray params = (JSONArray) query.get("Parameters");
            Map<String, String> element = new HashMap<>();
            for (int j = 0; j < params.size(); j++) {
                JSONObject parameter = (JSONObject) params.get(j);
                String key = "" + parameter.get("name");
                String value = "" +  parameter.get("value");
                element.put(key, value);
            }
            result.add(element);
        }
        return result;
    }
}
