package it.polimi.ingsw.network;

import it.polimi.ingsw.client.controller.ClientController;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class ClientSocket implements Runnable, NetworkInterface, ConnectionObserver {
    private final Socket socket;
    private final BufferedReader in;
    private final PrintWriter out;
    private final ConnectionChecker connectionChecker;

    JSONObject receivedMessage;

    private final ClientController clientController;
    Thread thread;

    public ClientSocket(String serverAddress, int port) {
        try {
            socket = new Socket(serverAddress,port);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(),true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.connectionChecker = new ConnectionChecker(this);
        clientController = new ClientController(this);
        thread = new Thread(clientController);
        thread.start();
    }

    @Override
    public Socket getSocket() {
        return socket;
    }

    /**
     * method sends the message and notifies when the reply is ready
     * @param message message to send to the server
     */
    public void send(JSONObject message) {
        sendMessage(message);
        receiveMessage();
    }

    @Override
    public void sendMessage(JSONObject message) {
        out.println(message.toJSONString());
    }

    /**
     * allows to read an incoming message and notify when it's ready to be parsed
     */
    public void receiveMessage() {
        try {
            String receivedString = in.readLine();
            if(receivedString != null) {
                JSONParser parser = new JSONParser();
                receivedMessage = (JSONObject) parser.parse(receivedString);
            } else {
                receivedMessage = null;
            }
        } catch (IOException | ParseException ignored) {
        }
        System.out.println("response: "+receivedMessage.toJSONString());
        //TODO decidere cosa fare con le eccezioni
        //TODO notificare che è arrivato il messaggio
    }

    @Override
    public void run() {
        //TODO sistemare
        //catches messages sent spontaneously from the server to give updates on data in the game
        while (true) {
//            receiveMessage();
//            if(receivedMessage != null) {
//                System.out.println("server replied: " + receivedMessage.get("response"));
//            }
        }
    }

    @Override
    public void connectionLossNotification() {
        connectionChecker.shutdown();
        System.out.println("lost connection with the server");
        try {
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //TODO clean up per chiudere il client

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
