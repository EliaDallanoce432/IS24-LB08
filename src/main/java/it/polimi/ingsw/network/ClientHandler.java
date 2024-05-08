package it.polimi.ingsw.network;

import it.polimi.ingsw.network.ping.Pinger;
import it.polimi.ingsw.network.sockets.InputHandler;
import it.polimi.ingsw.network.sockets.networkInputObserver;
import it.polimi.ingsw.server.controller.GameController;
import it.polimi.ingsw.server.lobby.Lobby;
import it.polimi.ingsw.util.supportclasses.Color;
import it.polimi.ingsw.util.supportclasses.Request;
import org.json.simple.JSONObject;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler implements Runnable, NetworkInterface, networkInputObserver, ConnectionObserver{
    private PrintWriter out;
    private Socket socket;
    private final InputHandler inputHandler;
    private final Thread inputHandlerThread;
    private final Pinger pinger;
    private final Thread pingerThread;
    private volatile boolean running;
    private volatile JSONObject receivedReply;

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
        while(running) {}
    }

    @Override
    public JSONObject send(JSONObject message, boolean waitReply) {
        out.println(message.toJSONString());
        JSONObject reply = null;
        if (waitReply) {
            while (receivedReply == null) {
                Thread.onSpinWait();
            }
            reply = receivedReply;
            receivedReply = null;
        }
        return reply;
    }

    @Override
    public void send(JSONObject message) {
        send(message, false);
    }


    @Override
    public void notifyIncomingMessageFromSocket(JSONObject message) {
        if(!networkMessageHandling(message)) {
            if(isInGame) {
                game.addNewRequest(new Request(this, message));
            }
            else {
                lobby.addNewRequest(new Request(this, message));
            }
        }
    }

    private boolean networkMessageHandling(JSONObject message) {
        if(message.containsKey("type")) {
            switch (message.get("type").toString()) {
                case "pong" -> {
                    pinger.notifyPong();
                }
                case "ping" -> {
                    JSONObject pongMessage = new JSONObject();
                    pongMessage.put("type", "pong");
                    out.println(pongMessage);
                }
                case "reply" -> {
                    while (receivedReply != null) {
                        Thread.onSpinWait();
                    }
                    receivedReply = message;
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

    public void shutdown() {
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
        Thread.currentThread().interrupt();
    }
}
