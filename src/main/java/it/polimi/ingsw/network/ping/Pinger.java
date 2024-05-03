package it.polimi.ingsw.network.ping;

import it.polimi.ingsw.network.ConnectionObserver;
import it.polimi.ingsw.network.sockets.SocketInfoInterface;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class Pinger implements Runnable, SocketInfoInterface {
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
            if(!sendPing()) continue;
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
    private boolean sendPing() {
        printWriter.println("PING");
        //System.out.println("pinging");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            return false;
        }
        return true;
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
        isAlive = false;
    }

    @Override
    public InetAddress getSocketAddress() {
        return linkedPonger.getInetAddress();
    }

    @Override
    public int getSocketPort() {
        return linkedPonger.getPort();
    }
}
