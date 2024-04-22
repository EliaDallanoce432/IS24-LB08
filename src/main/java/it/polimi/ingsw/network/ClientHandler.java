package it.polimi.ingsw.network;

import it.polimi.ingsw.server.controller.GameController;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class ClientHandler implements Runnable, NetworkInterface, ConnectionObserver{
    private final Socket socket;
    private final ConnectionChecker connectionChecker;
    private String username;

    private GameController game;
    private final Lobby lobby;
    private boolean isInGame;

    private BufferedReader in;
    private PrintWriter out;
    private JSONObject receivedRequest;

    public ClientHandler(Socket socket, Lobby lobby) {
        this.isInGame = false;
        this.lobby = lobby;
        receivedRequest = null;
        this.socket = socket;
        try {
            this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.out = new PrintWriter(socket.getOutputStream(),true);
        } catch (IOException ignored) {}
        this.connectionChecker = new ConnectionChecker(this);
    }

    public JSONObject getReceivedRequest() {
        return receivedRequest;
    }

    public GameController getGame() {
        return game;
    }

    public void setGame(GameController game) {
        this.game = game;
    }

    public boolean isInGame() {
        return isInGame;
    }

    public void setInGame(boolean inGame) {
        isInGame = inGame;
    }

    @Override
    public void run() {
        while(true) {
            receiveMessage();
            if (receivedRequest != null) {
                if (isInGame) {
                    game.notifyServerOfIncomingMessage(this);
                } else {
                    lobby.notifyServerOfIncomingMessage(this);
                }
                receivedRequest = null;
            }
        }
    }

    @Override
    public Socket getSocket() {
        return socket;
    }

    @Override
    public void sendMessage(JSONObject jsonMessage) {
        out.println(jsonMessage);
    }

    /**
     * method receives a string from the standard input, if the string is not null it converts it in a json object and puts it in the attribute receivedRequest
     */
    private void receiveMessage() {
        try {
            String receivedString = in.readLine();
            if(receivedString != null) {
                JSONParser parser = new JSONParser();
                receivedRequest = (JSONObject) parser.parse(receivedString);
            } else {
                receivedRequest = null;
            }
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
    private synchronized void connectionLossProcedure() {
        JSONObject message = new JSONObject();
        message.put("command", "connectionLost");
        receivedRequest = message;
        if(isInGame) {
            game.notifyServerOfIncomingMessage(this);
        }
        lobby.notifyServerOfIncomingMessage(this);
    }

    @Override
    public void connectionLossNotification() {
        connectionChecker.shutdown();
        System.out.println("lost connection with the client " + getUsername());
        connectionLossProcedure();
        //TODO clean up per chiudere il client

        try {
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Thread.currentThread().interrupt();
    }

    @Override
    public int exchangePongerPorts(int localPongerPort) {
        out.println(localPongerPort);
        try {
            return Integer.parseInt(in.readLine()); //remote ponger port returned by the server
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public InetAddress getRemotePongerAddress() {
        return socket.getInetAddress();
    }
}
