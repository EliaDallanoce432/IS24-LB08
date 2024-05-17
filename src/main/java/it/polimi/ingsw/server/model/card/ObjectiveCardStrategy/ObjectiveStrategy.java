package it.polimi.ingsw.server.model.card.ObjectiveCardStrategy;

import it.polimi.ingsw.server.model.GameField;

/**
 * This interface contains the method that is going to be implemented for each single strategy
 * owned by every single objective card
 */
public interface ObjectiveStrategy {
    /**
     * method performs the calculation of the points received by fulfilling the objective
     * @param pointsOnTheCard points multiplier written on the card
     * @param gamefield game-field on which the objective has to be checked
     * @return the amount of calculated points
     */
    int calculatePoints(int pointsOnTheCard, GameField gamefield);
}
