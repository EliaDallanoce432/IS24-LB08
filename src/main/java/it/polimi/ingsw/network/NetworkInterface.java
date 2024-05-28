package it.polimi.ingsw.network;

import it.polimi.ingsw.network.input.NetworkInputObserver;
import it.polimi.ingsw.network.ping.ConnectionObserver;
import org.json.simple.JSONObject;

/**
 *  Represents a generic network interface to enable communication between two hosts.
 */
public interface NetworkInterface extends ConnectionObserver, NetworkInputObserver {
    /**
     * Method sends a message to a remote host.
     * @param message The message to send to the remote host.
     */
    void send(JSONObject message);
}
