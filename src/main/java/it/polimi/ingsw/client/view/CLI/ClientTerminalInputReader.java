package it.polimi.ingsw.client.view.CLI;

import it.polimi.ingsw.util.cli.CommandParser;
import it.polimi.ingsw.util.cli.TerminalInputReader;

public class ClientTerminalInputReader extends TerminalInputReader {
    public ClientTerminalInputReader() {
        commandParser = new ClientTerminalParser();
    }
}
