package it.polimi.ingsw.server.controller;

public interface GameObserver {

    /**
     * this method is used by players to notify they are ready to play
     */
    void notifyReady();

    /**
     * this method is used by players to notify the controller they have completed the initial cards selection
     */
    void notifyStarterCardAndSecretObjectiveSelected();

    /**
     * this method is used to notify the controller the number of connected players changed
     */
    void notifyConnectedClientCountChanged();

    /**
     * this method is used to notify the controller the beginning of the last round of the match
     */
    void notifyLastRound();

    /**
     * this method is used to notify the controller the end of the current game
     */
    void notifyEndGame();

}
