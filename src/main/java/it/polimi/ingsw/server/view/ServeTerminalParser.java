package it.polimi.ingsw.server.view;

import it.polimi.ingsw.util.cli.CommandParser;

public class ServeTerminalParser implements CommandParser {
    private final ServerView serverView;

    public ServeTerminalParser() {
        serverView = ServerView.getInstance();
    }

    @Override
    public void parse(String command) {
        switch (command) {
            case "help": serverView.printHelp();
            case "setPort": serverView.setPort(10);
            case "shutdown": serverView.shutdown();
        }
    }
}
