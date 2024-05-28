package it.polimi.ingsw.server.model.card.GoldCardStrategy;

import it.polimi.ingsw.server.model.GameField;
import it.polimi.ingsw.server.model.card.GoldCard;

/**
 * This class is used to apply the feather strategy on a gold-card.
 */
public class GoldCardFeatherStrategy implements ConditionStrategy {

    /**
     * Used to calculate the amount of points given by the gold-card for each visible feather on the game-field.
     * @param pointsOnTheCard Points given by the gold-card.
     * @param gamefield Reference to the player's game-field.
     * @param goldcard Used for the calculation.
     * @return The amount of calculated points.
     */
    public int calculatePoints(int pointsOnTheCard, GameField gamefield, GoldCard goldcard) {
            return pointsOnTheCard * gamefield.getFeatherCount();
    }
}
