package com.demoapp.sport.data.remote;


import com.squareup.moshi.Json;

public class Schedules {

    @Json(name = "strEvent")
    private String match;

    @Json(name = "strLeague")
    private String league;

    @Json(name = "strSeason")
    private String year;

    @Json(name = "dateEvent")
    private String date;

    @Json(name = "strTime")
    private String time;

    public  String getMatch(){
        return  match;
    }

    public String getDate() {
        return date;
    }

    public String getLeague() {
        return league;
    }

    public String getTime() {
        return time != null ? time.split(":")[0] + ":" + time.split(":")[1] : "";
    }
    public String getEventDate() {
        return date + ", " + getTime();
    }

    public String getYear() {
        return year;
    }
}
