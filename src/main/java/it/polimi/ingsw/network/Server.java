package it.polimi.ingsw.network;

import it.polimi.ingsw.server.lobby.Lobby;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server implements Runnable {
    private final int port;
    Lobby lobby;
    ExecutorService executor = Executors.newCachedThreadPool();

    public Server(int port) {
        this.port = port;
        this.lobby = new Lobby();
        executor.execute(this.lobby);

    }

    public void run() {}

    //TODO controllare la gestione dell'uscita
    public void startServer() {
        ExecutorService executor = Executors.newCachedThreadPool();
        ServerSocket serverSocket;
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            System.err.println(e.getMessage());
            return;
        }
        try {
            System.out.println("Server ready at: " + InetAddress.getLocalHost() + ":" + serverSocket.getLocalPort());
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
        while (true) {
            try {
                Socket socket = serverSocket.accept();
                System.out.println("server address: " + socket.getInetAddress());
                System.out.println("client address: " + socket.getRemoteSocketAddress());
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
