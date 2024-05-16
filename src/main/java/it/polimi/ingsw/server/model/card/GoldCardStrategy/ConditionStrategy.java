package it.polimi.ingsw.server.model.card.GoldCardStrategy;

import it.polimi.ingsw.server.model.GameField;
import it.polimi.ingsw.server.model.card.GoldCard;

/**
 * This interface contains the method that is going to be implemented for each single strategy
 * owned by a gold-card
 */
public interface ConditionStrategy {
    /**
     * method performs the calculation of the points received by placing the gold-card
     * @param pointsOnTheCard points given by the card
     * @param gamefield game-field that is being analysed
     * @param goldcard  the gold-card used for the calculation
     * @return the amount of calculated points
     */
    int calculatePoints(int pointsOnTheCard, GameField gamefield, GoldCard goldcard);
}
