package it.polimi.ingsw.server.model.card.ObjectiveCardStrategy;

import it.polimi.ingsw.server.model.GameField;

/**
 * This interface contains the method that is going to be implemented for each single strategy
 * owned by every single objective card.
 */
public interface ObjectiveStrategy {
    /**
     * Performs the calculation of the points received by fulfilling the objective.
     * @param pointsOnTheCard Points given by the objective card.
     * @param gamefield Reference to the game-field that is being analysed.
     * @return The amount of calculated points on the specific game-field.
     */
    int calculatePoints(int pointsOnTheCard, GameField gamefield);
}
