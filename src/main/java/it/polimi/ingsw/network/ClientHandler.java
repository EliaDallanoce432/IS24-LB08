package it.polimi.ingsw.network;

import it.polimi.ingsw.util.supportclasses.Observer;

import java.io.IOException;
import java.net.Socket;

public class ClientHandler implements Runnable, Observer {
    private Socket socket;
    private String username;
    private Pinger pinger;
    public ClientHandler(Socket socket) {
        this.socket = socket;
        this.pinger = new Pinger(this);
        Thread t = new Thread(this.pinger);
        t.start();
    }
    @Override
    public void run() {

    }

    public String getUsername() {
        return username;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    //TODO adattare la funzione alle nuove esigenze in modo che chiuda tutto in maniera corretta
    public void closeConnection() {
        try {
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Thread.currentThread().interrupt();
    }

    @Override
    public void update(String message) {

    }
}
