package it.polimi.ingsw.network.sockets;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class InputSocket implements Runnable, SocketInterface {
    private ServerSocket acceptSocket;
    private final SocketObserverInterface socketObserver;
    private Socket socket;
    private Scanner in;
    private PrintWriter out;
    private final JSONParser parser;
    private boolean running;

    public InputSocket(SocketObserverInterface socketObserver) {
        try {
            acceptSocket = new ServerSocket(0);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        parser = new JSONParser();
        this.socketObserver = socketObserver;
        running = true;
//        this.socketObserver = socketObserver;
//        try {
//            socket = new Socket(address,port);
//            in = new Scanner(socket.getInputStream());
//            out = new PrintWriter(socket.getOutputStream(), true);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        parser = new JSONParser();
//        running = true;
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
        if(acceptSocket != null) {
            return acceptSocket.getLocalPort();
        }
        return 0;
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
        try {
            socket = acceptSocket.accept();
            acceptSocket.close();
            acceptSocket = null;
            in = new Scanner(socket.getInputStream());
            out = new PrintWriter(socket.getOutputStream(),true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        while (running) {
            //if(in.hasNextLine()) {
                try {
                    String message = receiveMessage();
                    socketObserver.notifyIncomingMessageFromSocket((JSONObject) parser.parse(message));
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
           // }
        }
    }
}
