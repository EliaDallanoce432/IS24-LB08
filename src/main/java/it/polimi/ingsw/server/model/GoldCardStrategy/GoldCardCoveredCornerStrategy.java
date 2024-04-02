package it.polimi.ingsw.server.model.GoldCardStrategy;

import it.polimi.ingsw.server.model.GameField;
import it.polimi.ingsw.server.model.card.GoldCard;

public class GoldCardCoveredCornerStrategy implements ConditionStrategy {
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
