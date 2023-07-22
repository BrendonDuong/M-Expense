package com.jovanovic.stefan.sqlitetutorial;


import java.io.Serializable;

public class Trip implements Serializable {
    private int trip_id;
    private String trip_name;
    private String date_start;
    private String date_end;
    private String place_from;
    private String place_to;
    private String risk;

    public Trip(int trip_id, String trip_name, String date_start, String date_end, String place_from, String place_to,String risk) {
        this.trip_id = trip_id;
        this.trip_name = trip_name;
        this.date_start = date_start;
        this.date_end = date_end;
        this.place_from = place_from;
        this.place_to = place_to;
        this.risk =risk;
    }

    public Trip() {

    }

    public String getTrip_name() {
        return trip_name;
    }

    public void setTrip_name(String trip_name) {
        this.trip_name = trip_name;
    }

    public String getDate_start() {
        return date_start;
    }

    public void setDate_start(String date_start) {
        this.date_start = date_start;
    }

    public String getDate_end() {
        return date_end;
    }

    public void setDate_end(String date_end) {
        this.date_end = date_end;
    }

    public String getPlace_from() {
        return place_from;
    }

    public void setPlace_from(String place_from) {
        this.place_from = place_from;
    }

    public String getPlace_to() {
        return place_to;
    }

    public void setPlace_to(String place_to) {
        this.place_to = place_to;
    }

    public String getRisk() {
        return risk;
    }

    public void setRisk(String risk) {
        this.risk = risk;
    }

    public int getId() {
        return trip_id;
    }

    public void setId(int trip_id) {
        this.trip_id = trip_id;
    }

    @Override
    public String toString() {
        return "Trip{" +
                "trip_id=" + trip_id +
                ", trip_name='" + trip_name + '\'' +
                ", date_start='" + date_start + '\'' +
                ", date_end='" + date_end + '\'' +
                ", place_from='" + place_from + '\'' +
                ", place_to='" + place_to + '\'' +
                ", risk=" + risk +
                '}';
    }
}
