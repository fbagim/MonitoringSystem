package com.yukon.test.minitoring.server;

import java.util.Calendar;

/**
 * To handel service outage information for the Servers
 */
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

    /**
     * check the status whether event is active or not     *
     * @return boolean active
     */
    public boolean isActive() {
        return active;
    }

    /**
     * return event description     *
     * @return String description
     */
    public String getDescription() {
        return description;
    }

    /**
     * return start time for service outage     *
     * @return Calender start time
     */
    public Calendar getStartTime() {
        return startTime;
    }


    /**
     * return end time for service outage     *
     * @return Calender end time
     */
    public Calendar getEndTime() {
        return endTime;
    }

}
