package it.polimi.ingsw.network;

import it.polimi.ingsw.util.supportclasses.Request;

public interface ServerNetworkObserverInterface {

    /**
     * notifies the server network observer that the connection with the remote host is lost
     * @param clientHandler reference to the network interface that is notifying
     */
    void notifyConnectionLoss(ClientHandler clientHandler);

    void submitNewRequest(Request request);
}
