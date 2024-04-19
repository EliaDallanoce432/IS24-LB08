package it.polimi.ingsw.network;

import java.io.IOException;
import java.net.InetAddress;

public class Pinger implements Runnable{
    private final NetworkInterface application;
    private final InetAddress applicationInetAddress;

    public Pinger (NetworkInterface networkInterface) {
        this.application = networkInterface;
        this.applicationInetAddress = networkInterface.getSocket().getInetAddress();
    }

    @Override
    public void run() {
        boolean connected = true;
        while (connected) {
            try {
                if(!applicationInetAddress.isReachable(1000)) {
                    connected = isAlive(applicationInetAddress);
                }
                Thread.sleep(5000);
            } catch (IOException | InterruptedException ignored) {
            }
        }

        application.notifyConnectionLoss();
    }

    /**
     * method tries to ping 3 times with a 5 seconds timeout for each try
     * if the application doesn't respond for 3 times in a row it returns false
     * if the application responds even once it returns true
     */
    private boolean isAlive(InetAddress applicationInetAddress) {
        boolean isAlive = true;
        for(int i=0; i<3; i++) {
            try {
                if(!applicationInetAddress.isReachable(5000)) {
                    isAlive = false;
                }
                else {
                    isAlive = true;
                    break;
                }
                Thread.sleep(1000);
            } catch (IOException | InterruptedException ignored) {
            }
        }
        return isAlive;
    }
}
