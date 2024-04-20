package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.network.ClientHandler;
import it.polimi.ingsw.network.ClientObserver;
import it.polimi.ingsw.network.Lobby;
import org.json.simple.JSONObject;

import java.util.ArrayList;

public class GameController implements Runnable, ClientObserver {
    private String gameName;
    private int numberOfPlayers;
    private ArrayList<ClientHandler> players;
    private Lobby lobby;

    public GameController(Lobby lobby, int numberOfPlayers, String gameName) {
        this.players = new ArrayList<>();
        this.numberOfPlayers = numberOfPlayers;
        this.lobby = lobby;
        System.out.println(gameName + " is ready");
    }

    public String getGameName() {
        return gameName;
    }

    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public synchronized void enterGame(ClientHandler player) {
        players.add(player);
        player.setGame(this);
        player.setInGame(true);
    }

    public synchronized void leaveGame(ClientHandler player) {
        players.remove(player);
        player.setGame(null);
        player.setInGame(false);
        lobby.enterLobby(player);
    }

    public void run() {
        waitForEveryoneToJoinAndBeReady();
        gamePreparation();

    }

    private void waitForEveryoneToJoinAndBeReady() {

    }

    private void gamePreparation(){

    }


    @Override
    public void notifyServerOfIncomingMessage(ClientHandler clientWithTheMessage) {
        JSONObject jsonMessage = clientWithTheMessage.getReceivedRequest();
        //TODO chiamare il pareser e agire di conseguenza
    }
}
