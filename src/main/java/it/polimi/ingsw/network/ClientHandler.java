package it.polimi.ingsw.network;

import it.polimi.ingsw.network.ping.Pinger;
import it.polimi.ingsw.network.input.InputHandler;
import it.polimi.ingsw.server.controller.GameController;
import it.polimi.ingsw.server.lobby.Lobby;
import it.polimi.ingsw.util.supportclasses.Request;
import org.json.simple.JSONObject;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/**
 * this class offers network functionalities for the server between the server and the client
 */
public class ClientHandler implements Runnable, NetworkInterface {
    private final PrintWriter out;
    private final Socket socket;
    private final InputHandler inputHandler;
    private final Thread inputHandlerThread;
    private final Pinger pinger;
    private final Thread pingerThread;
    private volatile boolean running;

    private String username;
    private GameController game = null;
    private final Lobby lobby;
    private boolean isInGame;

    public ClientHandler(Socket socket, Lobby lobby) {
        this.lobby = lobby;
        this.socket = socket;
        try {
            out= new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        inputHandler = new InputHandler(this,socket);
        inputHandlerThread = new Thread(inputHandler);
        inputHandlerThread.start();

        pinger = new Pinger(this);
        pingerThread = new Thread(pinger);
        pingerThread.start();

        isInGame = false;
        running = true;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public GameController getGame() {
        return game;
    }

    public void setGame(GameController game) {
        this.game = game;
    }

    public void setInGame(boolean inGame) {
        isInGame = inGame;
    }

    @Override
    public void run() {
        while (running) {
            Thread.onSpinWait();
        }
    }

    @Override
    public void send(JSONObject message) {
        out.println(message.toJSONString());
    }

    @Override
    public void notifyIncomingMessageFromSocket(JSONObject message) {
        if(!networkMessageHandling(message)) {
            if(isInGame) {
                game.submitNewRequest(new Request(this, message));
            }
            else {
                lobby.submitNewRequest(new Request(this, message));
            }
        }
    }

    /**
     * handles messages that are not meant for the higher level, but they are service messages for the proper network functionality
     * @param message message to handle
     * @return returns true if it was a service message, false if it's a message for the application
     */
    private boolean networkMessageHandling(JSONObject message) {
        if(message.containsKey("type")) {
            switch (message.get("type").toString()) {
                case "pong" -> pinger.notifyPong();
                case "ping" -> {
                    Map<String,String> jsonMap = new HashMap<>();
                    jsonMap.put("type", "pong");
                    JSONObject pongMessage = new JSONObject(jsonMap);
                    out.println(pongMessage);
                }
                default -> {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public void connectionLossNotification() {
        if(isInGame) {
            game.notifyConnectionLoss(this);
        }
        else {lobby.notifyConnectionLoss(this);}
        shutdown();
    }

    /**
     * closes every service that was open and ends the connection
     */
    public void shutdown() {
        pinger.shutdown();
        out.close();
        inputHandler.shutdown();
        inputHandlerThread.interrupt();
        pingerThread.interrupt();
        try {
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        running = false;
    }
}
