package it.polimi.ingsw.network;

import it.polimi.ingsw.network.ping.ConnectionChecker;
import it.polimi.ingsw.network.sockets.InputSocket;
import it.polimi.ingsw.network.sockets.OutputSocket;
import it.polimi.ingsw.network.sockets.SocketObserverInterface;
import it.polimi.ingsw.server.controller.GameController;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class ClientHandler implements Runnable, NetworkInterface, SocketObserverInterface, ConnectionObserver{
    private final OutputSocket outputSocket;
    private final InputSocket inputSocket;
    private boolean running;
    private final Thread outputSocketThread;
    private final Thread inputSocketThread;
    private final ConnectionChecker connectionChecker;

    private JSONObject receivedRequest;
    private final JSONParser jsonParser;

    private String username;
    private GameController game;
    private final Lobby lobby;
    private boolean isInGame;
    private boolean isReady;

    public ClientHandler(Socket setUpSocket, Lobby lobby) {
        Scanner scanner;
        PrintWriter printWriter;
        try {
            scanner = new Scanner(setUpSocket.getInputStream());
            printWriter = new PrintWriter(setUpSocket.getOutputStream(),true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        /* setting up input socket and sharing the ports with the client output socket */
        inputSocket = new InputSocket(this);
        printWriter.println(inputSocket.getSocketPort());
        int clientInputSocketPort = Integer.parseInt(scanner.nextLine());

        /* setting up output socket */
        outputSocket = new OutputSocket(setUpSocket.getInetAddress().getHostAddress(), clientInputSocketPort);

        /* starting communication threads */
        inputSocketThread = new Thread(inputSocket);
        outputSocketThread = new Thread(outputSocket);
        inputSocketThread.start();
        System.out.println("clienthandler input socket ready");
        outputSocketThread.start();
        System.out.println("clienthandler output socket ready");

        this.lobby = lobby;
        this.connectionChecker = new ConnectionChecker(this, setUpSocket);
        System.out.println("pinger and ponger created");
        this.jsonParser = new JSONParser();
        try {
            setUpSocket.close();
            scanner.close();
            printWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public JSONObject getReceivedRequest() {
        return receivedRequest;
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

    public boolean isInGame() {
        return isInGame;
    }

    public void setInGame(boolean inGame) {
        isInGame = inGame;
    }

    public boolean isReady() {
        return isReady;
    }

    public void setReady(boolean ready) {
        isReady = ready;
    }


    public Lobby getLobby() {
        return lobby;
    }

    @Override
    public void run() {
        while(running) {}
    }


    @Override
    public void connectionLossNotification() {
        if(isInGame) {
            game.notifyConnectionLoss(this);
        }
        lobby.notifyConnectionLoss(this);
        shutdown();
    }

    @Override
    public JSONObject send(JSONObject message) {
        outputSocket.sendMessage(message.toJSONString());
        try {
            return (JSONObject) jsonParser.parse(outputSocket.receiveMessage());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void reply(JSONObject message) {
        inputSocket.sendMessage(message.toJSONString());
    }

    @Override
    public void notifyIncomingMessageFromSocket(JSONObject message) {
        receivedRequest = message;
        if(isInGame) {
            game.notifyIncomingMessage(this);
        }
        else {
            lobby.notifyIncomingMessage(this);
        }
    }

    @Override
    public int exchangePongerPorts(int pongerPort) {
        outputSocket.sendMessage(String.valueOf(pongerPort));
        return Integer.parseInt(outputSocket.receiveMessage());
    }

    @Override
    public InetAddress getRemotePongerAddress() {
        return outputSocket.getSocketAddress();
    }

    public void shutdown() {
        //TODO controller shutdown
        connectionChecker.shutdown();
        outputSocket.shutdown();
        inputSocket.shutdown();
        outputSocketThread.interrupt();
        inputSocketThread.interrupt();
        running = false;
    }
}
