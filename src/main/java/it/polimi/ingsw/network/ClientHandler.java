package it.polimi.ingsw.network;

import it.polimi.ingsw.server.controller.GameController;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler implements Runnable, NetworkInterface{
    private final Socket socket;
    private String username;
    private final Thread pingerThread;

    private GameController game;
    private Lobby lobby;

    private BufferedReader in;
    private PrintWriter out;
    private JSONObject receivedMessage;

    public ClientHandler(Socket socket, Lobby lobby) {
        this.lobby = lobby;
        receivedMessage = null;
        this.socket = socket;
        this.pingerThread = new Thread(new Pinger(this));
        pingerThread.start();
        try {
            this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.out = new PrintWriter(socket.getOutputStream());
        } catch (IOException ignored) {}
    }

    public JSONObject getReceivedMessage() {
        return receivedMessage;
    }

    public GameController getGame() {
        return game;
    }

    public void setGame(GameController game) {
        this.game = game;
    }

    @Override
    public void run() {
        while(true) {
            receiveMessage();
            if(receivedMessage != null) {
                game.notifyServerOfIncomingMessage(this);
            }
        }
    }

    @Override
    public Socket getSocket() {
        return socket;
    }

    @Override
    public synchronized void sendMessage(JSONObject jsonMessage) {
        out.println(jsonMessage);
        out.flush();
    }

    private void receiveMessage() {
        try {
            String receivedString = in.readLine();
            JSONParser parser = new JSONParser();
            receivedMessage = (JSONObject) parser.parse(receivedString);
        } catch (IOException | ParseException ignored) {
        }

        //TODO decidere cosa fare con le eccezioni

    }

    public String getUsername() {
        return username;
    }

    public synchronized void setUsername(String username) {
        this.username = username;
    }



    /**
     * method informs lobby or game that the connection was lost to ensure a safe disconnection
     */
    private void connectionLossProcedure() {

    }

    /**
     * method terminates the pinger, closes the socket connection and terminates the thread current execution
     */
    public synchronized void closeConnection() {
        pingerThread.interrupt();
        try {
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Thread.currentThread().interrupt();
    }

    @Override
    public synchronized void notifyConnectionLoss() {
        connectionLossProcedure();
        closeConnection();
    }
}
