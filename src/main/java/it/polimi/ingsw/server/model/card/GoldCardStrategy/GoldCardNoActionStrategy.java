package it.polimi.ingsw.server.model.card.GoldCardStrategy;

import it.polimi.ingsw.server.model.GameField;
import it.polimi.ingsw.server.model.card.GoldCard;

/**
 * This class is used to apply the no action strategy on a gold-card.
 */
public class GoldCardNoActionStrategy implements ConditionStrategy{

    /**
     * Used to calculate the amount of points given by the gold-card when it gets placed.
     * @param pointsOnTheCard Points given by the gold-card.
     * @param gamefield Reference to the player's game-field.
     * @param goldcard Used for the calculation.
     * @return The amount of calculated points.
     */
    public int calculatePoints(int pointsOnTheCard, GameField gamefield, GoldCard goldcard) {
        return pointsOnTheCard;
    }
}
