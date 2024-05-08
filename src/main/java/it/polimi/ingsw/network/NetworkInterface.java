package it.polimi.ingsw.network;

import org.json.simple.JSONObject;

public interface NetworkInterface extends ConnectionObserver {
    /**
     * method sends a message to a remote host and returns the reply received from it
     *
     * @param message   message to send to the remote host
     * @param waitReply  if true makes the method blocking until the reply is received
     * @return JSONObject reply from th remote host
     */
    JSONObject send(JSONObject message, boolean waitReply);
    void send(JSONObject message);

}
