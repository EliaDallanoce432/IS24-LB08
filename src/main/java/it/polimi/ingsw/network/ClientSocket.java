package it.polimi.ingsw.network;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientSocket implements Runnable, NetworkInterface {
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private Pinger pinger;

    JSONObject receivedMessage;

    public ClientSocket(String serverAddress, int port) {
        try {
            socket = new Socket(serverAddress,port);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        pinger = new Pinger(this);
    }

    @Override
    public Socket getSocket() {
        return socket;
    }

    @Override
    public synchronized void sendMessage(JSONObject message) {
        out.println(message.toJSONString());
        out.flush();
        receiveMessage();
        //TODO gestire la risposta che torna indietro
    }

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
        //TODO decidere cosa fare con le eccezioni
    }

    @Override
    public void notifyConnectionLoss() {

    }

    @Override
    public void run() {
        //catches messages sent spontaneously from the server to give updates on data in the game
        receiveMessage();
        if(receivedMessage != null) {
            System.out.println("server replied: " + receivedMessage.get("response"));
        }
    }
}
