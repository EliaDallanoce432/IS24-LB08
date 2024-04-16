package it.polimi.ingsw.network;

public interface NetworkObserver {
    /**
     * method used for the observer pattern
     */
    void notifyConnectionLoss();
}
