package it.polimi.ingsw.server.view;

import it.polimi.ingsw.util.cli.CommandParser;

public class ServerTerminalParser implements CommandParser {

    @Override
    public void parse(String command) {
        String[] tokens = command.split("\\s+");
        switch (tokens[0]) {
            case "help": {
                if (tokens.length == 1) {
                    ServerView.getInstance().printHelp();
                }
                else ServerView.getInstance().parseError();
                break;
            }
            case "setPort": {
                if (tokens.length == 2) {
                    ServerView.getInstance().setPort(Integer.parseInt(tokens[1]));
                }
                else ServerView.getInstance().parseError();
                break;
            }
            case "shutdown": {
                if (tokens.length == 1) {
                    ServerView.getInstance().shutdown();
                }
                else ServerView.getInstance().parseError();
                break;
            }
            default: {
                ServerView.getInstance().parseError();
                break;
            }
        }
    }
}
