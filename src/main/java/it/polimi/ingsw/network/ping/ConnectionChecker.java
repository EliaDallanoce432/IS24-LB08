package it.polimi.ingsw.network.ping;

import it.polimi.ingsw.network.ConnectionObserver;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class ConnectionChecker {
    ConnectionObserver connectionObserver;
    private final Pinger pinger;
    private final Ponger ponger;

    private Thread pingerThread;
    private Thread pongerThread;

    public ConnectionChecker(ConnectionObserver observer, Socket setUpSocket) {
        this.connectionObserver = observer;
        this.ponger = new Ponger();

        Scanner scanner;
        PrintWriter printWriter;
        try {
            scanner = new Scanner(setUpSocket.getInputStream());
            printWriter = new PrintWriter(setUpSocket.getOutputStream(), true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        /* exchanging ponger ports */
        printWriter.println(ponger.getPort());
        int remotePongerPort =  Integer.parseInt(scanner.nextLine());

        this.pinger = new Pinger(observer,setUpSocket.getInetAddress(),remotePongerPort);
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
        pingerThread.interrupt();
        pongerThread.interrupt();
    }
}
