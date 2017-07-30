package com.yukon.test.minitoring.server;

import java.util.Calendar;

public class ServiceOutage {
    private String description;
    private Calendar startTime;
    private Calendar endTime;
    private boolean active;

    public ServiceOutage(Calendar startTime, Calendar endTime, boolean active, String description) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.active = active;
        this.description = description;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Calendar getStartTime() {
        return startTime;
    }

    public void setStartTime(Calendar startTime) {
        this.startTime = startTime;
    }

    public Calendar getEndTime() {
        return endTime;
    }

    public void setEndTime(Calendar endTime) {
        this.endTime = endTime;
    }
}
