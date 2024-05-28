package it.polimi.ingsw.server.model.card.GoldCardStrategy;

import it.polimi.ingsw.server.model.GameField;
import it.polimi.ingsw.server.model.card.GoldCard;

/**
 * This interface contains the method that is going to be implemented for each single strategy.
 * owned by a gold-card.
 */
public interface ConditionStrategy {
    /**
     * Performs the calculation of the points received by placing the gold-card.
     * @param pointsOnTheCard Points given by the card.
     * @param gamefield Reference to the game-field that is being analysed.
     * @param goldcard  The gold-card used for the calculation.
     * @return The amount of calculated points.
     */
    int calculatePoints(int pointsOnTheCard, GameField gamefield, GoldCard goldcard);
}
