package it.polimi.ingsw.network;

import java.net.InetAddress;

public interface ConnectionObserver {
    /**
     * notifies the observer when there's a detected connection loss
     */
    void connectionLossNotification();
}
