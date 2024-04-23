package it.polimi.ingsw.network.ping;

import it.polimi.ingsw.network.ConnectionObserver;

import java.net.InetAddress;

public class ConnectionChecker {
    ConnectionObserver connectionObserver;
    private final Pinger pinger;
    private final Ponger ponger;

    private Thread pingerThread;
    private Thread pongerThread;

    public ConnectionChecker(ConnectionObserver observer) {
        this.connectionObserver = observer;
        this.ponger = new Ponger();
        int remotePongerPort =  connectionObserver.exchangePongerPorts(ponger.getPort());
        InetAddress remotePongerAddress = connectionObserver.getRemotePongerAddress();
        this.pinger = new Pinger(observer,remotePongerAddress,remotePongerPort);
        this.pongerThread = new Thread(ponger);
        this.pingerThread = new Thread(pinger);
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
