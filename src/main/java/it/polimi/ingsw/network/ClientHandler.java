package it.polimi.ingsw.network;

import java.io.IOException;
import java.net.Socket;

public class ClientHandler implements Runnable{
    private Socket socket;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {

    }

    public void closeConnection() {
        try {
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //TO DO : stop execution

    }
}
