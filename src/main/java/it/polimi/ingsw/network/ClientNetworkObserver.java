package it.polimi.ingsw.network;

import org.json.simple.JSONObject;

/**
 * This interface has the functionalities of the client seen from the network perspective.
 */
public interface ClientNetworkObserver {

    /**
     * Notifies the client network observer that the connection with the remote host is lost.
     */
    void notifyConnectionLoss();

    /**
     * Feeds the incoming message to the client in order to be processed.
     * @param message The message to process.
     */
    void processMessage(JSONObject message);
}
