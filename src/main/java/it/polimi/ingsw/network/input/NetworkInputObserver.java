package it.polimi.ingsw.network.input;

import org.json.simple.JSONObject;

/**
 * This interface is used by a class that is interested in incoming messages from the network.
 */
public interface NetworkInputObserver {

    /**
     * Notifies the socket observer of an incoming message and passes the message to it.
     * @param message The received message.
     */
    void notifyIncomingMessageFromSocket(JSONObject message);
}
