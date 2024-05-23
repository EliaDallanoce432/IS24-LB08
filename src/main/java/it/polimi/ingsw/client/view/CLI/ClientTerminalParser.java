package it.polimi.ingsw.client.view.CLI;

import it.polimi.ingsw.client.controller.ClientController;
import it.polimi.ingsw.client.model.AvailableGamesModel;
import it.polimi.ingsw.client.model.ClientStateModel;
import it.polimi.ingsw.client.model.SelectableCardsModel;
import it.polimi.ingsw.util.cli.CommandParser;
import it.polimi.ingsw.util.customexceptions.InvalidIdException;
import it.polimi.ingsw.util.supportclasses.ClientState;

import java.util.Objects;

public class ClientTerminalParser implements CommandParser {


    @Override
    public void parse(String command) {
        String[] tokens = command.split("\\s+");
        tokens[0] = tokens[0].toLowerCase();
        switch (tokens[0]) {
            case "help","h","?" -> help();
            case "setusername","su" -> updateUsername(tokens);
            case "leave", "le" -> leave();
            case "quit", "q" -> ClientController.getInstance().shutdownForCLI();
            case "create", "c" -> createGame(tokens);
            case "availablegames", "ag" -> getAvailableGames();
            case "info" -> getInfo(tokens);
            case "join", "j" -> joinGame(tokens);
            case "ready", "r" -> setReady();
            case "startercard","sc" -> selectStarterCardOrientation(tokens);
            case "secretobjective", "so" -> selectSecretObjective(tokens);
            case "place", "p" -> place(tokens);
            case "directdrawresource", "ddr" -> directDrawResource();
            case "drawleftresource", "dlr" -> drawLeftResource();
            case "drawrightresource", "drr" -> drawRightResource();
            case "directdrawgold", "ddg" -> directDrawGold();
            case "drawleftgold", "dlg" -> drawLeftGold();
            case "drawrightgold", "drg" -> drawRightGold();
            default -> {
                System.out.println("Unknown command");
                System.out.println("Type 'help' for more information.");
                System.out.println();
            }
        }
    }
    private void parseError(String messageError) {
        System.out.println("Unexpected arguments: " + messageError);
        System.out.println();
    }
    private void parseError() {
        System.out.println("Unexpected arguments");
        System.out.println();
    }



    private void updateUsername(String[] tokens) {
        if(tokens.length == 2) {
            if (!tokens[1].contains(" ") && tokens[1].matches("^[a-zA-Z0-9_]*$")) {
                ClientController.getInstance().sendSetUsernameMessage(tokens[1]);
            }
            else
                parseError("invalid username");
        }
        else {
            parseError();
        }
    }
    private void help() {
        //TODO stampare help
    }
    private void createGame(String[] tokens) {
        if (tokens.length==3) {
            if(!tokens[1].contains(" ") && Integer.parseInt(tokens[2])>=2 && Integer.parseInt(tokens[2])<=4) {
                ClientController.getInstance().sendSetUpGameMessage(tokens[1], Integer.parseInt(tokens[2]));
            }
            else {
                parseError("invalid parameters");
            }
        }
        else {
            parseError();
        }
    }

    private void getAvailableGames () {
        ClientController.getInstance().sendGetAvailableGamesMessage();
    }

    private void joinGame(String[] tokens) {
        if (tokens.length == 2)
        {
            if (AvailableGamesModel.getInstance().getGames().contains(tokens[1])) {
                ClientController.getInstance().sendJoinGameMessage(tokens[1]);
            }
            else {
                parseError("Game doesn't exist");
            }
        }

        else parseError("Unexpected arguments");
    }

    private void setReady() {
        Printer.printMessage("You are ready! - waiting for all the players to get ready...");
        ClientController.getInstance().sendReadyMessage();
    }

    private void selectStarterCardOrientation(String[] tokens) {
        if (tokens.length == 3) {
            if(Objects.equals(tokens[2], "left")) {
                ClientController.getInstance().sendChosenStarterCardSideMessage(Integer.parseInt(tokens[1]), true);
            }
            else if(Objects.equals(tokens[2], "right")) {
                ClientController.getInstance().sendChosenStarterCardSideMessage(Integer.parseInt(tokens[1]), false);
            }
            else parseError("invalid parameters");
        }
        else {
            parseError("Unexpected arguments");
        }
    }

    private void selectSecretObjective (String[] tokens) {
        if (tokens.length == 2) {
            SelectableCardsModel selectableCardsModel = SelectableCardsModel.getInstance();
            int objectiveId = Integer.parseInt(tokens[1]);
            if(objectiveId == selectableCardsModel.getSelectableObjectiveCardsId()[0] || objectiveId == selectableCardsModel.getSelectableObjectiveCardsId()[1]) {
                ClientController.getInstance().sendChosenSecretObjectiveMessage(Integer.parseInt(tokens[1]));
            }
            else {
                parseError("This ID is not valid");
            }
        }
        else {
            parseError("Unexpected arguments");
        }
    }

    private void place(String[] tokens) {
        if (tokens.length == 5) {
            ClientController.getInstance().sendPlaceMessage(Integer.parseInt(tokens[1]),Integer.parseInt(tokens[2]), Integer.parseInt(tokens[3]), Boolean.parseBoolean(tokens[4]));
        }
        else {
            parseError("Unexpected arguments");
        }
    }


    private void directDrawResource() {
        if(ClientStateModel.getInstance().getClientState() == ClientState.DRAWING_STATE) ClientController.getInstance().sendDirectDrawResourceCardMessage();
    }

    private void drawLeftResource() {
        if(ClientStateModel.getInstance().getClientState() == ClientState.DRAWING_STATE) ClientController.getInstance().sendDrawLeftResourceCardMessage();
    }

    private void drawRightResource() {
        if(ClientStateModel.getInstance().getClientState() == ClientState.DRAWING_STATE)ClientController.getInstance().sendDrawRightResourceCardMessage();
    }

    private void directDrawGold() {
        if(ClientStateModel.getInstance().getClientState() == ClientState.DRAWING_STATE) ClientController.getInstance().sendDirectDrawGoldCardMessage();
    }

    private void drawLeftGold() {
        if(ClientStateModel.getInstance().getClientState() == ClientState.DRAWING_STATE) ClientController.getInstance().sendDrawLeftGoldCardMessage();
    }

    private void drawRightGold() {
        if(ClientStateModel.getInstance().getClientState() == ClientState.DRAWING_STATE) ClientController.getInstance().sendDrawRightGoldCardMessage();
    }

    private void leave() {
        ClientController.getInstance().sendLeaveMessage();
    }

    private void getInfo (String[] tokens) {
        if (tokens.length == 2) {
            try {
                Printer.printCardInfo(Integer.parseInt(tokens[1]),true);
            } catch (InvalidIdException e) {
                Printer.printMessage("Error: unrecognized card ID.");
            }
        }
        else if (tokens.length == 3) {
            if (Objects.equals(tokens[2], "front")){
                try {
                    Printer.printCardInfo(Integer.parseInt(tokens[1]), true);
                } catch (InvalidIdException e) {
                    Printer.printMessage("Error: unrecognized card ID.");
                }
            }
            else if (Objects.equals(tokens[2], "back")){
                try {
                    Printer.printCardInfo(Integer.parseInt(tokens[1]), false);
                } catch (InvalidIdException e) {
                    Printer.printMessage("Error: unrecognized card ID.");
                }
            }

        }
        else System.out.println("Error: Invalid number of arguments");
    }
}
