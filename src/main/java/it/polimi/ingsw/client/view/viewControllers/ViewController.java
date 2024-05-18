package it.polimi.ingsw.client.view.viewControllers;

import it.polimi.ingsw.client.model.ClientStateModel;
import it.polimi.ingsw.client.view.StageManager;
import javafx.application.Platform;

/**
 * This abstract class is the base for all the View Controllers. It contains all the main methods to update the GUI
 * components.
 */

public abstract class ViewController {

    /**
     * Shows a message in the GUI.
     * @param message the message to be shown
     */
    public void showMessage(String message) {
        System.out.println(message);
    }

    /**
     * Shows an error message in the GUI.
     */
    public void showErrorMessage(String message) {}

    /**
     * Updates the available games in the GUI.
     */
    public void updateAvailableGames(){}

    /**
     * Updates the decks in the GUI.
     */
    public void updateDecks(){}

    /**
     * Updates the game board in the GUI.
     */
    public void updateGameBoard(){}

    /**
     * Updates the scoreboard in the GUI.
     */
    public void updateScoreBoard(){}

    /**
     * Updates the hand in the GUI.
     */
    public void updateHand(){}

    /**
     * Updates the player info in the GUI.
     */
    public void updatePlayerInfo(){}

    /**
     * Updates the selectable cards in the GUI.
     */
    public void updateSelectableCards(){}

    /**
     * Updates the objectives in the GUI.
     */
    public void updateObjectives(){}


    /**
     * Updates the scene status in the GUI accordingly to the current ClientStateModel.
     */
    public void updateSceneStatus(){

        Platform.runLater(()->{
            switch (ClientStateModel.getInstance().getClientState()){
                case KICKED_STATE -> StageManager.loadKickedFromGameScene();
                case LOST_CONNECTION_STATE -> StageManager.loadLostConnectionScene();
                default -> {}
            }
        });

    }



}
