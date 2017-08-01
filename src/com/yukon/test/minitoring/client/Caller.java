package com.yukon.test.minitoring.client;

import org.apache.log4j.Logger;

/**
 *  Managed and store the caller data.
 */
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

    /**
     * used to send server status messages to caller and calling from server
     * @param statusMessage message from sever to Caller
     */
    public void setStatusMessage(String statusMessage) {
        logger.info(statusMessage);
    }
}
