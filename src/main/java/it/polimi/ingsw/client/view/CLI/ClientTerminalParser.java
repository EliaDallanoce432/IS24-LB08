package it.polimi.ingsw.client.view.CLI;

import it.polimi.ingsw.client.controller.ClientController;
import it.polimi.ingsw.util.cli.CommandParser;

public class ClientTerminalParser implements CommandParser {


    @Override
    public void parse(String command) {
        String[] tokens = command.split("\\s+");
        switch (tokens[0]) {
            case "help": {
                //TODO stampare help
                break;
            }
            case "setUsername" : {
                if (tokens.length == 2) {
                    ClientController.getInstance().sendSetUsernameMessage(tokens[1]);
                }
                else {
                    //TODO parse error
                }
                break;
            }
            case "exit": {
                ClientController.getInstance().shutdown();
                break;
            }
        }
    }
}
