package it.polimi.ingsw.server.controller;

/**
 * This interface defines methods for notifying a game controller about various game events.
 */
public interface GameObserver {

    /**
     * Notifies to controller that all players are ready to play.
     */
    void notifyReady();

    /**
     * Notifies to controller that all  players have completed the initial card selection.
     */
    void notifyStarterCardAndSecretObjectiveSelected();

    /**
     * Notifies to controller the number of connected players changed.
     */
    void notifyConnectedClientCountChanged();

    /**
     * Notifies to controller the beginning of the last round of the match.
     */
    void notifyLastRound();

    /**
     * Notifies to controller the end of the current game.
     */
    void notifyEndGame();

}
