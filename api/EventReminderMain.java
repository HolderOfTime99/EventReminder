package api;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.util.*;
import java.io.*;


/**
 * This class is the driver for the automated email sending.
 */
public class EventReminderMain {

    public static final String NEWS_API_ENDPOINT = "/v2/top-headlines";
    public static final String EVENTFUL_API_ENDPOINT = "/rest/events/search";

    public static String username;
    public static String gmailPassword;
    public static String mailTo;
    public static String newsApiKey;
    public static String eventfulApiKey;

    public static void main(String[] args) throws Exception {
        getEmailInfo();
        getKeys();

        List<Map<String, String>> newsParams = getParams(new FileReader("json" + File.separator + "News.json"));
        List<Map<String, String>> eventParams = getParams(new FileReader("json" + File.separator + "Events.json"));


        APIGetter<APIResult> news = new NewsAPIGetter(newsApiKey, NEWS_API_ENDPOINT);
        APIGetter<APIResult> events = new EventfulAPIGetter(eventfulApiKey, EVENTFUL_API_ENDPOINT);

        List<APIResult[]> newsQueries = queries(news, newsParams);
        List<APIResult[]> eventQueries = queries(events, eventParams);

        GmailSender mailer = new GmailSender(username, gmailPassword);

        String content = getContent(newsQueries, eventQueries, newsParams, eventParams);

        mailer.send(mailTo, "Daily Email Update", content);

    }

    /**
     * This method formats the content to be sent in the email message
     *
     * @param news the list of news API results for each of the desired queries
     * @param events the list of event API results for each of the desired queries
     * @param newsKeywords the list of news API parameters for each of the desired queries
     * @param eventKeywords the list of event API parameters for each of the desired queries
     * @return the formatted content for the email to be sent.
     * @throws IOException throws various IOExceptions for file handling
     */
    public static String getContent(List<APIResult[]> news, List<APIResult[]> events,
                                    List<Map<String, String>> newsKeywords,
                                    List<Map<String, String>> eventKeywords) throws IOException {
        BufferedReader in = new BufferedReader(new FileReader("email_format.txt"));
        String content = in.readLine() + "\n";
        String line = "";
        while (!line.equals("Articles:\n")) {
            line = in.readLine() + "\n";
            content += line;
        }
        content += getAPIResults(news, newsKeywords);
        content += in.readLine() + "\n";
        content += getAPIResults(events, eventKeywords);
        while (in.ready()) {
            content += in.readLine();
        }
        return content;
    }

    /**
     * Formats the given API results for the content of the email.
     *
     * @param queryResults the list of query results for each of the desired queries
     * @param parameters the list of parameters for each of the desired queries
     * @return the formatted content string for results
     */
    public static String getAPIResults(List<APIResult[]> queryResults, List<Map<String, String>> parameters) {
        String result = "";
        int listIndex = 0;
        for (Map<String, String> queryParams : parameters) {
            String searchParams = formatParameters(queryParams);
            result += "\n" + searchParams + "\n";
            for (int i = 0; i < queryResults.get(listIndex).length; i++) {
                result += queryResults.get(listIndex)[i];
                result += "-----------------------------------------------------\n";
            }
            listIndex++;
        }
        return result;
    }

    /**
     * This method actually queries the APIs
     *
     * @param getter the specific API getter to be queried
     * @param parametersList list of the parameters for each query
     * @return a list of APIResult[] wherein each element is the result of a query
     * @throws Exception
     */
    public static List<APIResult[]> queries(APIGetter<APIResult> getter,
                                            List<Map<String, String>> parametersList) throws Exception {
        List<APIResult[]> result = new LinkedList<>();
        for (Map<String, String> parameters : parametersList) {
            APIResult[] query = getter.query(parameters);
            result.add(query);
        }
        return result;
    }

    /**
     * This method parses the JSON file that has the parameters for the queries
     *
     * @param jsonFile the file being parsed
     * @return a list of parameter maps for each query
     * @throws Exception throws exceptions for parsing and file handling
     */
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

    /**
     * Formats the given parameters for the message content
     *
     * @param params the map of parameters
     * @return formatted String for message content
     */
    public static String formatParameters(Map<String, String> params) {
        String result = "Search Parameters-";
        int i = params.keySet().size();
        for (String key : params.keySet()) {
            String value = params.get(key).replace('+', ' ').replace('-', ' ');
            result += " " + key + " = " + value;
            i--;
            if (i != 0) {
                result += ",";
            }
        }
        return result + "\n";
    }

    /**
     * Gets the email information from the specified file in README.md
     *
     * @throws IOException for file handling
     */
    public static void getEmailInfo() throws Exception{
        File file = new File("email.txt");
        BufferedReader br = new BufferedReader(new FileReader(file));

        username = br.readLine();
        gmailPassword = br.readLine();
        mailTo = br.readLine();
    }

    /**
     * Gets the API key information from the specified file in README.md
     *
     * @throws IOException for file handling
     */
    public static void getKeys() throws Exception {

        File newsFile = new File("api" + File.separator + "newsKey.txt");
        File eventFile = new File("api" + File.separator + "eventKey.txt");

        BufferedReader newsBr = new BufferedReader(new FileReader(newsFile));
        BufferedReader eventBr = new BufferedReader(new FileReader(eventFile));

        newsApiKey = newsBr.readLine();
        eventfulApiKey = eventBr.readLine();
    }


}
