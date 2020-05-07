package api;

import java.io.BufferedReader;
import java.util.Map;

/**
 * This class represents an interface for an API. Supports only
 * GET queries.
 *
 * @param <E> the type of results to be returned to the client
 */
public interface APIGetter<E> {

    /**
     * Queries the given endpoint of the api based on the given parameters.
     *
     * @param parameters A Map of parameter keywords to their values.
     * @return An array of E that are the results from querying the api with
     *         the given parameters.
     * @throws Exception throws an IllegalArgumentException when the parameters are
     *         not formatted to the API requirements.
     */
    E[] query(Map<String, String> parameters) throws Exception;

    /**
     * Formats the parameters according to the API requirements to be appended
     * to the API query URL.
     *
     * @param parameters A Map from API parameter keywords to their values.
     * @return formatted String of parameters to be appended to the API URL
     */
    String paramString(Map<String, String> parameters);

    /**
     * Changes the endpoint to be queried from the desired API.
     *
     * @param newEnd a String endpoint from the API.
     */
    void setEndpoint(String newEnd);

    /**
     * Changes the authentication key for the  API.
     *
     * @param newKey a String valid key from the desired API.
     */
    void changeKey(String newKey);

    /**
     * Reads in the content from the given reader. Used for JSON parsing
     * @param in the BufferedReader connected to the JSON file
     * @return the JSON string to be parsed
     * @throws Exception for file handling
     */
    default String readQuery(BufferedReader in) throws Exception {
        String input = in.readLine();
        StringBuilder content = new StringBuilder();
        while (input != null) {
            content.append(input);
            input = in.readLine();
        }
        in.close();
        return content.toString();
    }
}
