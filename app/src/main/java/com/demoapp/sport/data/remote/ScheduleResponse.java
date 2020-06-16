package com.demoapp.sport.data.remote;

import com.squareup.moshi.Json;

import java.util.List;

public class ScheduleResponse {

    @Json(name = "events")
    List<Schedules> schedules;

    public List<Schedules> getSchedules() {
        return schedules;
    }

    public boolean isNullOrEmpty() {
        return schedules == null || schedules.isEmpty();
    }

}
