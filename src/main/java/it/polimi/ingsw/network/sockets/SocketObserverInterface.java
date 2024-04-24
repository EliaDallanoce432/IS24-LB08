package it.polimi.ingsw.network.sockets;

import org.json.simple.JSONObject;

public interface SocketObserverInterface {

    /**
     * notifies the socket observer of an incoming message and passes the message to it
     * @param message received message
     */
    void notifyIncomingMessageFromSocket(JSONObject message);
}
