package it.polimi.ingsw.server.model.card.GoldCardStrategy;

import it.polimi.ingsw.server.model.GameField;
import it.polimi.ingsw.server.model.card.GoldCard;

/**
 * This class is used to apply the covered corner strategy on a gold-card.
 */
public class GoldCardCoveredCornerStrategy implements ConditionStrategy {

    /**
     * Used to calculate the amount of points given by the gold-card for every corner it covers
     * when it gets placed.
     * @param pointsOnTheCard Points given by the gold-card.
     * @param gamefield Reference to the player's game-field.
     * @param goldcard Used for the calculation.
     * @return The amount of calculated points.
     */
    public int calculatePoints(int pointsOnTheCard, GameField gamefield, GoldCard goldcard) {
        int coveredCorners = 0;
        // checks covered corners
        if(goldcard.getBottomLeftCorner().isAttached() && goldcard.getBottomLeftCorner().isVisible()) coveredCorners++;
        if(goldcard.getBottomRightCorner().isAttached() && goldcard.getBottomRightCorner().isVisible()) coveredCorners++;
        if(goldcard.getTopLeftCorner().isAttached() && goldcard.getTopLeftCorner().isVisible()) coveredCorners++;
        if(goldcard.getTopRightCorner().isAttached() && goldcard.getTopRightCorner().isVisible()) coveredCorners++;
        return pointsOnTheCard * coveredCorners;
    }
}
