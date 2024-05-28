package it.polimi.ingsw.network.ping;

/**
 * This interface allows to notify an observer of a pong response.
 */
public interface PongObserver {
    /**
     * Notifies the PongObserver that a pong has been received.
     */
    void notifyPong();
}
