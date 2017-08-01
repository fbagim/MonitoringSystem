package com.yukon.test.minitoring.client;

import java.util.*;

import com.yukon.test.minitoring.server.Server;
import com.yukon.test.minitoring.server.ServerManager;

/***
 * Responsible to handel all clients and functionalist's
 */
public class ClientManager {

    private ServerManager serverManager;
    private Hashtable<Server, List<Caller>> serverClinetMap;
    private ConnectorService connectorService;

    public ClientManager(ServerManager serverManager) {
        this.serverManager = serverManager;
        serverClinetMap = new Hashtable();
    }

    /**
     * To register caller with server
     *
     * @param caller   caller object
     * @param serverId server id to map caller
     */
    public void registerCaller(Caller caller, String serverId) {
        if (caller != null && serverId != null) {
            Server server = serverManager.getServers().get(serverId);
            if (server != null) {
                List<Caller> list = serverClinetMap.get(server);
                if (list == null) {
                    list = new ArrayList();
                }
                list.add(caller);
                serverClinetMap.put(server, list);
            }
        }
    }

    /**
     * Start connector service with server client mapping
     */
    public void startConnectorService() {
        connectorService = new ConnectorService(serverClinetMap);
        connectorService.start();
    }
}
