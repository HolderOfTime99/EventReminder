package api;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Event extends APIResult {
    private String venue;
    private String address;
    private String date;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public Event(String name, String venue, String address, String description,
                 String startTime, String endTime, String url) {
        super(name, url, description);
        this.venue = venue;
        this.address = address;
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


        ret = "Event: " + getTitle() + "\n" +
                "Time: " + time + "\n" +
                "Venue: " + venue + "\n" +
                "Address " + address + "\n" +
                "Description: " + getDescription() + "\n" +
                "URL: " + getUrl() + "\n";
        return ret;
    }
}
