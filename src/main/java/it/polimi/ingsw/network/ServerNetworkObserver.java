package it.polimi.ingsw.network;

import it.polimi.ingsw.util.supportclasses.Request;

/**
 * This interface has the functionalities of the server seen from the client handler perspective.
 */
public interface ServerNetworkObserver {

    /**
     * Notifies the server network observer that the connection with the remote host is lost.
     * @param clientHandler The reference to the network interface that is notifying.
     */
    void notifyConnectionLoss(ClientHandler clientHandler);

    /**
     * Adds the new ìncoming request to the request queue of the server.
     * @param request The request to be processed.
     */
    void submitNewRequest(Request request);
}
