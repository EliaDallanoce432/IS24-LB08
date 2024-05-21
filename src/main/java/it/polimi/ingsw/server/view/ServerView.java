package it.polimi.ingsw.server.view;

import it.polimi.ingsw.util.cli.TerminalInputReader;

public class ServerView {
    private static ServerView instance;
    private TerminalInputReader terminalInputReader;


    private ServerView() {
        terminalInputReader = new TerminalInputReader(new ServeTerminalParser());
    }

    public static ServerView getInstance() {
        if(instance == null) {
            instance = new ServerView();
            return instance;
        }
        return instance;
    }

    public void printHelp() {

    }

    public void setPort(int port) {

    }

    public void shutdown() {

    }


}
