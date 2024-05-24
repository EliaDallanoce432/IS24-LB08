package it.polimi.ingsw.server.view;

import it.polimi.ingsw.network.ClientHandler;
import it.polimi.ingsw.server.controller.GameController;
import it.polimi.ingsw.server.lobby.Lobby;
import it.polimi.ingsw.util.customexceptions.CannotOpenWelcomeSocket;
import it.polimi.ingsw.util.customexceptions.WelcomeSocketIsAlreadyOpenException;

/**
 * This class manages the retrieval of data from the server or setting options in the server and displays them to the user through the terminal.
 * It implements the singleton pattern.
 */
public class ServerView {
    private static ServerView instance;
    private final ServerTerminalInputReader serverTerminalInputReader;
    private final Thread inputThread;
    private final Lobby lobby;


    private ServerView(Lobby lobby) {
        serverTerminalInputReader = ServerTerminalInputReader.getInstance();
        inputThread = new Thread(serverTerminalInputReader);
        inputThread.start();
        this.lobby = lobby;
    }

    public static ServerView getInstance(Lobby lobby) {
        if(instance == null) {
            instance = new ServerView(lobby);
        }
        return instance;
    }

    public static ServerView getInstance() {
        return instance;
    }

    /**
     * Shows on the terminal the available commands for the user
     */
    public void printHelp() {
        System.out.println("Available commands:");
        System.out.println("┌────────────────────────┬────────────────────────────────────────────────────────┐");
        System.out.println("│      commands          │                       Description                      │");
        System.out.println("├────────────────────────┴────────────────────────────────────────────────────────┤");
        System.out.printf("%-20s %-36s", "│ setport <port>         │", "Run Server at set port (default port 12345)            │\n");
        System.out.println("├────────────────────────┴────────────────────────────────────────────────────────┤");
        System.out.printf("%-20s %-36s", "│ port                   │", "Shows the current listening port                       │\n");
        System.out.println("├────────────────────────┴────────────────────────────────────────────────────────┤");
        System.out.printf("%-20s %-36s", "│ clients                │", "Shows the currently connected clients                  │\n");
        System.out.println("├────────────────────────┴────────────────────────────────────────────────────────┤");
        System.out.printf("%-20s %-36s", "│ games                  │", "Shows the current running games                        │\n");
        System.out.println("├────────────────────────┴────────────────────────────────────────────────────────┤");
        System.out.printf("%-20s %-36s", "│ games --info  <game>   │", "Shows the info of the specific game                    │\n");
        System.out.println("├────────────────────────┴────────────────────────────────────────────────────────┤");
        System.out.printf("%-20s %-36s", "│ echo <on/off>          │", "Enables or disables the verbose prints from the server │\n");
        System.out.println("├────────────────────────┴────────────────────────────────────────────────────────┤");
        System.out.printf("%-20s %-36s", "│ shutdown               │", "shuts down the server                                  │\n");
        System.out.println("└────────────────────────┴────────────────────────────────────────────────────────┘");
        System.out.println();
    }

    /**
     * Allows the user to enable or disable the echo functionality of the server.
     * The echo functionality makes the server log on the terminal every activity that has just been performed, either from the lobby or the games.
     * @param echo If set to true it enables the echo functionality. When set to false it disables the functionality.
     */
    public void setEcho(boolean echo) {
        if(echo) {
            lobby.echoOn();
            System.out.println("Echo enabled");
        }
        else {
            lobby.echoOff();
            System.out.println("Echo disabled");
            System.out.println();
        }
    }

    /**
     * Allows the user to set the port used by the server to accept new connections with the clients.
     * @param port The integer value of the port. It must bew between 1 and 65535.
     */
    public void setPort(int port) {
        if(port < 1 || port > 65535) {
            System.out.println("Port must be between 1 and 65535!");
            System.out.println();
            return;
        }
        try {
            lobby.initializeWelcomeSocket(port);
        } catch (CannotOpenWelcomeSocket e) {
            System.out.println("Could not open welcome socket on port " + port + "!");
            System.out.println("Please try again on another port.");
        } catch (WelcomeSocketIsAlreadyOpenException e) {
            System.out.println("The server is already listening on port " + e.getMessage() + ".");
        }
        finally {
            System.out.println();
        }
    }

