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
    private final Socket socket;
    private final PrintWriter out;
    private final InputHandler inputHandler;
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

        inputHandler = new InputHandler(this,socket);
        inputHandlerThread = new Thread(inputHandler);
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

    private boolean networkMessageHandling(JSONObject message) {
        if(message.containsKey("type")) {
            switch (message.get("type").toString()) {
                case "pong" -> pinger.notifyPong();
                case "ping" -> {
                    JSONObject pongMessage = new JSONObject();
                    pongMessage.put("type", "pong");
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

    public void shutdown() {
        out.close();
        inputHandler.shutdown();
        inputHandlerThread.interrupt();
        pinger.shutdown();
        pingerThread.interrupt();
        try {
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
