package it.polimi.ingsw.network;

import it.polimi.ingsw.server.controller.GameController;
import it.polimi.ingsw.util.customexceptions.AlreadyTakenUsernameException;

import java.util.ArrayList;
import java.util.HashMap;

public class Lobby implements Runnable{

    private ArrayList<ClientHandler> clientsWaitingInTheLobby;
    private HashMap<String, GameController> availableGames;
    private ArrayList<String> takenUsernames;

    @Override
    public void run() {


    }

    public Lobby() {
        clientsWaitingInTheLobby = new ArrayList<>();
        availableGames = new HashMap<>();
        takenUsernames = new ArrayList<>();
    }

    public void enterLobby(ClientHandler client) {
        clientsWaitingInTheLobby.add(client);
    }

    public void exitLobby(ClientHandler client) {
        clientsWaitingInTheLobby.remove(client);
        takenUsernames.remove(client.getUsername());
        client.closeConnection();
    }

    public void setUsername(String username, ClientHandler client) throws AlreadyTakenUsernameException {
        if (!takenUsernames.contains(username)) {
            takenUsernames.add(username);
            client.setUsername(username);
        }
        else {
            throw new AlreadyTakenUsernameException();
        }
    }

    public void setup(int numberOfPlayers, String gameName) {
        GameController newGameController = new GameController(this,numberOfPlayers,gameName);
        availableGames.put(gameName,newGameController);
    }

    public void join(ClientHandler client, String gameName) {
        client.setGame(availableGames.get(gameName));
        clientsWaitingInTheLobby.remove(client);
        availableGames.get(gameName).enterGame(client);
    }

}
