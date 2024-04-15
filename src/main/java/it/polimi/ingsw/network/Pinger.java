package it.polimi.ingsw.network;

import it.polimi.ingsw.util.supportclasses.Observer;

import java.io.IOException;
import java.net.InetAddress;

public class Pinger implements Runnable, Observer {
    private ClientHandler client;
    private InetAddress clientIp;

    public Pinger (ClientHandler client) {
        this.client = client;
        this.clientIp = client.getSocket().getInetAddress();
    }

    @Override
    public void run() {
        boolean connected = true;
        while (connected) {
            try {
                if(!clientIp.isReachable(5000)) {
                    connected = false;
                    continue;
                }
                Thread.sleep(5000);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        //TODO mettere il messaggio corretto o parametro da destinarsi
        update("lost connection");
        Thread.currentThread().interrupt();
    }

    @Override
    public void update(String message) {
        client.closeConnection();
    }
}
