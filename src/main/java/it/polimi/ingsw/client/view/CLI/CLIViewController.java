package it.polimi.ingsw.client.view.CLI;

import it.polimi.ingsw.client.model.ClientStateModel;
import it.polimi.ingsw.client.model.PlayerModel;
import it.polimi.ingsw.client.model.SelectableCardsModel;
import it.polimi.ingsw.client.view.GUI.observers.*;
import it.polimi.ingsw.client.view.GUI.viewControllers.ViewController;
import it.polimi.ingsw.server.model.card.StarterCard;
import it.polimi.ingsw.util.customexceptions.InvalidIdException;
import it.polimi.ingsw.util.supportclasses.ClientState;

import java.util.Scanner;

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
        super.updateDecks();
    }

    @Override
    public void updateGameBoard() {
        super.updateGameBoard();
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

        try {
            Printer.printSelectableCards(starterCardID, true, starterCardID, false);
            Printer.printSelectableCards(objectivesID[0],true, objectivesID[1],true);
        } catch (InvalidIdException e) {
            System.out.println("Internal Server error: " + e.getMessage());
        }
        Printer.printMessage("Please select the orientation of the starter card and your secret objective before beginning.");
        Printer.printMessage("To select the starter card orientation, type 'sc left' or 'sc right'.");
        Printer.printMessage("To select a secret objective, type 'so <" + objectivesID[0] + ">' or 'so <" + objectivesID[1] + ">'.");
    }

    @Override
    public void updateObjectives() {
        super.updateObjectives();
    }

    @Override
    public void updateSceneStatus() {

    }
}
