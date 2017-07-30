package com.yukon.test.minitoring.client;

import org.apache.log4j.Logger;

public class Caller {
    final static Logger logger = Logger.getLogger(Caller.class);

    private int callerId;
    private String callerName;

    public Caller(int callerId, String callerName) {
        this.callerId = callerId;
        this.callerName = callerName;
    }

    public int getCallerId() {
        return callerId;
    }

    public String getCallerName() {
        return callerName;
    }

    public void setCallerName(String callerName) {
        this.callerName = callerName;
    }

    public void setCallerId(int callerId) {
        this.callerId = callerId;
    }

    public void setStatusMessage(String statusMessage) {
        logger.info(statusMessage);
    }
}
