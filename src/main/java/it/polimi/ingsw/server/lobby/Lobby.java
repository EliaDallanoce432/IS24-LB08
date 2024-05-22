package it.polimi.ingsw.server.lobby;

import it.polimi.ingsw.network.ClientHandler;
import it.polimi.ingsw.network.ServerWelcomeSocket;
import it.polimi.ingsw.network.ServerNetworkObserver;
import it.polimi.ingsw.server.controller.GameController;
import it.polimi.ingsw.server.view.ServerView;
import it.polimi.ingsw.util.customexceptions.*;
import it.polimi.ingsw.util.supportclasses.Request;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * This class represents the lobby where players can create or join a game and set their usernames
 */
public class Lobby implements ServerNetworkObserver {

    private final List<Request> requests;
    private final ArrayList<ClientHandler> connectedClients;
    private final HashMap<String, GameController> games;
    private final HashMap<String, GameController> availableGames;
    private final ArrayList<String> takenUsernames;
    private boolean welcomeSocketIsRunning = false;
    private int welcomeSocketPort;
    private volatile boolean running;
    private final ExecutorService executorService;
    private final LobbyRequestHandler lobbyRequestHandler;

    public Lobby() {
        connectedClients = new ArrayList<>();
        games = new HashMap<>();
        availableGames = new HashMap<>();
        takenUsernames = new ArrayList<>();
        requests = Collections.synchronizedList(new ArrayList<>());
        executorService = Executors.newCachedThreadPool();
        lobbyRequestHandler = new LobbyRequestHandler(this);
        ServerView.getInstance(this);
        running = true;
        System.out.println("Lobby started");
        System.out.println("Set a port for the server with 'setPort' command.");
        System.out.println("Type 'help' for more information.");
        System.out.println();
    }

    public void initializeWelcomeSocket(int port) throws CannotOpenWelcomeSocket, WelcomeSocketIsAlreadyOpenException {
        if (!welcomeSocketIsRunning) {
            ServerWelcomeSocket serverWelcomeSocket;
            serverWelcomeSocket = new ServerWelcomeSocket(this, port);
            executorService.submit(serverWelcomeSocket);
            welcomeSocketPort = port;
            welcomeSocketIsRunning = true;
        }
        else throw new WelcomeSocketIsAlreadyOpenException(String.valueOf(welcomeSocketPort));
    }

    public HashMap<String, GameController> getAvailableGames() {
        return availableGames;
    }

    public HashMap<String, GameController> getGames() {
        return games;
    }

    public ArrayList<ClientHandler> getConnectedClients() {
        return connectedClients;
    }

    public int getWelcomeSocketPort() {
        return welcomeSocketPort;
    }

    /**
     * starts the lobby server that continuously processes requests from clients until the server is shut down
     */
    public void startLobby() {
        while (running) {
            while (!requests.isEmpty()) {
                lobbyRequestHandler.execute(requests.getFirst());
                requests.removeFirst();
            }
        }
    }

    /**
     * adds a new client to the lobby.
     * @param client Client Handler of client
     */
    public void submitNewClient(ClientHandler client) {
        executorService.submit(client);
        enterLobby(client);
        setRandomGuestUsername(client);
        client.send(LobbyMessageGenerator.usernameSetMessage(client.getUsername()));
    }

    /**
     * sets a random username to submitted client.
     * @param client Client Handler of client
     */
    private void setRandomGuestUsername(ClientHandler client){
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
     * submits a new request from lobby
     * @param request request of client
     */
    public synchronized void submitNewRequest(Request request) {
        requests.addLast(request);
    }

    /**
     * adds the client to the lobby's arraylist of connected clients
     * @param client client to allow in
     */
    public void enterLobby(ClientHandler client) {
        connectedClients.add(client);
        System.out.println("client connected successfully to the lobby");
        client.send(LobbyMessageGenerator.joinedLobbyMessage());
    }

    /**
     * removes the client to the lobby arraylist of clients waiting inside of it
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
     * creates a new game with the requested number of players and assign a name to it
     * @param numberOfPlayers number of players that will join
     * @param gameName name to identify the game
     */
    public void setupNewGame(int numberOfPlayers, String gameName, ClientHandler client) throws CannotCreateGameException {
        if(gameName == null) throw new CannotCreateGameException("Invalid name!");
        if(games.containsKey(gameName)) throw new CannotCreateGameException("Game name already taken!");
        GameController newGameController = new GameController(this,numberOfPlayers,gameName);
        games.put(gameName, newGameController);
        availableGames.put(gameName,newGameController);
        try {
            newGameController.enterGame(client);
        } catch (GameIsFullException ignored) {
        }
        Thread thread = new Thread(newGameController);
        thread.start();
    }

    /**
     * removes a game from the list of available games.
     * @param gameName game name available
     */
    public void makeUnavailable(String gameName) {
        availableGames.remove(gameName);
    }

    public void closeGame(String gameName) {
        makeUnavailable(gameName);
        games.remove(gameName);
    }

    /**
     * allows a client to join a game that is waiting for players
     * @param client client that wants to join
     * @param gameName name of the game to join
     * @throws NonExistentGameException exception thrown when the given game name isn't the name of one of the available games to join
     */
    public void joinGame(ClientHandler client, String gameName) throws NonExistentGameException, GameIsFullException {
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
        running = false;
        executorService.shutdown();
        System.exit(0);
    }
}
