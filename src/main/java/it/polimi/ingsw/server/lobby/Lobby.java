package it.polimi.ingsw.server.lobby;

import it.polimi.ingsw.network.ClientHandler;
import it.polimi.ingsw.network.ServerWelcomeSocket;
import it.polimi.ingsw.network.ServerNetworkObserver;
import it.polimi.ingsw.server.controller.GameController;
import it.polimi.ingsw.util.customexceptions.AlreadyTakenUsernameException;
import it.polimi.ingsw.util.customexceptions.NonExistentGameException;
import it.polimi.ingsw.util.supportclasses.Request;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Lobby implements ServerNetworkObserver {

    private final List<Request> requests;
    private final ArrayList<ClientHandler> connectedClients;
    private final HashMap<String, GameController> availableGames;
    private final ArrayList<String> takenUsernames;
    private volatile boolean running;
    private final ExecutorService executorService;
    private final LobbyRequestExecutor lobbyRequestExecutor;

    public Lobby(int port) {
        connectedClients = new ArrayList<>();
        availableGames = new HashMap<>();
        takenUsernames = new ArrayList<>();
        requests = Collections.synchronizedList(new ArrayList<>());
        executorService = Executors.newCachedThreadPool();
        lobbyRequestExecutor = new LobbyRequestExecutor(this);
        ServerWelcomeSocket serverWelcomeSocket = new ServerWelcomeSocket(this, port);
        executorService.submit(serverWelcomeSocket);
        running = true;
    }

    public Set<String> getAvailableGames() {
        return availableGames.keySet();
    }

    /**
     * this method start the lobby server that continuously processes requests from clients until the server is shut down
     */
    public void startLobby() {
        while (running) {
            while (!requests.isEmpty()) {
                lobbyRequestExecutor.execute(requests.getFirst());
                requests.removeFirst();
            }
        }
    }

    /**
     * This method adds a new client to the lobby.
     * @param client Client Handler of client
     */
    public void submitNewClient(ClientHandler client) {
        //TODO prendere quello che ritorna la submit
        executorService.submit(client);
        enterLobby(client);
        setRandomGuestUsername(client);
        client.send(LobbyMessageGenerator.usernameSetMessage(client.getUsername()));
    }

    /**
     * This method set a random username to submitted client.
     * @param client Client Handler of client
     */
    private void setRandomGuestUsername(ClientHandler client) {
        boolean usernameNotSet = true;
        while (usernameNotSet) {
            String username = "Guest" + (int) (Math.random() * 100000);
            try {
                setUsername(username,client);
                usernameNotSet = false;
            } catch (AlreadyTakenUsernameException ignored) {
            }
        }
    }
    /**
     * this method submit a new request from lobby
     * @param request request of client
     */
    public synchronized void submitNewRequest(Request request) {
        requests.addLast(request);
    }

    /**
     * this method adds the client to the lobby's arraylist of connected clients
     * @param client client to allow in
     */
    public void enterLobby(ClientHandler client) {
        connectedClients.add(client);
        System.out.println("client connected successfully to the lobby");
    }

    /**
     * this method removes the client to the lobby arraylist of clients waiting inside of it
     * @param client client to allow in
     */
    public void leaveLobby(ClientHandler client) {
        connectedClients.remove(client);
        takenUsernames.remove(client.getUsername());
        System.out.println("client " + client.getUsername() + " left the lobby");
        client.shutdown();
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
     * this method creates a new game with the requested number of players and assign a name to it
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
     * This method removes a game from the list of available games.
     * @param gameName game name available
     */
    public void makeUnavailable(String gameName) {
        availableGames.remove(gameName);
    }

    public void closeGame(String gameName) {
        makeUnavailable(gameName);
        //TODO gestire la chiusura della partita
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
    public void notifyConnectionLoss(ClientHandler clientHandler) {
        System.out.println("client" + clientHandler.getUsername() + " lost connection");
        leaveLobby(clientHandler);
    }

    public void shutdown() {
        //TODO comunicare a tutti i client ancora connessi che il server sta chiudendo
        running = false;
    }
}
