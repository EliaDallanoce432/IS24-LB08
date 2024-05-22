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
                if (tokens.length == 1) {
                    ServerView.getInstance().setPort(12345);
                }
                else if (tokens.length == 2) {
                    ServerView.getInstance().setPort(Integer.parseInt(tokens[1]));
                }
                else ServerView.getInstance().parseError();
                break;
            }
            case "port": {
                if (tokens.length == 1) {
                    ServerView.getInstance().showPort();
                }
                else ServerView.getInstance().parseError();
                break;
            }
            case "clients": {
                if (tokens.length == 1) {
                    ServerView.getInstance().showConnectedClients();
                }
                else ServerView.getInstance().parseError();
                break;
            }
            case "games": {
                if (tokens.length == 1) {
                    ServerView.getInstance().showGames();
                }
                else if(tokens.length == 3 && tokens[1].equals("--info")) {
                    ServerView.getInstance().showGameInfo(tokens[2]);
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
