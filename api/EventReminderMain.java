package api;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.util.*;
import java.io.*;



public class EventReminderMain {

    public static String username = "xxxxxxxxxxxxxxx";
    public static String gmailPassword = "xxxxxxxxx";
    public static String mailTo = "xxxxxxxx";
    public static String newsApiKey = "190415b2675d41f6b5397bd6e3484f13";
    public static final String NEWS_API_ENDPOINT = "/v2/top-headlines";
    public static String eventfulApiKey = "zDsLqMh4NJdQtWsw";
    public static final String EVENTFUL_API_ENDPOINT = "/rest/events/search";

    public static void main(String[] args) throws Exception {
        getEmailInfo();

        List<Map<String, String>> newsParams = getParams(new FileReader("json" + File.separator + "News.json"));
        List<Map<String, String>> eventParams = getParams(new FileReader("json" + File.separator + "Events.json"));


        APIGetter<APIResult> news = new NewsAPIGetter(newsApiKey, NEWS_API_ENDPOINT);
        APIGetter<APIResult> events = new EventfulAPIGetter(eventfulApiKey, EVENTFUL_API_ENDPOINT);

        List<APIResult[]> newsQueries = queries(news, newsParams);
        List<APIResult[]> eventQueries = queries(events, eventParams);

        GmailSender mailer = new GmailSender(username, gmailPassword);

        String content = getContent(newsQueries, eventQueries);

        mailer.send(mailTo, "Daily Email Update", content);

    }

    public static String getContent(List<APIResult[]> news, List<APIResult[]> events) {
        String content = "Events: \n";
        for (APIResult[] arr : events) {
            content += Arrays.toString(arr);
        }
        content += "\n :::::::::::::::::::::::::::::: \n" +
                    "Articles:\n";
        for (APIResult[] arr : news) {
            content += Arrays.toString(arr);
        }
        return content;

    }

    public static List<APIResult[]> queries(APIGetter<APIResult> getter,
                                            List<Map<String, String>> parametersList) throws Exception {
        List<APIResult[]> result = new LinkedList<>();
        for (Map<String, String> parameters : parametersList) {
            APIResult[] query = getter.query(parameters);
            result.add(query);
        }
        return result;
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

    public static void getEmailInfo() throws Exception{
        File file = new File("email.txt");
        BufferedReader br = new BufferedReader(new FileReader(file));

        username = br.readLine();
        gmailPassword = br.readLine();
        mailTo = br.readLine();
        System.out.println(username + gmailPassword);

    }

    public static void getKeys() throws Exception{

        File newsFile = new File("newsKey.txt");
        File eventFile = new File("eventKey.txt");

        BufferedReader newsBr = new BufferedReader(new FileReader(newsFile));
        BufferedReader eventBr = new BufferedReader(new FileReader(eventFile));

        newsApiKey = newsBr.readLine();
        eventfulApiKey = eventBr.readLine();
    }


}
