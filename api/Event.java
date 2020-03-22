package api;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Event {
    private String name;
    private String venue;
    private String address;
    private String date;
    private String description;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String url;
    private final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public Event(String name, String venue, String address, String description,
                 String startTime, String endTime, String url) {
        this.name = name;
        this.venue = venue;
        this.address = address;
        this.description = description;
        this.url = url;
        if (!startTime.isEmpty()){
            this.startTime = LocalDateTime.parse(startTime, TIME_FORMAT);
            this.date = this.startTime.format(DATE_FORMAT);
        } else {
            this.startTime = null;
            this.date = null;
        }

        if (!endTime.isEmpty()){
            this.endTime = LocalDateTime.parse(endTime, TIME_FORMAT);
        } else {
            this.endTime = null;
        }
    }

    public String toString(){
        String time;
        String ret = "";

        if (date != null){
            time = startTime.format(TIME_FORMAT);
            if (endTime != null){
                 time += " - " + endTime.format(TIME_FORMAT);
            }
        } else {
            time = "no time given";
        }


        ret = "Event: " + name + "\n" +
                "------------------------------------\n" +
                "Time: " + time + "\n" +
                "Venue: " + venue + "\n" +
                "Address " + address + "\n" +
                "Description:" + description + "\n" +
                "URL: " + url + "\n";


        return ret;
    }

    public static void main(String[] args) throws Exception {
            Event test = new Event("name", "myhouse", "12345, apple street",
                    "test 123", "2020-06-24 08:30:00", "2020-06-24 08:30:01",
                    "www.google.com");
            System.out.println(test.toString());

    }



}
