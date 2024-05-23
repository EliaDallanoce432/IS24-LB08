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
            case "draw", "d" -> draw(tokens);
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
        if (ClientStateModel.getInstance().getClientState() == ClientState.LOBBY_STATE) {
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
        else {
            System.out.println("Unexpected command");
            System.out.println("you're not in the lobby");
            System.out.println();
        }
    }
    private void help() {
        //TODO stampare help
    }
    private void createGame(String[] tokens) {
        if (ClientStateModel.getInstance().getClientState() == ClientState.LOBBY_STATE) {
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
        else {
            System.out.println("Unexpected command");
            System.out.println("you're not in the lobby");
            System.out.println();
        }
    }

    private void getAvailableGames () {
        if (ClientStateModel.getInstance().getClientState() == ClientState.LOBBY_STATE)
            ClientController.getInstance().sendGetAvailableGamesMessage();
        else {
            System.out.println("Unexpected command");
            System.out.println("you're not in the lobby");
            System.out.println();
        }
    }

    private void joinGame(String[] tokens) {

        if (ClientStateModel.getInstance().getClientState() == ClientState.LOBBY_STATE) {
            if (tokens.length == 2) {
                ClientController.getInstance().sendJoinGameMessage(tokens[1]);
            }
            else parseError();
        }
        else {
            System.out.println("Unexpected command");
            System.out.println("you're not in the lobby");
            System.out.println();
        }
    }

    private void setReady() {
        if (ClientStateModel.getInstance().getClientState() == ClientState.GAME_SETUP_STATE) {
            Printer.printMessage("You are ready! - waiting for all the players to get ready...");
            ClientController.getInstance().sendReadyMessage();
        }
        else {
            System.out.println("Unexpected command");
            System.out.println("you're not in a game");
            System.out.println();
        }
    }

    private void selectStarterCardOrientation(String[] tokens) {
        if (ClientStateModel.getInstance().getClientState() == ClientState.GAME_SETUP_STATE) {
            if (tokens.length == 3) {
                if(Objects.equals(tokens[2], "front")) {
                    ClientController.getInstance().sendChosenStarterCardSideMessage(Integer.parseInt(tokens[1]), true);
                }
                else if(Objects.equals(tokens[2], "back")) {
                    ClientController.getInstance().sendChosenStarterCardSideMessage(Integer.parseInt(tokens[1]), false);
                }
                else parseError("invalid parameters");
            }
            else {
                parseError("Unexpected arguments");
            }
        }
        else {
            System.out.println("Unexpected command");
            System.out.println("you're not in a game");
            System.out.println();
        }
    }

    private void selectSecretObjective (String[] tokens) {
        if (ClientStateModel.getInstance().getClientState() == ClientState.GAME_SETUP_STATE) {
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
        else {
            System.out.println("Unexpected command");
            System.out.println("you're not in a game");
            System.out.println();
        }
    }

    private void place(String[] tokens) {

        if (ClientStateModel.getInstance().getClientState() == ClientState.PLAYING_STATE) {
            if (tokens.length == 5) {
                ClientController.getInstance().sendPlaceMessage(Integer.parseInt(tokens[1]),Integer.parseInt(tokens[2]), Integer.parseInt(tokens[3]), Boolean.parseBoolean(tokens[4]));
            }
            else {
                parseError("Unexpected arguments");
            }
        }
        else {
            System.out.println("Unexpected command");

            if (ClientStateModel.getInstance().getClientState() == ClientState.NOT_PLAYING_STATE) {
                System.out.println("Not your turn");
            } else if (ClientStateModel.getInstance().getClientState() == ClientState.DRAWING_STATE) {
                System.out.println("You have already placed, draw a card");
            }
            else System.out.println("You're not playing right now");
            System.out.println();
        }
    }


    private void draw(String[] tokens) {

        if (tokens.length == 2 && ClientStateModel.getInstance().getClientState() == ClientState.DRAWING_STATE) {
            switch (tokens[1]) {
                case "1" -> ClientController.getInstance().sendDirectDrawResourceCardMessage();
                case "2" -> ClientController.getInstance().sendDrawLeftResourceCardMessage();
                case "3" -> ClientController.getInstance().sendDrawRightResourceCardMessage();
                case "4" -> ClientController.getInstance().sendDirectDrawGoldCardMessage();
                case "5" -> ClientController.getInstance().sendDrawLeftGoldCardMessage();
                case "6" -> ClientController.getInstance().sendDrawRightGoldCardMessage();
                default -> parseError("the argument must be between 1 and 6");
            }
        }
        else {
            System.out.println("Unexpected command");

            if (ClientStateModel.getInstance().getClientState() == ClientState.NOT_PLAYING_STATE) {
                System.out.println("Not your turn");
            } else if (ClientStateModel.getInstance().getClientState() == ClientState.PLAYING_STATE) {
                System.out.println("You have to place a card first");
            }
            else System.out.println("You're not playing right now");
            System.out.println();
        }
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
