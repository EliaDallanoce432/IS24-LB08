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
        if(ClientStateModel.getInstance().getClientState() == ClientState.DRAWING_STATE) {
            Printer.printDeckInfo();
            Printer.printMessage("to draw a card type 'draw [selection]', with selection between 1 and 6\n" +
                    "type 'info [cardId]' to see more information about the cards");
        }

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


        Printer.printMessage("Your starter card ID is #" + starterCardID + " and you can choose your secret objective between these two #"+ objectivesID[0] + " #" +objectivesID[1] + "\n" +
                "Please select the orientation of the starter card and your secret objective before beginning. \n " +
                "To select the starter card orientation, type 'sc front' or 'sc back'.\n " +
                "To select a secret objective, type 'so " + objectivesID[0] + "' or 'so " + objectivesID[1] + "'.");
    }


    @Override
    public void updateSceneStatus() {
        /*StageManager stage =
        if(){
            Printer.printMessage( + "created successfully!");
            System.out.println();
        }

         */
    }
}
