package it.polimi.ingsw.network.sockets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class OutputSocket implements Runnable, SocketInterface {
    private final Socket socket;
    private final Scanner in;
    private final PrintWriter out;
    private boolean running;

    public OutputSocket(String address, int port) {
        try {
            socket = new Socket(address,port);
            in = new Scanner((socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        running = true;
    }

    @Override
    public void sendMessage(String message) {
        out.println(message);
    }

    @Override
    public String receiveMessage() {
        return in.nextLine();
    }

    @Override
    public InetAddress getSocketAddress() {
        return socket.getInetAddress();
    }

    @Override
    public int getSocketPort() {
        return socket.getPort();
    }

    @Override
    public void shutdown() {
        try {
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        running = false;
    }

    @Override
    public void run() {
        while (running) {}
    }
}
