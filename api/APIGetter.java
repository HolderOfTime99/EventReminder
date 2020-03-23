package api;

import java.io.BufferedReader;
import java.util.Map;

public interface APIGetter<T> {

    //comment test
    T[] query(Map<String, String> parameters) throws Exception;

    String paramString(Map<String, String> parameters);

    void setEndpoint(String newEnd);

    void changeKey(String newKey);

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
