package api;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * This is an object that represents an event returned by API call
 */
public class Event extends APIResult {
    private String venue;
    private String address;
    private String date;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * Constructor that creates an Event.
     *
     * @param name Name of the event.
     * @param venue Name of venue that the event is hosted at.
     * @param address The address of venue
     * @param description A description regarding the event.
     * @param startTime The time (given in yyyy-MM-dd HH:mm:ss format) that the event starts.
     * @param endTime The time (given in yyyy-MM-dd HH:mm:ss format) that the event ends.
     * @param url A link to additional information regarding the event.
     */
    public Event(String name, String venue, String address, String description,
                 String startTime, String endTime, String url) {
        super(name, url, description);
        this.venue = venue;
        this.address = address;

        // Start Time
        if (!startTime.isEmpty()){
            this.startTime = LocalDateTime.parse(startTime, TIME_FORMAT);
            this.date = this.startTime.format(DATE_FORMAT);
        } else {
            this.startTime = null;
            this.date = null;
        }

        // End Time
        if (!endTime.isEmpty()){
            this.endTime = LocalDateTime.parse(endTime, TIME_FORMAT);
        } else {
            this.endTime = null;
        }
    }

    /**
     * Returns a String representation of the Event
     *
     * @return String representation of the Event
     */
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
