package it.polimi.ingsw.network;

import it.polimi.ingsw.server.controller.GameController;
import it.polimi.ingsw.util.customexceptions.AlreadyTakenUsernameException;
import it.polimi.ingsw.util.customexceptions.NonExistentGameException;

import java.util.ArrayList;
import java.util.HashMap;

public class Lobby implements Runnable,ClientObserver{

    private ArrayList<ClientHandler> connectedClients;
    private HashMap<String, GameController> availableGames;
    private ArrayList<String> takenUsernames;

    @Override
    public void run() {


    }

    public Lobby() {
        connectedClients = new ArrayList<>();
        availableGames = new HashMap<>();
        takenUsernames = new ArrayList<>();
    }

    public HashMap<String, GameController> getAvailableGames() {
        return availableGames;
    }

    /**
     * method adds the client to the lobby arraylist of clients connected to it
     * @param client client to allow in
     */
    public void enterLobby(ClientHandler client) {
        connectedClients.add(client);
        System.out.println("client connected successfully to the lobby");
    }

    /**
     * method removes the client to the lobby arraylist of clients waiting inside of it
     * @param client client to allow in
     */
    public void leaveLobby(ClientHandler client) {
        connectedClients.remove(client);
        takenUsernames.remove(client.getUsername());
        client.closeConnection();
        System.out.println("client " + client.getUsername() + " left the lobby");
    }

    /**
     * allows the client to pick a username, the username is added to the taken usernames list to ensure the uniqueness
     * @param username chosen username
     * @param client client that is setting the username
     * @throws AlreadyTakenUsernameException exception gets thrown when trying to choose an already taken username
     */
    public void setUsername(String username, ClientHandler client) throws AlreadyTakenUsernameException {
        if (client.getUsername() != null) {
            takenUsernames.remove(client.getUsername()); //the client had already a registered username, so now it's going to be changed
        }
        if (!takenUsernames.contains(username)) {
            takenUsernames.add(username);
            client.setUsername(username);
        }
        else {
            throw new AlreadyTakenUsernameException();
        }
    }

    /**
     * method create a new game with the requested number of players and assign a name to it
     * @param numberOfPlayers number of players that will join
     * @param gameName name to identify the game
     */
    public void setupNewGame(int numberOfPlayers, String gameName, ClientHandler client) {
        GameController newGameController = new GameController(this,numberOfPlayers,gameName);
        availableGames.put(gameName,newGameController);
        newGameController.enterGame(client);
        Thread thread = new Thread(newGameController);
        thread.start();
    }

    /**
     * method allows a client to join a game that is waiting for players
     * @param client client that wants to join
     * @param gameName name of the game to join
     * @throws NonExistentGameException exception thrown when the given game name isn't the name of one of the available games to join
     */
    public void joinGame(ClientHandler client, String gameName) throws NonExistentGameException {
        if(!availableGames.containsKey(gameName)) { throw new NonExistentGameException(); }
        connectedClients.remove(client);
        availableGames.get(gameName).enterGame(client);
    }

    @Override
    public void notifyServerOfIncomingMessage(ClientHandler clientWithTheMessage) {
        LobbyRequestExecutor.execute(this,clientWithTheMessage.getReceivedRequest(),clientWithTheMessage);
    }
}
