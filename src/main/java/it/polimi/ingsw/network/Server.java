package it.polimi.ingsw.network;

import it.polimi.ingsw.Codex;
import it.polimi.ingsw.server.lobby.Lobby;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Server implements Runnable {
    private Codex codexMain;
    private final int port;
    Lobby lobby;
    ExecutorService executor = Executors.newCachedThreadPool();

    public Server(int port, Codex codexMain) {
        this.codexMain = codexMain;
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
            System.out.println("Server ready at: " + InetAddress.getLocalHost());
        } catch (IOException e) {
            System.err.println(e.getMessage());
            return;
        }

        while (!executor.isShutdown()) {
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
            if (!executor.awaitTermination(5000, TimeUnit.MILLISECONDS)) {
                System.err.println("Some clients might not have finished gracefully.");
            }
        } catch (InterruptedException e) {
            System.err.println("Interrupted while waiting for client to finish: " + e.getMessage());
        } finally {
            try {
                serverSocket.close();
            } catch (IOException e) {
                System.err.println("Error closing server socket: " + e.getMessage());
            }
        }
    }
}
