package it.polimi.ingsw.network;

public interface ClientObserver {
    /**
     * method is used to notify the client observer that a new message arrived from the client
     * @param clientWithTheMessage reference to the client that received the message
     */
    void notifyServerOfIncomingMessage(ClientHandler clientWithTheMessage);

}
