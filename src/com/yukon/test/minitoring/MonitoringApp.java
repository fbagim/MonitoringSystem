package com.yukon.test.minitoring;

import com.yukon.test.minitoring.client.Caller;
import com.yukon.test.minitoring.client.ClientManager;
import com.yukon.test.minitoring.server.ServerManager;
import org.apache.log4j.Logger;

import java.io.IOException;

public class MonitoringApp {

    final static Logger logger = Logger.getLogger(MonitoringApp.class);

    public static final String SERVER_NAME_TEST1 = "SEV_LINUX";
    public static final String SERVER_NAME_TEST2 = "SEV_WINDOWS";
    public static final String SERVER_NAME_TEST3 = "SEV_LINUX";

    /**
     * Entery point to start minoring service.
     * @param args
     */
    public static void main(String[] args) {
        // Creating server manger for server management
        ServerManager serverManager = new ServerManager();

        // Creating client manager for client management
        ClientManager clientManager = new ClientManager(serverManager);

        try {
            // To instantiate  servers
            serverManager.initServers();
            //To start servers
            serverManager.startServers();

            // Creating Callers with caller id and name
            Caller caller1 = new Caller(100, "Caller_100");
            Caller caller2 = new Caller(101, "Caller_101");
            Caller caller3 = new Caller(102, "Caller_102");

            // Registering caller with server
            clientManager.registerCaller(caller1, SERVER_NAME_TEST1);
            clientManager.registerCaller(caller1, SERVER_NAME_TEST2);
            clientManager.registerCaller(caller2, SERVER_NAME_TEST2);
            clientManager.registerCaller(caller3, SERVER_NAME_TEST3);

            // Start Connector service to check server status
            clientManager.startConnectorService();

            // Stop one server for testing
            serverManager.stopServer(SERVER_NAME_TEST3);

        } catch (IOException e) {
            logger.error("Error on running Monitoring System >> " + e.getMessage());
        }
    }
}
