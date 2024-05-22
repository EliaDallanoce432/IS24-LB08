package it.polimi.ingsw.server.view;

import it.polimi.ingsw.network.ClientHandler;
import it.polimi.ingsw.server.controller.GameController;
import it.polimi.ingsw.server.lobby.Lobby;
import it.polimi.ingsw.util.customexceptions.CannotOpenWelcomeSocket;
import it.polimi.ingsw.util.customexceptions.WelcomeSocketIsAlreadyOpenException;

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

    public void printHelp() {
        System.out.println("Available commands:");
        System.out.println("setport [port number]: allows you to set the port where the server is listening on (leave [port number] to set it on the default port 12345)");
        System.out.println("port: shows you on what port the server is listening to" );
        System.out.println("echo [on/off]: enables or disables the verbose prints from the server at every relevant event (from the lobby and games too)");
        System.out.println("clients: shows the currently connected clients to the server");
        System.out.println("games: shows the current running games");
        System.out.println("games --info [game name]: shows the specific infos of the specified game");
        System.out.println("shutdown: shuts down the server");
        System.out.println();
    }

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

    public void showPort() {
        System.out.println("The server is listening on port: " + lobby.getWelcomeSocketPort());
        System.out.println();
    }

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

    public void showGameInfo(String gameName) {
        if (!lobby.getGames().containsKey(gameName)) {
            System.out.println("Game '" + gameName + "' not found.");
            System.out.println();
            return;
        }
        if(lobby.getAvailableGames().containsKey(gameName)) printGameWaitingForPlayers(gameName);
        else fullInfoPrintGameInProgress(gameName);
    }

    public void parseError() {
        System.out.println("Unexpected command or arguments");
        System.out.println("Try 'help' for more information.");
        System.out.println();
    }

    public void shutdown() {
        System.out.println("Shutting down the server...");
        serverTerminalInputReader.shutdown();
        inputThread.interrupt();
        lobby.shutdown();
    }


}
