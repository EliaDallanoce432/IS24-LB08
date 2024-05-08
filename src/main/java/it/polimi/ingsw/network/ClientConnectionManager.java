package it.polimi.ingsw.network;

import it.polimi.ingsw.client.controller.ClientController;
import it.polimi.ingsw.network.ping.Pinger;
import it.polimi.ingsw.network.sockets.InputHandler;
import it.polimi.ingsw.network.sockets.networkInputObserver;
import it.polimi.ingsw.util.customexceptions.ServerUnreachableException;
import org.json.simple.JSONObject;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientConnectionManager implements NetworkInterface, networkInputObserver, ConnectionObserver{
    private Socket socket = null;
    private PrintWriter out = null;
    private final InputHandler inputHandler;
    private final Thread inputHandlerThread;
    private final Pinger pinger;
    private final Thread pingerThread;

    private JSONObject receivedMessage;
    private volatile JSONObject receivedReply;
    private final ClientController clientController;

    public ClientConnectionManager(ClientController clientController, String serverAddress, int port) throws ServerUnreachableException {
        this.clientController = clientController;
        try {
            socket = new Socket(serverAddress,port);
            out = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            //TODO segnalare che la connessione non si è aperta
        }

        inputHandler = new InputHandler(this,socket);
        inputHandlerThread = new Thread(inputHandler);
        inputHandlerThread.start();

        pinger = new Pinger(this);
        pingerThread = new Thread(pinger);
        pingerThread.start();
    }

    public JSONObject getReceivedMessage() {
        return receivedMessage;
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
        System.out.println(reply);
        return reply;
    }

    @Override
    public void send(JSONObject message) {
        send(message,false);
    }


    @Override
    public void notifyIncomingMessageFromSocket(JSONObject message) {
        if(!networkMessageHandling(message)) {
            receivedMessage = message;
            clientController.notifyIncomingMessage();
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
        clientController.notifyConnectionLoss();
    }

    public void shutdown() {
        out.close();
        inputHandler.shutdown();
        inputHandlerThread.interrupt();
        pinger.shutdown();
        pingerThread.interrupt();        try {
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
