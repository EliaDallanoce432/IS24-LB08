package it.polimi.ingsw.network;

import org.json.simple.JSONObject;

public interface ClientNetworkObserverInterface {

    /**
     * notifies the client network observer that the connection with the remote host is lost
     */
    void notifyConnectionLoss();

    void processMessage(JSONObject message);
}
