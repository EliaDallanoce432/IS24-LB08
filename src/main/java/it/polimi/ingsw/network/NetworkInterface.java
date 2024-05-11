package it.polimi.ingsw.network;

import org.json.simple.JSONObject;

public interface NetworkInterface extends ConnectionObserver {
    /**
     * method sends a message to a remote host
     * @param message   message to send to the remote host
     */
    void send(JSONObject message);

}
