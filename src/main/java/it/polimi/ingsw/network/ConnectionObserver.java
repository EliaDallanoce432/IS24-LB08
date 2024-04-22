package it.polimi.ingsw.network;

import java.net.InetAddress;

public interface ConnectionObserver {

    /**
     * notifies the observer when there's a detected connection loss
     */
    void connectionLossNotification();

    /**
     * allows to exchange ports btween two hosts to connect the pingers to the relative pongers
     * @param localPongerPort port of the local ponger
     * @return int port of the remote ponger port used by the pinger to connect to it
     */
    int exchangePongerPorts(int localPongerPort);

    /**
     * method returns the ip address of the remote ponger
     * @return InetAddress
     */
    InetAddress getRemotePongerAddress();
}
