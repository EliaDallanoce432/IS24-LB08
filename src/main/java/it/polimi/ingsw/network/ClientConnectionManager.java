package it.polimi.ingsw.network;

import it.polimi.ingsw.client.controller.ClientController;
import it.polimi.ingsw.network.ping.ConnectionChecker;
import it.polimi.ingsw.network.sockets.InputSocket;
import it.polimi.ingsw.network.sockets.OutputSocket;
import it.polimi.ingsw.network.sockets.SocketObserverInterface;
import it.polimi.ingsw.util.customexceptions.ServerUnreachableException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class ClientConnectionManager implements Runnable, NetworkInterface, SocketObserverInterface, ConnectionObserver{
    private final InputSocket inputSocket;
    private final OutputSocket outputSocket;
    private final ConnectionChecker connectionChecker;
    private final Thread inputSocketThread;
    private final Thread outputSocketThread;

    private JSONObject receivedMessage;
    private final JSONParser jsonParser;
    private final ClientController clientController;
    private final Thread clientControllerThread;
    private boolean running;

    public ClientConnectionManager(String serverAddress, int port) throws ServerUnreachableException {
        Scanner scanner;
        PrintWriter printWriter;
        Socket setUpSocket;
        try {
            setUpSocket = new Socket(serverAddress, port);
        } catch (IOException e) {
            System.out.println("Could not connect to server at: " + serverAddress + ":" + port);
            throw new ServerUnreachableException();
        }
        try {
            scanner = new Scanner(setUpSocket.getInputStream());
            printWriter = new PrintWriter(setUpSocket.getOutputStream(), true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        /* setting up input socket and sharing the ports with the server output socket */
        inputSocket = new InputSocket(this);
        printWriter.println(inputSocket.getSocketPort());
        int serverInputSocketPort = Integer.parseInt(scanner.nextLine());

        /* setting up output socket */
        outputSocket = new OutputSocket(serverAddress,serverInputSocketPort);

        /* starting communication threads */
        inputSocketThread = new Thread(inputSocket);
        outputSocketThread = new Thread(outputSocket);
        inputSocketThread.start();
        System.out.println("input socket created");
        outputSocketThread.start();
        System.out.println("output socket created");

        this.connectionChecker = new ConnectionChecker(this,setUpSocket);
        System.out.println("pinger and ponger created");
        jsonParser = new JSONParser();

        try {
            setUpSocket.close();
            scanner.close();
            printWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        this.clientController = new ClientController(this);
        this.clientControllerThread = new Thread(clientController);
        clientControllerThread.start();
        running = true;
    }

    public JSONObject getReceivedMessage() {
        return receivedMessage;
    }

    @Override
    public JSONObject send(JSONObject message) {
        outputSocket.sendMessage(message.toJSONString());
        System.out.println("message sent");
        try {
            String response = outputSocket.receiveMessage();
            System.out.println("response: " + response);
            return (JSONObject) jsonParser.parse(response);
        } catch (ParseException e) {
            System.out.println("parse exception");
            throw new RuntimeException(e);
        }
    }

    @Override
    public void reply(JSONObject message) {
        inputSocket.sendMessage(message.toJSONString());
    }

    @Override
    public void notifyIncomingMessageFromSocket(JSONObject message) {
        receivedMessage = message;
        clientController.notifyIncomingMessage();
    }

    @Override
    public void run() {
        while (running) {}
    }

    @Override
    public void connectionLossNotification() {
        //TODO notifica il controller
        shutdown();
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
