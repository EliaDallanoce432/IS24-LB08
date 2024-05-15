package it.polimi.ingsw.network;

import org.json.simple.JSONObject;

/**
 * this interface has the functionalities of the client seen from the network perspective
 */
public interface ClientNetworkObserverInterface {

    /**
     * notifies the client network observer that the connection with the remote host is lost
     */
    void notifyConnectionLoss();

    /**
     * feeds the incoming message to the client in order to be processed
     * @param message message to process
     */
    void processMessage(JSONObject message);
}
