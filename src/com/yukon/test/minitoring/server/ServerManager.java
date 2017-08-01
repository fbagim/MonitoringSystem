package com.yukon.test.minitoring.server;

import com.yukon.test.minitoring.MonitoringApp;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.*;

/**
 * To manage all running servers and connections
 */
public class ServerManager {
    final static Logger logger = Logger.getLogger(ServerManager.class);

    // Map to store servers with server id and server
    private Map<String, Server> servers;

    public ServerManager() {
        servers = new HashMap<String, Server>();
    }

    /**
     * Instantiate all servers
     * @throws IOException
     */
    public void initServers() throws IOException {
        Calendar from = new GregorianCalendar(2017, Calendar.JULY, 30, 21, 32, 00);
        Calendar to = new GregorianCalendar(2017, Calendar.JULY, 30, 21, 35, 00);

        ServiceOutage serviceOutage = new ServiceOutage(from, to, true, "Server on maintenance in progress...");

        Server serverLinux = new Server(4555, "0.0.0.0", "Linux Box", MonitoringApp.SERVER_NAME_TEST1);
        Server serverWindows = new Server(4556, "0.0.0.0", "Windows Box", MonitoringApp.SERVER_NAME_TEST2);
        Server serverApache = new Server(4557, "0.0.0.0", "Apache Web", MonitoringApp.SERVER_NAME_TEST3);

        serverWindows.getServiceOutages().add(serviceOutage);
        serverApache.getServiceOutages().add(serviceOutage);

        servers.put(serverLinux.getServerId(), serverLinux);
        servers.put(serverWindows.getServerId(), serverWindows);
        servers.put(serverApache.getServerId(), serverApache);

        logger.info("Instantiated all servers");
    }

    /**
     *
     * @return all servers as map
     */
    public Map<String, Server> getServers() {
        return servers;
    }

    /**
     * stop server by server id
     * @param serverId
     * @throws IOException
     */
    public void stopServer(String serverId) throws IOException {
        Server server = servers.get(serverId);
        if (server != null) {
            server.stopServer();
        }
    }

    /**
     * start all servers
     */
    public void startServers() {
        for (Map.Entry<String, Server> entry : servers.entrySet()) {
            Server server = entry.getValue();
            logger.info("Server Started ==> " + server.getServerId() + "==> Port" + server.getPort());
            new Thread(server).start();
        }
    }
}
