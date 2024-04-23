package it.polimi.ingsw.network;

import java.net.InetAddress;

public interface ConnectionObserver {
    /**
     * notifies the observer when there's a detected connection loss
     */
    void connectionLossNotification();

    /**
     * method used in the setup of the pinger to know the other host's ponger port
     * @param pongerPort local ponger port
     * @return remote ponger port
     */
    int exchangePongerPorts(int pongerPort);

    /**
     * method gets the remote host address
     * @return remote host address
     */
    InetAddress getRemotePongerAddress();
}
