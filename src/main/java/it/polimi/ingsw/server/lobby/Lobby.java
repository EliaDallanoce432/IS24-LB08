package it.polimi.ingsw.server.lobby;

import it.polimi.ingsw.network.ClientHandler;
import it.polimi.ingsw.network.Server;
import it.polimi.ingsw.network.ServerNetworkObserverInterface;
import it.polimi.ingsw.server.controller.GameController;
import it.polimi.ingsw.util.customexceptions.AlreadyTakenUsernameException;
import it.polimi.ingsw.util.customexceptions.NonExistentGameException;
import it.polimi.ingsw.util.supportclasses.Request;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Lobby implements ServerNetworkObserverInterface {

    private List<Request> requests;
    private ArrayList<ClientHandler> connectedClients;
    private HashMap<String, GameController> availableGames;
    private ArrayList<String> takenUsernames;
    private volatile boolean running;
    private ExecutorService executorService;
    private LobbyRequestExecutor lobbyRequestExecutor;
    private Server server;

    public void startLobby() {
        while (running) {
            while (!requests.isEmpty()) {
                lobbyRequestExecutor.execute(requests.getFirst());
                requests.removeFirst();
                System.out.println("executed request");
            }
        }
    }

    public Lobby(int port) {
        connectedClients = new ArrayList<>();
        availableGames = new HashMap<>();
        takenUsernames = new ArrayList<>();
        requests = Collections.synchronizedList(new ArrayList<Request>());
        executorService = Executors.newCachedThreadPool();
        lobbyRequestExecutor = new LobbyRequestExecutor(this);
        server = new Server(this, port);
        executorService.submit(server);
        running = true;
    }

    public void submitNewCLient(ClientHandler client) {
        //TODO prendere quello che ritorna la submit
        executorService.submit(client);
        connectedClients.add(client);
        System.out.println("New client added to the lobby");
    }

    public synchronized void addNewRequest(Request request) {
        requests.addLast(request);
        System.out.println("New request added to the lobby");
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
    public void notifyIncomingMessage(ClientHandler clientHandler) {
        System.out.println("incoming message");
    }

    @Override
    public void notifyConnectionLoss(ClientHandler clientHandler) {
        System.out.println("client" + clientHandler.getUsername() + " lost connection");
        leaveLobby(clientHandler);
    }

    public void shutdown() {
        //TODO comunicare a tutti i client ancora connessi che il server sta chiudendo
        running = false;
    }
}
