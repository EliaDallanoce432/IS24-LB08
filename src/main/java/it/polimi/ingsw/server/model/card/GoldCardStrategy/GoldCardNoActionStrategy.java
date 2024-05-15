package it.polimi.ingsw.server.model.card.GoldCardStrategy;

import it.polimi.ingsw.server.model.GameField;
import it.polimi.ingsw.server.model.card.GoldCard;

public class GoldCardNoActionStrategy implements ConditionStrategy{

    /**
     * Used to calculate the amount of points given by the gold-card when it gets placed
     * @param pointsOnTheCard points given by the gold-card
     * @param gamefield game-field that is being analysed
     * @param goldcard  the gold-card used for the calculation
     * @return the amount of calculated points
     */
    public int calculatePoints(int pointsOnTheCard, GameField gamefield, GoldCard goldcard) {
        return pointsOnTheCard;
    }
}
