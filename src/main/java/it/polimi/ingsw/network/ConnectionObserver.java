package it.polimi.ingsw.network;

/**
 * this interface is implemented by a class interested to be notified in case the connection is lost
 */
public interface ConnectionObserver {
    /**
     * notifies the observer when there's a detected connection loss
     */
    void connectionLossNotification();
}
