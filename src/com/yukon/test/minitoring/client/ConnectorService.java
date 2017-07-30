package com.yukon.test.minitoring.client;

import com.yukon.test.minitoring.server.Server;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

public class ConnectorService extends Thread {

    final static Logger logger = Logger.getLogger(ConnectorService.class);

    private Hashtable<Server, List<Caller>> serverClinetMap;

    public ConnectorService(Hashtable<Server, List<Caller>> serverClinetMap) {
        this.serverClinetMap = serverClinetMap;
    }

    /**
     * Testing client - server connection one by one
     *
     * @throws UnknownHostException
     * @throws IOException
     */
    public void testServers() throws UnknownHostException, IOException {

        Set<Server> Servers = serverClinetMap.keySet();
        for (Server server : Servers) {
            connectToServer(serverClinetMap.get(server), server);
        }
    }

    /**
     * Testing client server connection by opening Socket for Client registered  servers
     *
     * @param callers all caller list
     * @param server server to test connection
     */
    public void connectToServer(List<Caller> callers, Server server) {

        for (Caller caller : callers) {
            try {
                // set grace time to  1000ms ,
                new Socket(server.getServerSocket().getInetAddress().getHostAddress(), server.getPort()).setSoTimeout(1000);
                caller.setStatusMessage("Send Message to Caller" + caller.getCallerName() + " : Server" + server.getHost() + ":port" + server.getPort() + "==> status==> Running");
            } catch (IOException e) {
                caller.setStatusMessage("Send Message to Caller" + caller.getCallerName() + " : Server" + server.getHost() + ":port" + server.getPort() + "==> status==> Stopped");
            }
        }
    }

    @Override
    /**
     *  Start and test the server with 1000 ms sleep time ,this will prevent poll any service more frequently than once a second.
     */
    public void run() {
        while (true) {
            try {
                testServers();
                sleep(1000);
            } catch (UnknownHostException e) {
                logger.debug("Error on ConnectorService " + e.getMessage());
            } catch (IOException e) {
                logger.debug("Error on ConnectorService " + e.getMessage());
            } catch (InterruptedException e) {
                logger.debug("Error on ConnectorService " + e.getMessage());
            }
        }
    }
}

