package it.polimi.ingsw.network;

import it.polimi.ingsw.client.controller.ClientController;
import it.polimi.ingsw.network.ping.Pinger;
import it.polimi.ingsw.network.input.NetworkInputHandler;
import it.polimi.ingsw.util.customexceptions.ServerUnreachableException;
import org.json.simple.JSONObject;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/**
 * This class offers networks functionalities for the client.
 */
public class ClientConnectionManager implements NetworkInterface {
    private final Socket socket;
    private final PrintWriter out;
    private final NetworkInputHandler networkInputHandler;
    private final Thread inputHandlerThread;
    private final Pinger pinger;
    private final Thread pingerThread;
    private final ClientController clientController;

    public ClientConnectionManager(ClientController clientController, String serverAddress, int port) throws ServerUnreachableException {
        this.clientController = clientController;
        try {
            socket = new Socket(serverAddress,port);
            out = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            throw new ServerUnreachableException();
        }

        networkInputHandler = new NetworkInputHandler(this,socket);
        inputHandlerThread = new Thread(networkInputHandler);
        inputHandlerThread.start();

        pinger = new Pinger(this);
        pingerThread = new Thread(pinger);
        pingerThread.start();
    }

    @Override
    public void send(JSONObject message) {
        out.println(message.toJSONString());
    }


    @Override
    public void notifyIncomingMessageFromSocket(JSONObject message) {
        if(!networkMessageHandling(message)) {
            clientController.processMessage(message);
        }
    }

    /**
     * Handles messages that are not meant for the higher level, but they are service messages for the proper network functionality.
     * @param message The message to handle.
     * @return true if it was a service message, false if it's a message for the application.
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
        clientController.notifyConnectionLoss();
    }

    /**
     * Closes every service that was open and ends the connection.
     */
    public void shutdown() {
        pinger.shutdown();
        out.close();
        networkInputHandler.shutdown();
        inputHandlerThread.interrupt();
        pingerThread.interrupt();
        try {
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
