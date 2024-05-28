package it.polimi.ingsw.client.view.CLI;

import it.polimi.ingsw.util.cli.TerminalInputReader;

/**
 * This class is specifically designed for reading user input in the context of the Codex game client.
 */
public class ClientTerminalInputReader extends TerminalInputReader {
    public ClientTerminalInputReader() {
        commandParser = new ClientTerminalParser();
    }
}