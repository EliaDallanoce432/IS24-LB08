package it.polimi.ingsw.network;

import org.json.simple.JSONObject;

import java.net.Socket;

public interface NetworkInterface{
    /**
     * method returns the socket used by the Network interface
     * @return Socket
     */
    Socket getSocket();

    /**
     * method takes a message to send to the other application
     * @param message message to send out on the socket
     */
    void sendMessage(JSONObject message);
}
