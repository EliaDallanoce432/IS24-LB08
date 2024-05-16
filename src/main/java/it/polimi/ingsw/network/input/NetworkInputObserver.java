package it.polimi.ingsw.network.input;

import org.json.simple.JSONObject;

/**
 * this interface is used by a class that is interested in incoming messages from the network
 */
public interface NetworkInputObserver {

    /**
     * notifies the socket observer of an incoming message and passes the message to it
     * @param message received message
     */
    void notifyIncomingMessageFromSocket(JSONObject message);
}
