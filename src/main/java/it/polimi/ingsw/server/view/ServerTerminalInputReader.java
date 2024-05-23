package it.polimi.ingsw.server.view;

import it.polimi.ingsw.util.cli.TerminalInputReader;

/**
 * This class represents the specific TerminalInputReader for the server. It implements the singleton pattern.
 */
public class ServerTerminalInputReader extends TerminalInputReader {
    private static ServerTerminalInputReader instance;

    private ServerTerminalInputReader() {
        this.commandParser = new ServerTerminalParser();
    }

    public static ServerTerminalInputReader getInstance() {
        if(instance == null) {
            instance = new ServerTerminalInputReader();
        }
        return instance;
    }
}
