package it.polimi.ingsw.server.controller;

/**
 * This interface defines methods for notifying a game controller about various game events.
 */
public interface GameObserver {

    /**
     * notifies to controller that all players are ready to play
     */
    void notifyReady();

    /**
     * notifies to controller that all  players have completed the initial cards selection
     */
    void notifyStarterCardAndSecretObjectiveSelected();

    /**
     * notifies to controller the number of connected players changed
     */
    void notifyConnectedClientCountChanged();

    /**
     * notifies to controller the beginning of the last round of the match
     */
    void notifyLastRound();

    /**
     * notifies to controller the end of the current game
     */
    void notifyEndGame();

}
