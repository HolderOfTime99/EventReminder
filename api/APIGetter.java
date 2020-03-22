package api;

import java.util.Map;

public interface APIGetter {

    String query(Map<String, String> parameters) throws Exception;


    String paramString(Map<String, String> parameters);

    void setEndpoint(String newEnd);

}
