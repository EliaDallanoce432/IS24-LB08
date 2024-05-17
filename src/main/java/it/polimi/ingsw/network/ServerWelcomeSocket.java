package it.polimi.ingsw.network;

import it.polimi.ingsw.server.lobby.Lobby;
import it.polimi.ingsw.util.customexceptions.CannotOpenWelcomeSocket;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * This class accepts the new client connections
 */
public class ServerWelcomeSocket implements Runnable {
    private final ServerSocket serverSocket;
    Lobby lobby;
    boolean running;

    public ServerWelcomeSocket(Lobby lobby, int port) throws CannotOpenWelcomeSocket {
        this.lobby = lobby;
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Server ready at: " + InetAddress.getLocalHost() + " on port: " + port);
        } catch (IOException e) {
            throw new CannotOpenWelcomeSocket();
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
