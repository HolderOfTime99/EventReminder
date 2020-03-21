package src.News;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class NewsAPI {
    public static void main(String[] args) throws Exception {
        URL url = new URL("http://newsapi.org/v2/top-headlines?country=us&apiKey=190415b2675d41f6b5397bd6e3484f13");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String input;
        StringBuilder content = new StringBuilder();
        while ((input = in.readLine()) != null) {
            content.append(input);
        }
        in.close();
        System.out.println(content);
    }
}