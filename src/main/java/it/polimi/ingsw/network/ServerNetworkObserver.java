package it.polimi.ingsw.network;

import it.polimi.ingsw.util.supportclasses.Request;

/**
 * this interface has the functionalities of the server seen from the client handler perspective
 */
public interface ServerNetworkObserver {

    /**
     * notifies the server network observer that the connection with the remote host is lost
     * @param clientHandler reference to the network interface that is notifying
     */
    void notifyConnectionLoss(ClientHandler clientHandler);

    /**
     * adds the new ìncoming request to the request queue of the server
     * @param request request to be processed
     */
    void submitNewRequest(Request request);
}
