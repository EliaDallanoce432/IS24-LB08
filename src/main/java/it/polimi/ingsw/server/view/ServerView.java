package it.polimi.ingsw.server.view;

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
            return instance;
        }
        else return instance;
    }

    public static ServerView getInstance() {
        return instance;
    }

    public void printHelp() {
        System.out.println("Available commands:");
        System.out.println("setPort [port number]: allows you to set the port where the server is listening on");
        System.out.println("shutdown: allows you to shutdown the server");
    }

    public void setPort(int port) {
        try {
            lobby.initializeWelcomeSocket(port);
        } catch (CannotOpenWelcomeSocket e) {
            System.out.println("Could not open welcome socket on port " + port + "!");
            System.out.println("Please try again on another port.");
        } catch (WelcomeSocketIsAlreadyOpenException e) {
            System.out.println("The server is already listening on port " + e.getMessage() + ".");
        }
    }

    public void parseError() {
        System.out.println("Unexpected command or arguments");
        System.out.println("Try 'help' for more information.");
    }

    public void shutdown() {
        System.out.println("Shutting down the server...");
        serverTerminalInputReader.shutdown();
        inputThread.interrupt();
        lobby.shutdown();
    }


}