    /**
     * Allows the user to know the port that is currently used for accepting by the server to accept new connections with the clients.
     */
    public void showPort() {
        System.out.println("The server is listening on port: " + lobby.getWelcomeSocketPort());
        System.out.println();
    }

    /**
     * Shows to the user the clients currently connected to the server and where they are (in the lobby or in a game)
     */
    public void showConnectedClients() {
        System.out.println("Connected clients:");
        System.out.println();
        if (!lobby.getConnectedClients().isEmpty()) {
            for (ClientHandler client : lobby.getConnectedClients()) {
                System.out.println(client.getUsername());
                System.out.println(client.getInetAddress());
                if (client.isInGame()) System.out.println("Playing in '" + client.getGameController().getGameName() + "'");
                else System.out.println("In the lobby");
                System.out.println();
            }
        }
        else System.out.println("none");
        System.out.println();
    }

    /**
     * Shows to the user the games that are currently open/running.
     */
    public void showGames() {
        System.out.println("Games waiting for players to join:");
        System.out.println();
        if (!lobby.getAvailableGames().isEmpty()) {
            for (String gameName : lobby.getAvailableGames().keySet()) {
                printGameWaitingForPlayers(gameName);
            }
        } else System.out.println("none");
        System.out.println();
        System.out.println("Games in progress:");
        System.out.println();
        if (lobby.getAvailableGames().size() < lobby.getGames().size()) {
            for (String gameName : lobby.getGames().keySet()) {
                if (lobby.getAvailableGames().containsKey(gameName)) continue;
                quickPrintGameInProgress(gameName);
            }
        } else System.out.println("none");
        System.out.println();
    }

    private void printGameWaitingForPlayers(String gameName) {
        GameController gameController = lobby.getAvailableGames().get(gameName);
        System.out.println(gameName + " - " + "(" + gameController.getClientHandlers().size() + "/" + gameController.getNumberOfPlayers() + ")");
    }

    private void quickPrintGameInProgress(String gameName) {
        GameController gameController = lobby.getGames().get(gameName);
        System.out.println(gameName + " - " + gameController.getNumberOfPlayers() + " players");
    }

    private void fullInfoPrintGameInProgress(String gameName) {
        GameController gameController = lobby.getGames().get(gameName);
        System.out.println(gameName + " - " + gameController.getNumberOfPlayers() + " players");
        System.out.println("Players:");
        for (ClientHandler client : gameController.getClientHandlers()) {
            System.out.println(client.getUsername() + " has " + gameController.getCurrentPlayer(client).getScore() + " points");
        }
        System.out.println("Currently it's the turn of: " + gameController.getTurnPlayerUsername());
        System.out.println();
    }

    /**
     * Shows to the users more detailed info about a specified game (if such information is available).
     * @param gameName The name of the game of which to display more info.
     */
    public void showGameInfo(String gameName) {
        if (!lobby.getGames().containsKey(gameName)) {
            System.out.println("Game '" + gameName + "' not found.");
            System.out.println();
            return;
        }
        if(lobby.getAvailableGames().containsKey(gameName)) printGameWaitingForPlayers(gameName);
        else fullInfoPrintGameInProgress(gameName);
    }

    /**
     * Lets the user know that a parsing error has occurred.
     */
    public void parseError() {
        System.out.println("Unexpected command or arguments");
        System.out.println("Try 'help' for more information about the available commands.");
        System.out.println();
    }

    /**
     * Executes the needed cleanups before shutting down the server
     */
    public void shutdown() {
        System.out.println("Shutting down the server...");
        serverTerminalInputReader.shutdown();
        inputThread.interrupt();
        lobby.shutdown();
    }


}
