package it.polimi.ingsw.client.view.CLI;

import it.polimi.ingsw.client.model.ClientStateModel;
import it.polimi.ingsw.client.model.GameFieldModel;
import it.polimi.ingsw.client.model.PlayerModel;
import it.polimi.ingsw.client.model.SelectableCardsModel;
import it.polimi.ingsw.client.view.GUI.observers.*;
import it.polimi.ingsw.client.view.GUI.viewControllers.ViewController;
import it.polimi.ingsw.client.view.StageManager;
import it.polimi.ingsw.server.lobby.Lobby;
import it.polimi.ingsw.server.model.Game;
import it.polimi.ingsw.server.model.card.StarterCard;
import it.polimi.ingsw.util.customexceptions.InvalidIdException;
import it.polimi.ingsw.util.supportclasses.ClientState;

import java.util.Scanner;

import static it.polimi.ingsw.util.supportclasses.ClientState.PLACING_STATE;

public class CLIViewController extends ViewController {

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

    @Override
    public void showMessage(String message) {
        Printer.printMessage(message);
    }

    @Override
    public void showErrorMessage(String message) {
        Printer.printMessage("ERROR: " + message);
    }

    @Override
    public void updateAvailableGames() {
        super.updateAvailableGames();
    }

    @Override
    public void updateDecks() {
    }

    @Override
    public void updateGameBoard() {
    }

    @Override
    public void updateScoreBoard() {
        super.updateScoreBoard();
    }

    @Override
    public void updateHand() {
        super.updateHand();
    }

    @Override
    public void updatePlayerInfo() {
        if(ClientStateModel.getInstance().getClientState() == ClientState.LOBBY_STATE){
            Printer.printMessage("User logged in as: " + PlayerModel.getInstance().getUsername());
            System.out.println();
        }
    }

    @Override
    public void updateSelectableCards() {

        SelectableCardsModel selectableCardsModel = SelectableCardsModel.getInstance();
        int starterCardID = selectableCardsModel.getStarterCardId();
        int[] objectivesID = selectableCardsModel.getSelectableObjectiveCardsId();


        Printer.printMessage("Your starter card ID is #" + starterCardID + " and you can choose your secret objective between these two #"+ objectivesID[0] + " #" +objectivesID[1] + "\n" +
                "Please select the orientation of the starter card and your secret objective before beginning. \n " +
                "To select the starter card orientation, type 'sc front' or 'sc back'.\n " +
                "To select a secret objective, type 'so " + objectivesID[0] + "' or 'so " + objectivesID[1] + "'.");
    }


    @Override
    public void updateSceneStatus() {
        ClientState clientState = ClientStateModel.getInstance().getClientState();

        switch (clientState) {
            case PLACING_STATE -> {
                Printer.printMessage("Please place a card!");
                Printer.printGameBoard();
                //TODO print hand
            }
            case DRAWING_STATE -> {
                Printer.printMessage("Your updated Game Board:");
                Printer.printGameBoard();
                Printer.printDeckInfo();
                Printer.printMessage("to draw a card type 'draw [selection]', with selection between 1 and 6\n" +
                        "type 'info [cardId]' to see more information about the cards");
            }
            case NOT_PLAYING_STATE -> {
                Printer.printMessage("Waiting for" + PlayerModel.getInstance().getTurnPlayer() + " to finish their turn..");
            }
            case LOST_CONNECTION_STATE -> {
                Printer.printMessage("ERROR: Lost connection to the server, closing the game.");
                System.exit(0);
            }
            case KICKED_STATE -> {
                Printer.printMessage("ERROR: one player left the game or has lost connection, closing the game.");
            }
        }
    }
}
