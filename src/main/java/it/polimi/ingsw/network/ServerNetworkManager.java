package it.polimi.ingsw.network;

import it.polimi.ingsw.server.controller.GameController;

import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerNetworkManager {
    GameController gameController;
    private ExecutorService executor;
    private ArrayList<ClientHandler> clientHandlers;

    public ServerNetworkManager(int port) {
        executor = Executors.newCachedThreadPool();
        executor.submit(new WelcomeServerSocket(port,this));
        executor.submit(new Pinger());

        clientHandlers = new ArrayList<>();
    }

    public void addClient(ClientHandler client) {
        clientHandlers.add(client);
    }

    public void closeConnections() {
        executor.shutdown();
    }
}
