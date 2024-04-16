package it.polimi.ingsw.network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server{
    private final int port;
    Lobby lobby;
    ExecutorService executor = Executors.newCachedThreadPool();

    public Server(int port) {
        this.port = port;
        this.lobby = new Lobby();
        executor.execute(this.lobby);

    }

    //TODO ccontrollare la gestione dell'uscita
    public void StartServer() {
        ExecutorService executor = Executors.newCachedThreadPool();
        ServerSocket serverSocket;
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            System.err.println(e.getMessage());
            return;
        }
        while (true) {
            try {
                Socket socket = serverSocket.accept();
                ClientHandler client = new ClientHandler(socket,lobby);
                lobby.enterLobby(client);
                executor.submit(client);

            } catch (IOException e) {
                break;
            }
        }

        executor.shutdown();
        try {
            serverSocket.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
            return;
        }
    }
}
