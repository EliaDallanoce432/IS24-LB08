package it.polimi.ingsw.network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class WelcomeServerSocket implements Runnable{
    private int port;
    ServerNetworkManager serverNetworkManager;
    public WelcomeServerSocket(int port,ServerNetworkManager serverNetworkManager) {
        this.port = port;
        this.serverNetworkManager = serverNetworkManager;
    }

    @Override
    public void run() {
        ExecutorService executor = Executors.newCachedThreadPool();
        ServerSocket serverSocket;

        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return;
        }
        while (true) {
            try {
                Socket socket = serverSocket.accept();
                ClientHandler client = new ClientHandler(socket);
                serverNetworkManager.addClient(client);
                executor.submit(client);

            } catch (IOException e) {
                break;
            }
        }

        executor.shutdown();
        try {
            serverSocket.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return;
        }
    }
}
