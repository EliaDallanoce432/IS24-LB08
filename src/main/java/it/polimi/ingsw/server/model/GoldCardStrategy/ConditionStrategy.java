package it.polimi.ingsw.server.model.GoldCardStrategy;

import it.polimi.ingsw.server.model.GameField;
import it.polimi.ingsw.server.model.card.GoldCard;

public interface ConditionStrategy {
    /**
     * method performs the calculation of the points received by placing the gold card
     * @param pointsOnTheCard points given by the card
     * @param gamefield game field that is being analysed
     * @param goldcard  the gold card itself (needed in GoldCardCoveredCornerStrategy)
     * @return the points given by the card
     */
    int calculatePoints(int pointsOnTheCard, GameField gamefield, GoldCard goldcard);
}
