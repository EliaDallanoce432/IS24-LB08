package it.polimi.ingsw.network;

import org.json.simple.JSONObject;

public interface NetworkInterface {
    /**
     * method sends a message to a remote host and returns the reply received from it
     *
     * @param message   message to send to the remote host
     * @param waitReply
     * @return JSONObject reply from th remote host
     */
    JSONObject send(JSONObject message, boolean waitReply);

    /**
     * method sends a reply to the remote host
     * @param message JSONObject reply to send
     */
    void reply(JSONObject message);
}
