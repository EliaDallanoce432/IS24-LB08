package it.polimi.ingsw.network;

import java.net.InetAddress;

public class ConnectionChecker {
    private final Pinger pinger;
    private final Ponger ponger;

    public ConnectionChecker(ConnectionObserver observer) {
        this.ponger = new Ponger();
        int remotePongerPort =  observer.exchangePongerPorts(ponger.getPort());
        InetAddress remotePongerAddress = observer.getRemotePongerAddress();
        this.pinger = new Pinger(observer,remotePongerAddress,remotePongerPort);
        Thread pongerThread = new Thread(ponger);
        Thread pingerThread = new Thread(pinger);
        pongerThread.start();
        pingerThread.start();
    }

    /**
     * method shuts down the pinger and the ponger used for checking the connection
     */
    public void shutdown() {
        pinger.shutdown();
        ponger.shutdown();
    }
}
