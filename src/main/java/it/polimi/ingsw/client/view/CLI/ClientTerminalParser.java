package it.polimi.ingsw.client.view.CLI;

import it.polimi.ingsw.client.controller.ClientController;
import it.polimi.ingsw.client.model.ClientStateModel;
import it.polimi.ingsw.client.model.GameFieldModel;
import it.polimi.ingsw.client.model.HandModel;
import it.polimi.ingsw.client.model.SelectableCardsModel;
import it.polimi.ingsw.client.view.GUI.viewControllers.utility.CardRepresentation;
import it.polimi.ingsw.util.cli.CommandParser;
import it.polimi.ingsw.util.customexceptions.InvalidIdException;
import it.polimi.ingsw.util.supportclasses.ClientState;

import java.util.ArrayList;
import java.util.Objects;

/**
 * This class is responsible for parsing user commands entered the Codex game client's CLI.
 */
public class ClientTerminalParser implements CommandParser {

    /**
     * Parses a user command entered the CLI.
     * @param command The user-entered command string.
     */
    @Override
    public void parse(String command) {
        String[] tokens = command.split("\\s+");
        tokens[0] = tokens[0].toLowerCase();
        switch (tokens[0]) {
            case "help","h","?" -> Printer.printHelp();
            case "setusername","su" -> updateUsername(tokens);
            case "leave", "l" -> leave();
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
            case "board" -> showBoard();
            case "hand" -> showHand();
            case "score" -> showScore();
            case "objectives","obj" -> showObjectives();
            case "guide" -> showGuide();
            case "decks" -> showDecks();
            default -> {
                System.out.println("Unknown command");
                System.out.println("Type 'help' for more information.");
                System.out.println();
            }
        }
    }

    /**
     * Sends an error message to the client when the received command is invalid.
     * @param messageError message containing the reason of the failure.
     */
    private void parseError(String messageError) {
        System.out.println("Unexpected arguments: " + messageError);
        System.out.println();
    }

    /**
     * Sends an error message to the client when the received command is invalid.
     */
    private void parseError() {
        System.out.println("Unexpected arguments");
        System.out.println();
    }

    /**
     * Parses the setUsername command required by the client.
     * @param tokens array of strings containing the parameters needed to execute the command.
     */
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

    /**
     * Parses the createGame command required by the client.
     * @param tokens array of strings containing the parameters needed to execute the command.
     */
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

    /**
     * Parses the getAvailableGames required by the client and shows him
     * all the possible games he can join.
     */
    private void getAvailableGames () {
        if (ClientStateModel.getInstance().getClientState() == ClientState.LOBBY_STATE)
            ClientController.getInstance().sendGetAvailableGamesMessage();
        else {
            System.out.println("Unexpected command");
            System.out.println("you're not in the lobby");
            System.out.println();
        }
    }

    /**
     * Parses the joinGame command required by the client.
     * @param tokens array of strings containing the parameters needed to execute the command.
     */
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

    /**
     * Parses the setReady command required by the client.
     */
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

