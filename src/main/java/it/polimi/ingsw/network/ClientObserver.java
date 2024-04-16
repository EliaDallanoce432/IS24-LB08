package it.polimi.ingsw.network;

public interface ClientObserver {
    void notifyServerOfIncomingMessage(ClientHandler clientWithTheMessage);

}
