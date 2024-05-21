package it.polimi.ingsw.client.view.CLI;

import it.polimi.ingsw.client.controller.ClientController;
import it.polimi.ingsw.util.cli.CommandParser;

public class ClientTerminalParser implements CommandParser {


    @Override
    public void parse(String command) {
        String[] tokens = command.split("\\s+");
        switch (tokens[0]) {
            case "help", "?" -> help();
            case "setUsername" -> updateUsername(tokens);
            case "exit" -> ClientController.getInstance().shutdown();
            case "createGame" -> createGame(tokens);
            case "getAvailableGames" -> getAvailableGames();
            case "joinGame" -> joinGame(tokens);
            case "enterGame" -> enterGame(tokens);
            case "setReady" -> setReady();
            case "selectStarterCard" -> selectStarterCardOrientation(tokens);
            case "selectSecreteObjective" -> selectSecretObjective(tokens);
            case "place" -> place(tokens);
            case "directDrawResource" -> directDrawResource();
            case "drawLeftResource" -> drawLeftResource();
            case "drawRightResource" -> drawRightResource();
            case "directDrawGold" -> directDrawGold();
            case "drawLeftGold" -> drawLeftGold();
            case "drawRightGold" -> drawRightGold();

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
    private void createGame(String[] tokens) {
        if (tokens.length == 3) {
            ClientController.getInstance().sendSetUpGameMessage(tokens[1], Integer.parseInt(tokens[2]));
        }
    }

    private void getAvailableGames () {
        ClientController.getInstance().sendGetAvailableGamesMessage();
    }

    private void joinGame(String[] tokens) {
        if (tokens.length == 2) ClientController.getInstance().sendJoinGameMessage(tokens[1]);
    }
    private void enterGame(String[] tokens) {
        if (tokens.length == 2) {
            ClientController.getInstance().sendJoinGameMessage(tokens[1]);
        }
        else {
            //TODO parse error
        }
    }

    private void setReady() {
        ClientController.getInstance().sendReadyMessage();
    }

    private void selectStarterCardOrientation(String[] tokens) {
        if (tokens.length == 3) {
            ClientController.getInstance().sendChosenStarterCardSideMessage(Integer.parseInt(tokens[1]), Boolean.parseBoolean(tokens[2]));
        }
        else {
            //TODO parse error
        }
    }

    private void selectSecretObjective (String[] tokens) {
        if (tokens.length == 2) {
            ClientController.getInstance().sendChosenSecretObjectiveMessage(Integer.parseInt(tokens[1]));
        }
        else {
            //TODO parse error
        }
    }

    private void place(String[] tokens) {
        if (tokens.length == 5) {
            ClientController.getInstance().sendPlaceMessage(Integer.parseInt(tokens[1]),Integer.parseInt(tokens[2]), Integer.parseInt(tokens[3]), Boolean.parseBoolean(tokens[4]));
        }
        else {
            //TODO parse error
        }
    }

    private void directDrawResource() {
        ClientController.getInstance().sendDirectDrawResourceCardMessage();
    }

    private void drawLeftResource() {
        ClientController.getInstance().sendDrawLeftResourceCardMessage();
    }

    private void drawRightResource() {
        ClientController.getInstance().sendDrawRightResourceCardMessage();
    }

    private void directDrawGold() {
        ClientController.getInstance().sendDirectDrawGoldCardMessage();
    }

    private void drawLeftGold() {
        ClientController.getInstance().sendDrawLeftGoldCardMessage();
    }

    private void drawRightGold() {
        ClientController.getInstance().sendDrawRightGoldCardMessage();
    }
}