    /**
     * Parses the selectStarterCardOrientation command required by the client
     * @param tokens array of strings containing the parameters needed to execute the command.
     */
    private void selectStarterCardOrientation(String[] tokens) {
        if (ClientStateModel.getInstance().getClientState() == ClientState.GAME_SETUP_STATE) {
            int starterCardID = SelectableCardsModel.getInstance().getStarterCardId();
            if (tokens.length == 2) {
                if(Objects.equals(tokens[1], "front")) {
                    ClientController.getInstance().sendChosenStarterCardSideMessage(starterCardID, true);
                }
                else if(Objects.equals(tokens[1], "back")) {
                    ClientController.getInstance().sendChosenStarterCardSideMessage(starterCardID, false);
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

    /**
     * Parses the selectSecreteObjective command required by the client
     * @param tokens array of strings containing the parameters needed to execute the command.
     */
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

    /**
     * Handles the "place" command for placing cards in the game.
     * @param tokens The tokens parsed from the user command.
     */
    private void place(String[] tokens) {

        if (ClientStateModel.getInstance().getClientState() == ClientState.PLACING_STATE) {
            if (tokens.length == 5) {
                int cardId = 0;
                int targetId = 0;
                try {
                    cardId = Integer.parseInt(tokens[1]);
                    targetId = Integer.parseInt(tokens[3]);
                } catch (NumberFormatException e) {
                    parseError();
                }
                try {
                    searchCardId(HandModel.getInstance().getCardsInHand(), cardId);
                } catch (RuntimeException e) {
                    parseError("You don't have the #" + cardId + " card in your hand");
                    return;
                }
                boolean facingUp = false;
                if(tokens[2].equals("front")) facingUp = true;
                else if(!tokens[2].equals("back")) {
                    parseError();
                    return;
                }
                CardRepresentation targetCard;
                try {
                    targetCard = searchCardId(GameFieldModel.getInstance().getPlacementHistory(), targetId);
                } catch (RuntimeException e) {
                    parseError("the target card is not on your field");
                    return;
                }
                int x = 0;
                int y = 0;
                switch (tokens[4]) {
                    case "topleft", "tl" -> {
                        x = targetCard.getX() - 1;
                        y = targetCard.getY() + 1;
                    }
                    case "topright", "tr" -> {
                        x = targetCard.getX() + 1;
                        y = targetCard.getY() + 1;
                    }
                    case "bottomleft", "bl" -> {
                        x = targetCard.getX() - 1;
                        y = targetCard.getY() - 1;
                    }
                    case "bottomright", "br" -> {
                        x = targetCard.getX() + 1;
                        y = targetCard.getY() -1;
                    }
                    default -> {
                        parseError("the position argument is not correct");
                        return;
                    }
                }

                ClientController.getInstance().sendPlaceMessage(cardId,x,y,facingUp);
            }
            else {
                parseError();

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

    /**
     * Searches for a card with a specific ID within a list of card representations.
     * @param cards The list of card representations to search.
     * @param searchedId The ID of the card to search for.
     * @return The `CardRepresentation` object if the card is found, otherwise throws a `RuntimeException`.
     * @throws RuntimeException If no card with the specified ID is found in the list.
     */
    private CardRepresentation searchCardId(ArrayList<CardRepresentation> cards, int searchedId) throws RuntimeException {
        for(CardRepresentation card : cards) {
            if(card.getId() == searchedId) {
                return card;
            }
        }
        throw new RuntimeException();
    }

    /**
     * Parses the draw command received by the client.
     * @param tokens array of strings containing the parameters needed to execute the command.
     */
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
            } else if (ClientStateModel.getInstance().getClientState() == ClientState.PLACING_STATE) {
                System.out.println("You have to place a card first");
            }
            else System.out.println("You're not playing right now");
            System.out.println();
        }
    }

    /**
     * Parses the leave command required by the client.
     */
    private void leave() {
        ClientController.getInstance().sendLeaveMessage();
    }

    /**
     * Parses the getInfo command required by the client.
     * @param tokens array of strings containing the parameters needed to execute the command.
     */
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

    /**
     * Parses the showBoard command required by the client.
     */
    private void showBoard() {
        if (!isInGame()) {
            System.out.println("Unexpected command");
            System.out.println("you're not in a game");
            System.out.println();
        }
        else Printer.printGameBoard();
    }

    /**
     * Parses the showHand command required by the client.
     */
    private void showHand () {
        if (!isInGame()) {
            System.out.println("Unexpected command");
            System.out.println("you're not in a game");
            System.out.println();
        }
        else Printer.printHand();
    }

    /**
     * Parses the showScore command required by the client.
     */
    private void showScore () {
        if (!isInGame()) {
            System.out.println("Unexpected command");
            System.out.println("you're not in a game");
            System.out.println();
        }
        else Printer.printScores();
    }

    /**
     * Parses the showDecks command required by the client.
     */
    private void showDecks() {
        if (!isInGame()) {
            System.out.println("Unexpected command");
            System.out.println("you're not in a game");
            System.out.println();
        }
        else Printer.printDeckInfo();
    }

    /**
     * Parses the showObjectives command required by the client.
     */
    private void showObjectives() {
        if (!isInGame()) {
            System.out.println("Unexpected command");
            System.out.println("you're not in a game");
            System.out.println();
        }
        else {
            Printer.printObjectives();
        }
    }

    /**
     * Parses the showGuide command required by the client.
     */
    private void showGuide() {
        Printer.printGuide();
    }

    private boolean isInGame(){
        ClientState clientState = ClientStateModel.getInstance().getClientState();
        return (clientState == ClientState.PLACING_STATE || clientState == ClientState.DRAWING_STATE || clientState == ClientState.NOT_PLAYING_STATE || clientState == ClientState.LAST_ROUND_STATE);
    }
}
