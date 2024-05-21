package it.polimi.ingsw.client.view.CLI;

import it.polimi.ingsw.client.model.ClientStateModel;
import it.polimi.ingsw.client.model.PlayerModel;
import it.polimi.ingsw.client.view.GUI.observers.*;
import it.polimi.ingsw.client.view.GUI.viewControllers.ViewController;
import it.polimi.ingsw.util.supportclasses.ClientState;

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
        super.showMessage(message);
    }

    @Override
    public void showErrorMessage(String message) {
        super.showErrorMessage(message);
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
        }
    }

    @Override
    public void updateSelectableCards() {
        super.updateSelectableCards();
    }

    @Override
    public void updateObjectives() {
        super.updateObjectives();
    }

    @Override
    public void updateSceneStatus() {

    }
}
