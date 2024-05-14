package it.polimi.ingsw.server.model.GoldCardStrategy;

import it.polimi.ingsw.server.model.GameField;
import it.polimi.ingsw.server.model.card.GoldCard;


public class GoldCardCoveredCornerStrategy implements ConditionStrategy {

    /**
     * Used to calculate the amount of points given by the gold-card for every corner it covers
     * when it gets placed
     * @param pointsOnTheCard points given by the gold-card
     * @param gamefield game-field that is being analysed
     * @param goldcard  the gold-card used for the calculation
     * @return the amount of calculated points
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
