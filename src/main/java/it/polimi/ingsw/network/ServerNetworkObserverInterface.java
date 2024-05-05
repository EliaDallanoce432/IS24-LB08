package it.polimi.ingsw.network;

public interface ServerNetworkObserverInterface {

    /**
     * notifies the server network observer of an incoming message from the remote host
     * @param clientHandler reference to the network interface that is notifying
     */
    void notifyIncomingMessage(ClientHandler clientHandler);

    /**
     * notifies the server network observer that the connection with the remote host is lost
     * @param clientHandler reference to the network interface that is notifying
     */
    void notifyConnectionLoss(ClientHandler clientHandler);
}