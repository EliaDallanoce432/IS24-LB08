package it.polimi.ingsw.network;

import it.polimi.ingsw.server.lobby.Lobby;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements Runnable {
    private ServerSocket serverSocket;
    Lobby lobby;
    boolean running;

    public Server(Lobby lobby, int port) {
        this.lobby = lobby;
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Server ready at: " + InetAddress.getLocalHost());
        } catch (IOException e) {
            //TODO lanciare eccezione se non si apre la accept socket
            throw new RuntimeException(e);
        }
        running = true;
    }

    @Override
    public void run() {
        while (running) {
            try {
                Socket socket = serverSocket.accept();
                System.out.println("server address: " + socket.getInetAddress());
                System.out.println("client address: " + socket.getRemoteSocketAddress());
                ClientHandler client = new ClientHandler(socket,lobby);
                lobby.submitNewClient(client);
            } catch (IOException e) {
                break;
            }
        }
    }
}
