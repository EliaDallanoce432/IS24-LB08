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
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ClientTerminalParser implements CommandParser {


    @Override
    public void parse(String command) {
        String[] tokens = command.split("\\s+");
        tokens[0] = tokens[0].toLowerCase();
        switch (tokens[0]) {
            case "help","h","?" -> help();
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
     * Parses the help command required by the client and shows him
     * all the possible commands he can send.
     */
    private void help() {
        Map<String, String> commands  = new HashMap<>();
        switch (ClientStateModel.getInstance().getClientState())
        {
            case ClientState.LOBBY_STATE -> {
                commands.put("setusername | su <username>", "Set your username");
                commands.put("join | j <gameName>", "Join to a game");
                commands.put("create | c <gameName> <players(2-4)>", "Create a game for 2 to 4 players");
                commands.put("quit | q", "Exit from Codex");
            }
            case ClientState.GAME_SETUP_STATE -> {
                commands.put("ready | r", "Set you are ready to play");
                commands.put("availablegames | ag", "View all available games");
                commands.put("startercard | sc <cardId> <front/back>", "Choose a starter card and its side");
                commands.put("secretobjective | so <cardId>", "Choose a secret objective");
                commands.put("leave | l", "Leave the game");
                commands.put("quit | q", "Exit from Codex");
            }
            case ClientState.DRAWING_STATE -> {
                commands.put("info | i <cardId>", "View information of a card");
                commands.put("place | p <cardId> <front/back> <targetId> <position>", "Place a card in a specific position of game field. The position argument can be topleft|tl or toright|tr or bottomleft|bl or bottomright|br");
                commands.put("leave | l", "Leave the game");
                commands.put("quit | q", "Exit from Codex");
            }
            case ClientState.PLACING_STATE -> {
                commands.put("info | i <cardId>", "View information of a card");
                commands.put("draw | d <1-6>", "Draw a game into a player");
                commands.put("leave | l", "Leave the game");
                commands.put("quit | q", "Exit from Codex");
            }
            case ClientState.NOT_PLAYING_STATE, ClientState.LAST_TURN_STATE -> {
                commands.put("info | i <cardId>", "View information of a card");
                commands.put("place | p <cardId> <x> <y> <facingUp>", "Place a card in a specific position of game field");
                commands.put("draw | d <1-6>", "Draw a game into a player");
                commands.put("leave | l", "Leave the game");
                commands.put("quit | q", "Exit from Codex");
            }
            case ClientState.END_GAME_STATE -> {
                commands.put("leave | l", "Leave the game");
                commands.put("quit | q", "Exit from Codex");
            }
            default -> {
                commands.put("setusername | su <username>", "Set your username");
                commands.put("join | j <gameName>", "Join to a game");
                commands.put("create | c <gameName> <players(2-4)>", "Create a game for 2 to 4 players");
                commands.put("info | i <cardId>", "View information of a card");
                commands.put("quit | q", "Exit from Codex");
            }
        }
        for (Map.Entry<String, String> entry : commands.entrySet()) {
            System.out.printf("%-50s %-20s", entry.getKey(), entry.getValue());
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
     * Parses the place command required by the client.
     * @param tokens array of strings containing the parameters needed to execute the command.
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
        if (ClientStateModel.getInstance().getClientState() == ClientState.LOBBY_STATE || ClientStateModel.getInstance().getClientState() == ClientState.GAME_SETUP_STATE) {
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
        if (ClientStateModel.getInstance().getClientState() == ClientState.LOBBY_STATE || ClientStateModel.getInstance().getClientState() == ClientState.GAME_SETUP_STATE) {
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
        if (ClientStateModel.getInstance().getClientState() == ClientState.LOBBY_STATE || ClientStateModel.getInstance().getClientState() == ClientState.GAME_SETUP_STATE) {
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
        if (ClientStateModel.getInstance().getClientState() == ClientState.LOBBY_STATE || ClientStateModel.getInstance().getClientState() == ClientState.GAME_SETUP_STATE) {
            System.out.println("Unexpected command");
            System.out.println("you're not in a game");
            System.out.println();
        }
        else Printer.printDeckInfo();
    }

    /**
     * Parses the showGuide command required by the client.
     */
    private void showGuide() {
        Printer.printGuide();
    }
}
