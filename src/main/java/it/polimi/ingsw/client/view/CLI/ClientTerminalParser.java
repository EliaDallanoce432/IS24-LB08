package it.polimi.ingsw.client.view.CLI;

import it.polimi.ingsw.client.controller.ClientController;
import it.polimi.ingsw.client.model.PlayerModel;
import it.polimi.ingsw.util.cli.CommandParser;
import org.json.simple.JSONObject;

public class ClientTerminalParser implements CommandParser {


    @Override
    public void parse(String command) {
        String[] tokens = command.split("\\s+");
        switch (tokens[0]) {
            case "help", "?" -> help();
            case "setUsername" -> updateUsername(tokens);
            case "exit" -> ClientController.getInstance().shutdown();
            case "createGame" -> createGame();
            case "joinGame" -> joinGame(tokens);
            case "enterGame" -> enterGame(tokens);
        }
    }

    private void updateUsername(String[] tokens) {
        if (tokens.length == 2) {
            ClientController.getInstance().sendSetUsernameMessage(tokens[1]);
        }
        else {
            //TODO parse error
        }
    }
    private void help() {
        //TODO stampare help
    }
    private void createGame() {

    }

    private void joinGame(String[] tokens) {

    }
    private void enterGame(String[] tokens) {
        if (tokens.length == 2) {
            ClientController.getInstance().sendJoinGameMessage(tokens[1]);
        }
        else {
            //TODO parse error
        }
    }

}
