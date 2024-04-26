package it.polimi.ingsw.network.ping;

import it.polimi.ingsw.network.sockets.SocketInfoInterface;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Ponger implements Runnable, SocketInfoInterface {
    private ServerSocket acceptSocket;
    private Socket linkedPinger;
    boolean running;

    public Ponger() {
        try {
            acceptSocket = new ServerSocket(0);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        running = true;
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
        while (running) {
            if (scanner.hasNextLine() && scanner.nextLine().equals("PING")) {
                //System.out.println("ponging");
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
        running = false;
    }

    @Override
    public InetAddress getSocketAddress() {
        return linkedPinger.getInetAddress();
    }

    @Override
    public int getSocketPort() {
        return linkedPinger.getPort();
    }
}
