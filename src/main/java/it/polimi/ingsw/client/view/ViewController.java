package it.polimi.ingsw.client.view;

/**
 * This abstract class is the base for all the View Controllers. It contains all the main methods to update the UI
 * components.
 */
public abstract class ViewController {
    /**
     * Shows a message in the UI.
     * @param message the message to be shown
     */
    public void showMessage(String message) {
        System.out.println(message);
    }

    /**
     * Shows an error message in the UI.
     */
    public void showErrorMessage(String message) {}

    /**
     * Updates the available games in the UI.
     */
    public void updateAvailableGames(){}

    /**
     * Updates the decks in the UI.
     */
    public void updateDecks(){}

    /**
     * Updates the game board in the UI.
     */
    public void updateGameBoard(){}

    /**
     * Updates the scoreboard in the UI.
     */
    public void updateScoreBoard(){}

    /**
     * Updates the hand in the UI.
     */
    public void updateHand(){}

    /**
     * Updates the player info in the UI.
     */
    public void updatePlayerInfo(){}

    /**
     * Updates the selectable cards in the UI.
     */
    public void updateSelectableCards(){}

    /**
     * Updates the objectives in the UI.
     */
    public void updateObjectives(){}

    /**
     * Updates the scene status in the UI accordingly to the current ClientStateModel.
     */
    public void updateSceneStatus(){}
}