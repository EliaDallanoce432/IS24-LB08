package it.polimi.ingsw.network.sockets;

public interface SocketInterface extends SocketInfoInterface {
    /**
     * sends a message out from the socket
     * @param message JSONObject
     */
    void sendMessage(String message);

    /**
     * receives and returns a message from the socket
     *
     * @return JSONObject message arrived to the socket
     */
    String receiveMessage();

    /**
     * terminates the socket execution
     */
    void shutdown();
}
