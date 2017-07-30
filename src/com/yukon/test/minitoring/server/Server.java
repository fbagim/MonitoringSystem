package com.yukon.test.minitoring.server;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.*;

public class Server extends Thread {

    final static Logger logger = Logger.getLogger(Server.class);

    private ServerSocket serverSocket;
    private int port;
    private String host;
    private String serverName;
    private String serverId;
    private boolean b = true;

    private List<ServiceOutage> serviceOutages;

    public Server(int port, String host, String serverName, String serverId) throws IOException {
        this.port = port;
        this.host = host;
        this.serverName = serverName;
        this.serverId = serverId;
        this.serverSocket = new ServerSocket(port);
        serviceOutages = new ArrayList();
    }

    public ServerSocket getServerSocket() {
        return serverSocket;
    }

    public void stopServer() throws IOException {
        b = false;
        serverSocket.close();
    }

    public String getServerId() {
        return serverId;
    }


    public int getPort() {
        return port;
    }


    public String getHost() {
        return host;
    }


    public List<ServiceOutage> getServiceOutages() {
        return serviceOutages;
    }

    /**
     *
     * @param from time to start serviceOutage
     * @param to time to end serviceOutage
     * @return validate or not
     */
    private boolean checkTimeForServiceOutage(Calendar from, Calendar to) {
        if (checkDateWithCurrent(from, true) && checkDateWithCurrent(to, false)) {
            return true;
        }
        return false;
    }

    /**
     *
     * @param calendar vaid
     * @param isFrom
     * @return validate or not
     */
    private boolean checkDateWithCurrent(Calendar calendar, boolean isFrom) {
        Calendar now = new GregorianCalendar();
        if (isFrom) {
            if (calendar.get(Calendar.YEAR) <= now.get(Calendar.YEAR)
                    && calendar.get(Calendar.MONTH) <= now.get(Calendar.MONTH)
                    && calendar.get(Calendar.DAY_OF_MONTH) <= now.get(Calendar.DAY_OF_MONTH) && calendar.get(Calendar.HOUR_OF_DAY) <= now.get(Calendar.HOUR_OF_DAY) && calendar.get(Calendar.MINUTE) <= now.get(Calendar.MINUTE)) {
                return true;
            }

        } else {
            if (calendar.get(Calendar.YEAR) >= now.get(Calendar.YEAR)
                    && calendar.get(Calendar.MONTH) >= now.get(Calendar.MONTH)
                    && calendar.get(Calendar.DAY_OF_MONTH) >= now.get(Calendar.DAY_OF_MONTH) && calendar.get(Calendar.HOUR_OF_DAY) >= now.get(Calendar.HOUR_OF_DAY) && calendar.get(Calendar.MINUTE) >= now.get(Calendar.MINUTE)) {
                return true;
            }
        }

        return false;
    }

    /**
     * @return server outage message if server running on outage time
     */
    private String checkServerOnOutageStatus() {
        Iterator<ServiceOutage> iterator = serviceOutages.iterator();

        while (iterator.hasNext()) {
            ServiceOutage serviceOutage = iterator.next();
            if (serviceOutage.isActive()) {
                if (checkTimeForServiceOutage(serviceOutage.getStartTime(), serviceOutage.getEndTime())) {
                    return serviceOutage.getDescription();
                }
            }
        }
        return null;
    }

    /**
     * Start server to continus running  with 1000 ms sleep time.
     *
     */
    @Override
    public void run() {
        while (b) {
            String message = checkServerOnOutageStatus();
            if (message != null) {
                logger.info("Server " + serverName + " --->Status ---> " + message);
            } else {
                logger.info("Server Running ==> " + serverName + "--- port --> " + serverSocket.getLocalPort());
            }
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                System.out.println(e);
            }
        }
    }
}