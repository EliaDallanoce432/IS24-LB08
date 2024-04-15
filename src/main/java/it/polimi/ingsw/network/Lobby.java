package it.polimi.ingsw.network;

import it.polimi.ingsw.server.controller.GameController;

import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

public class Lobby implements Runnable{

    private ArrayList<ClientHandler> clients;
    private HashMap<String, ClientHandler> availableGames;
    private ArrayList<String> takenUsernames;

    @Override
    public void run() {

    }

    public Lobby() {}

    public void enterLobby(ClientHandler client) {
        clients.add(client);
    }

    public void setUsername(String username, ClientHandler client) {
        if (!takenUsernames.contains(username)) {
            takenUsernames.add(username);
            client.setUsername(username);
        }
         //TODO caso in cui lo username sia già preso
    }

    public GameController setup(int numberOfPlayers, String gameName) {
        //TODO implentazione setup partita
        return null;
    }

    public void join(ClientHandler client, String gameName) {}

}
