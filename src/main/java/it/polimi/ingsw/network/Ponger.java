package it.polimi.ingsw.network;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Ponger implements Runnable {
    private ServerSocket acceptSocket;
    private Socket linkedPinger;

    public Ponger() {
        try {
            acceptSocket = new ServerSocket(0);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public int getPort() {
        return acceptSocket.getLocalPort();
    }

    public void run() {
        try {
            linkedPinger = acceptSocket.accept();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ponging();
    }

    /**
     * responds to the incoming ping messages until the thread is shutdown
     */
    private void ponging() {
        Scanner scanner;
        PrintWriter printWriter;
        try {
            scanner = new Scanner(linkedPinger.getInputStream());
            printWriter = new PrintWriter(linkedPinger.getOutputStream(),true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        while (true) {
            if (scanner.hasNextLine() && scanner.nextLine().equals("PING")) {
                System.out.println("ponging");
                printWriter.println("PONG");
            }
        }
    }

    /**
     * closes connection and interrupts thread execution
     */
    public void shutdown() {
        try {
            linkedPinger.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Thread.currentThread().interrupt();
    }
}
