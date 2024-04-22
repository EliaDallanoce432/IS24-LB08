package it.polimi.ingsw.network;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class Pinger implements Runnable{
    private final Socket linkedPonger;
    private final ConnectionObserver observer;

    private final PrintWriter printWriter;
    private final Scanner scanner;
    private boolean isAlive;

    public Pinger(ConnectionObserver observer, InetAddress address, int port) {
        try {
            linkedPonger = new Socket(address,port);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.observer = observer;


        try {
            scanner = new Scanner(linkedPonger.getInputStream());
            printWriter = new PrintWriter(linkedPonger.getOutputStream(), true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        isAlive = true;
    }

    public void run() {
        pinging();
    }

    /**
     * executes the pinging procedure and notifies the ConnectionObserver if there's a connection loss
     */
    private void pinging() {
        while (isAlive) {
            sendPing();
            if (!scanner.hasNextLine() || !scanner.nextLine().equals("PONG")) {
                if(!testingConnectionLiveness()) {
                    observer.connectionLossNotification();
                }
            }
        }
    }

    /**
     * sends a ping message and makes the thread wait 5 seconds
     */
    private void sendPing() {
        printWriter.println("PING");
        System.out.println("pinging");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * checks if the connection is actually dead or the previous ping had some issue
     * @return boolean
     */
    private boolean testingConnectionLiveness() {
        for(int i=0; i<2; i++) {
            sendPing();
            if (scanner.hasNextLine() && scanner.nextLine().equals("PONG")) {return true;}
        }
        isAlive = false;
        return false;
    }

    /**
     * closes connection and interrupts thread execution
     */
    public void shutdown() {
        try {
            linkedPonger.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Thread.currentThread().interrupt();
    }
}
