package it.polimi.ingsw.network.sockets;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class InputHandler implements Runnable {

    private final networkInputObserver socketObserver;
    private Scanner in;
    private final JSONParser parser;
    private volatile boolean running;

    public InputHandler(networkInputObserver socketObserver, Socket socket) {
        parser = new JSONParser();
        this.socketObserver = socketObserver;
        try {
            in = new Scanner(socket.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        running = true;
    }

    public void shutdown() {
        running = false;
    }

    @Override
    public void run() {
        while (running) {
            try {
                if (in.hasNextLine()) {
                    String message = in.nextLine();
                    if(message != null) {
                        socketObserver.notifyIncomingMessageFromSocket((JSONObject) parser.parse(message));
                    }
                }
            } catch (ParseException e) {
                //TODO gestire meglio l'eccezione
                throw new RuntimeException(e);
            }
        }
    }
}
