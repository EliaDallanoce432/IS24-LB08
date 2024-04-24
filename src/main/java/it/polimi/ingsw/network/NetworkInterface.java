package it.polimi.ingsw.network;

import org.json.simple.JSONObject;

import java.net.InetAddress;
import java.net.Socket;

public interface NetworkInterface {
    /**
     * method sends a message to a remote host and returns the reply received from it
     * @param message message to send to the remote host
     * @return JSONObject reply from th remote host
     */
    JSONObject send(JSONObject message);

    /**
     * method sends a reply to the remote host
     * @param message JSONObject reply to send
     */
    void reply(JSONObject message);
}
