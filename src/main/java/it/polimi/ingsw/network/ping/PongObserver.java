package it.polimi.ingsw.network.ping;

/**
 * this interface allows to notify an observer of a pong response
 */
public interface PongObserver {
    /**
     * notifies the PongObserver that a pong has been received
     */
    void notifyPong();
}
