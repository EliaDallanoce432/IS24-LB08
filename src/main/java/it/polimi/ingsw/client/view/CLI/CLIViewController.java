package it.polimi.ingsw.client.view.CLI;

import it.polimi.ingsw.client.model.ClientStateModel;
import it.polimi.ingsw.client.model.PlayerModel;
import it.polimi.ingsw.client.model.SelectableCardsModel;
import it.polimi.ingsw.client.view.GUI.viewControllers.ViewController;
import it.polimi.ingsw.client.view.observers.*;
import it.polimi.ingsw.util.supportclasses.ClientState;
import it.polimi.ingsw.util.supportclasses.ConsoleColor;

/**
 * This class is responsible for handling UI updates in the Command-Line Interface (CLI) for the Codex game client.
 */
public class CLIViewController extends ViewController {

    private static ClientState previousState = null;

    public CLIViewController() {
        //initializing observers
        new AvailableGamesObserver();
        new ClientStateObserver();
        new DeckObserver();
        new GameFieldObserver();
        new HandObserver();
        new ObjectivesObserver();
        new PlayerObserver();
        new ScoreBoardObserver();
        new SelectableCardsObserver();
    }

    /**
     * Prints a message to the console.
     * @param message The message to display.
     */
    @Override
    public void showMessage(String message) {
        Printer.printMessage(message);
    }

    /**
     * Prints the common objectives and the player's secret objective.
     */
    @Override
    public void updateObjectives(){
        Printer.printObjectives();
    }

    /**
     * Prints an error message to the console in red.
     * @param message The error message to display.
     */
    @Override
    public void showErrorMessage(String message) {
        Printer.printMessage("ERROR: " + message , ConsoleColor.RED);
    }

    /**
     * Prints the available games.
     */
    @Override
    public void updateAvailableGames() {
        Printer.printMessage("Available Games: ", ConsoleColor.YELLOW);
        Printer.printAvailableGames();
    }

    /**
     * Prints the current username if the client is in the LOBBY_STATE.
     */
    @Override
    public void updatePlayerInfo() {
        if(ClientStateModel.getInstance().getClientState() == ClientState.LOBBY_STATE){
            Printer.printMessage("User logged in as: " + PlayerModel.getInstance().getUsername(), ConsoleColor.YELLOW);
            System.out.println();
        }
    }

    /**
     * Prints information about the starter card ID and selectable objective card IDs to the console.
     * Also provides instructions for selecting starter card orientation and secret objective.
     */
    @Override
    public void updateSelectableCards() {
        SelectableCardsModel selectableCardsModel = SelectableCardsModel.getInstance();
        int starterCardID = selectableCardsModel.getStarterCardId();
        int[] objectivesID = selectableCardsModel.getSelectableObjectiveCardsId();
        Printer.printMessage("Your starter card is #" + starterCardID + "\n" +
                "You can choose your secret objective between these two cards: #"+ objectivesID[0] +" and #" +objectivesID[1]+"\n" +
                "To select the starter card orientation, type 'sc front' or 'sc back'.\n" +
                "To select a secret objective, type 'so " + objectivesID[0] + "' or 'so " + objectivesID[1] + "'.", ConsoleColor.YELLOW);
    }

    /**
     * Prints game state-specific messages and UI components to the console based on the current `ClientState`.
     */
    @Override
    public void updateSceneStatus() {
        ClientState clientState = ClientStateModel.getInstance().getClientState();
        switch (clientState) {
            case LOBBY_STATE -> {
                ClientCLI.clearConsole();
                Printer.printCodexLogo();
                if(previousState == ClientState.KICKED_STATE){
                    Printer.printMessage("A player left the game!", ConsoleColor.RED);
                }
                Printer.printMenu();
            }
            case GAME_SETUP_STATE -> {
                Printer.printMessage(ClientStateModel.getInstance().getReason(), ConsoleColor.YELLOW);
                Printer.printMessage("Type 'ready' when you're ready to begin!");

            }
            case PLACING_STATE -> {
                ClientCLI.clearConsole();
                Printer.printMessage("Please place a card!", ConsoleColor.YELLOW);
                Printer.printGameBoard();
                Printer.printResources();
                Printer.printGuide();
                Printer.printHand();
                Printer.printMessage("To place a card, type 'place <cardId> <orientation> <targetCardId> <position>'");
            }
            case DRAWING_STATE -> {
                Printer.printMessage("Your updated Game Board:", ConsoleColor.YELLOW);
                Printer.printGameBoard();
                Printer.printResources();
                Printer.printGuide();
                Printer.printMessage("Now please draw a card from the decks", ConsoleColor.YELLOW);
                Printer.printDeckInfo();
                Printer.printMessage("to draw a card type 'draw [selection]', with selection between 1 and 6\n" +
                        "type 'info [cardId]' to see more information about the cards");
            }
            case NOT_PLAYING_STATE -> {
                Printer.printScores();
                Printer.printMessage("Waiting for " + PlayerModel.getInstance().getTurnPlayer() + " to finish their turn..");
            }
            case LAST_ROUND_STATE -> Printer.printMessage("It's the last turn! " + ClientStateModel.getInstance().getReason(), ConsoleColor.YELLOW_BRIGHT);
            case END_GAME_STATE -> {
                ClientCLI.clearConsole();
                Printer.printMessage("Leaderboard", ConsoleColor.YELLOW);
                Printer.printLeaderboard();
                Printer.printMessage("Type 'leave' to go back to the lobby.");
            }
            case LOST_CONNECTION_STATE -> {
                Printer.printMessage("ERROR: Lost connection to the server, closing the game.", ConsoleColor.RED);
                System.exit(0);
            }
            case KICKED_STATE -> Printer.printMessage("ERROR: one player left the game or has lost connection, closing the game.", ConsoleColor.RED);
        }
        previousState = clientState;
    }
}
