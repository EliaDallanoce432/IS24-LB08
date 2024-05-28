package it.polimi.ingsw.server.view;

import it.polimi.ingsw.util.cli.CommandParser;
import static it.polimi.ingsw.util.supportclasses.Constants.INVALID_ARGUMENT_COUNT_MESSAGE;
import static it.polimi.ingsw.util.supportclasses.Constants.INVALID_ARGUMENT_TYPE_MESSAGE;

/**
 * This class parses the commands imparted to the server by the user through the terminal.
 */
public class ServerTerminalParser implements CommandParser {

    @Override
    public void parse(String command) {
        String[] tokens = command.split("\\s+");
        tokens[0] = tokens[0].toLowerCase();
        switch (tokens[0]) {
            case "help", "h" -> handleHelp(tokens);
            case "echo" -> handleEcho(tokens);
            case "setport", "sp" -> handleSetPort(tokens);
            case "port","p" -> handlePort(tokens);
            case "clients" -> handleClients(tokens);
            case "games" -> handleGames(tokens);
            case "shutdown" -> handleShutdown(tokens);
            default -> ServerView.getInstance().parseError();
        }
    }

    private void handleHelp(String[] tokens) {
        if (tokens.length > 1) {
            System.out.println(INVALID_ARGUMENT_COUNT_MESSAGE);
            return;
        }
        ServerView.getInstance().printHelp();
    }

    private void handleEcho(String[] tokens) {
        if (tokens.length != 2) {
            System.out.println(INVALID_ARGUMENT_COUNT_MESSAGE);
            return;
        }
        String argument = tokens[1].toLowerCase();
        if (!argument.equals("on") && !argument.equals("off")) {
            System.out.println(INVALID_ARGUMENT_TYPE_MESSAGE);
            return;
        }
        ServerView.getInstance().setEcho(argument.equals("on"));
    }

    private void handleSetPort(String[] tokens) {
        if (tokens.length == 1) {
            ServerView.getInstance().setPort(12345);
        } else if (tokens.length == 2) {
            try {
                int port = Integer.parseInt(tokens[1]);
                if (port <= 0 || port > 65535) {
                    System.out.println("Invalid port number: must be between 1 and 65535");
                    return;
                }
                ServerView.getInstance().setPort(port);
            } catch (NumberFormatException e) {
                System.out.println(INVALID_ARGUMENT_TYPE_MESSAGE);
            }
        } else {
            System.out.println(INVALID_ARGUMENT_COUNT_MESSAGE);
        }
    }

    private void handlePort(String[] tokens) {
        if (tokens.length > 1) {
            System.out.println(INVALID_ARGUMENT_COUNT_MESSAGE);
            return;
        }
        ServerView.getInstance().showPort();
    }

    private void handleClients(String[] tokens) {
        if (tokens.length > 1) {
            System.out.println(INVALID_ARGUMENT_COUNT_MESSAGE);
            return;
        }
        ServerView.getInstance().showConnectedClients();
    }

    private void handleGames(String[] tokens) {
        if (tokens.length == 1) {
            ServerView.getInstance().showGames();
        } else if (tokens.length == 3 && tokens[1].equals("--info")) {
            String gameName = tokens[2];
            if (gameName.isEmpty()) {
                System.out.println("Please provide a game name");
                return;
            }
            ServerView.getInstance().showGameInfo(gameName);
        } else {
            System.out.println(INVALID_ARGUMENT_COUNT_MESSAGE);
        }
    }

    private void handleShutdown(String[] tokens) {
        if (tokens.length > 1) {
            System.out.println(INVALID_ARGUMENT_COUNT_MESSAGE);
            return;
        }
        ServerView.getInstance().shutdown();
    }
}
