package it.polimi.ingsw.network.input;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

/**
 * this class handles the input from the TCP socket
 */
public class InputHandler implements Runnable {

    private final NetworkInputObserver socketObserver;
    private final Scanner in;
    private final JSONParser parser;
    private volatile boolean running;

    public InputHandler(NetworkInputObserver socketObserver, Socket socket) {
        parser = new JSONParser();
        this.socketObserver = socketObserver;
        try {
            in = new Scanner(socket.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        running = true;
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

    /**
     * stops the InputHandler execution
     */
    public void shutdown() {
        running = false;
    }
}
