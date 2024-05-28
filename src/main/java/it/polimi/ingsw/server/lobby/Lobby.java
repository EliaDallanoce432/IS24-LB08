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
    private ServerWelcomeSocket serverWelcomeSocket = null;
    private boolean welcomeSocketIsRunning = false;
    private int welcomeSocketPort;
    private final ExecutorService executorService;
    private final LobbyRequestHandler lobbyRequestHandler;
    private boolean echo;
    private volatile boolean running;

    public Lobby() {
        connectedClients = new ArrayList<>();
        games = new HashMap<>();
        availableGames = new HashMap<>();
        takenUsernames = new ArrayList<>();
        requests = Collections.synchronizedList(new ArrayList<>());
        executorService = Executors.newCachedThreadPool();
        lobbyRequestHandler = new LobbyRequestHandler(this);
        ServerView.getInstance(this);
        echo = false;
        running = true;

    }

    /**
     * Initializes the welcome socket that is used by the server to listen for new connections.
     * @param port The port to listen on.
     * @throws CannotOpenWelcomeSocket Thrown when the welcome socket can't be opened on the specified port.
     * @throws WelcomeSocketIsAlreadyOpenException Thrown when the server is already listening on a port.
     */
    public void initializeWelcomeSocket(int port) throws CannotOpenWelcomeSocket, WelcomeSocketIsAlreadyOpenException {
        if (!welcomeSocketIsRunning) {
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
     * Disables the echo function of the server.
     */
    public void echoOff() {
        echo = false;
        for(GameController gameController : games.values()) {
            gameController.echoOff();
        }
        System.out.println();
    }

    /**
     * Enables the echo function of the server.
     */
    public void echoOn() {
        echo = true;
        for(GameController gameController : games.values()) {
            gameController.echoOn();
        }
    }

    /**
     * Starts the lobby execution that continuously processes requests from clients until the server is shut down.
     */
    public void startLobby() {
        System.out.println("Lobby started");
        System.out.println("Echo: off");
        System.out.println("Set a port for the server with 'setPort' command.");
        System.out.println("Type 'help' for more information.");
        System.out.println();
        while (running) {
            while (!requests.isEmpty()) {
                lobbyRequestHandler.execute(requests.getFirst());
                requests.removeFirst();
            }
        }
    }

    /**
     * Adds a new client to the lobby.
     * @param client New client handler.
     */
    public void submitNewClient(ClientHandler client) {
        executorService.submit(client);
        enterLobby(client);
        setRandomGuestUsername(client);
        client.send(LobbyMessageGenerator.usernameSetMessage(client.getUsername()));
    }

    /**
     * Sets a random username to the client.
     * @param client The client handler that needs to be assigned with a random username.
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
     * Submits a new request to lobby.
     * @param request The new request.
     */
    public synchronized void submitNewRequest(Request request) {
        requests.addLast(request);
    }

    /**
     * Adds the client to the lobby's arraylist of connected clients.
     * @param client The client to allow in.
     */
    public void enterLobby(ClientHandler client) {
        if(!connectedClients.contains(client)) {
            connectedClients.add(client);
        }
        if (echo) {
            String username = "";
            if(client.getUsername() != null){ username = client.getUsername() + " "; }
            System.out.println("Client '"+username+ "' is now in the lobby");
        }
        client.send(LobbyMessageGenerator.joinedLobbyMessage());
    }

    /**
     * Removes the client from the lobby.
     * @param client The client to allow in.
     */
    public void leaveLobby(ClientHandler client) {
        connectedClients.remove(client);
        takenUsernames.remove(client.getUsername());
        if (echo) {
            System.out.println("Client '" + client.getUsername() + "' left the lobby");
        }
        client.shutdown();
    }

    /**
     * Allows the client to pick a username, the username is added to the taken usernames list to ensure the uniqueness.
     * @param username The chosen username.
     * @param client The client that is setting the username.
     * @throws AlreadyTakenUsernameException Thrown when trying to choose an already taken username.
     */
    public void setUsername(String username, ClientHandler client) throws AlreadyTakenUsernameException {
        if (client.getUsername() != null) {
            takenUsernames.remove(client.getUsername()); //the client had already a registered username, so now it's going to be changed
        }
        if (!takenUsernames.contains(username)) {
            String oldUsername = client.getUsername();
            takenUsernames.add(username);
            client.setUsername(username);
            if(echo) {
                if (oldUsername != null) {
                    System.out.println("Client '" + oldUsername + "' changed their username to '" + username +"'");
                }
            }
        }
        else {
            throw new AlreadyTakenUsernameException();
        }
    }

    /**
     * Creates a new game with the requested number of players and assign a name to it.
     * @param numberOfPlayers The number of players that will join.
     * @param gameName The name to identify the game.
     */
    public void setupNewGame(int numberOfPlayers, String gameName, ClientHandler client) throws CannotCreateGameException {
        if(gameName == null) throw new CannotCreateGameException("Invalid name!");
        if(games.containsKey(gameName)) throw new CannotCreateGameException("Game name already taken!");
        GameController newGameController = new GameController(this,numberOfPlayers,gameName, echo);
        games.put(gameName, newGameController);
        availableGames.put(gameName,newGameController);
        try {
            joinGame(client, gameName);
        } catch (NonExistentGameException | GameIsFullException ignored) {}
        Thread thread = new Thread(newGameController);
        thread.start();
    }

    /**
     * Removes a game from the list of available games.
     * @param gameName The game name.
     */
    public void makeUnavailable(String gameName) {
        availableGames.remove(gameName);
    }

    /**
     * CLoses the game from the lobby.
     * @param gameName The game name.
     */
    public void closeGame(String gameName) {
        makeUnavailable(gameName);
        games.remove(gameName);
    }

    /**
     * Allows a client to join a game that is waiting for players.
     * @param client The client that wants to join.
     * @param gameName The name of the game to join.
     * @throws NonExistentGameException Thrown when the given game name isn't the name of one of the available games to join.
     */
    public void joinGame(ClientHandler client, String gameName) throws NonExistentGameException, GameIsFullException {
        if(!availableGames.containsKey(gameName)) { throw new NonExistentGameException(); }
        availableGames.get(gameName).enterGame(client);
    }

    @Override
    public void notifyConnectionLoss(ClientHandler clientHandler) {
        if (echo) {
            System.out.println("Client '" + clientHandler.getUsername() + "' lost connection");
        }
        leaveLobby(clientHandler);
    }

    /**
     * Stops the lobby execution.
     */
    public void shutdown() {
        running = false;
        serverWelcomeSocket.shutdown();
        executorService.shutdown();
        System.exit(0);
    }
}
