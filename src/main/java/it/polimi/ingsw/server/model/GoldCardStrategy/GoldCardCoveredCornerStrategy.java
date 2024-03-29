package it.polimi.ingsw.server.model.GoldCardStrategy;

import it.polimi.ingsw.server.model.GameField;
import it.polimi.ingsw.server.model.card.GoldCard;

public class GoldCardCoveredCornerStrategy implements ConditionStrategy {
    public int calculatePoints(int pointsOnTheCard, GameField gamefield, GoldCard goldcard) {
        int covered_corner = 0;
        // check sugli angoli coperti
        if(goldcard.getBottomLeftCorner().isAttached()) covered_corner++;
        if(goldcard.getBottomRightCorner().isAttached()) covered_corner++;
        if(goldcard.getTopLeftCorner().isAttached()) covered_corner++;
        if(goldcard.getTopRightCorner().isAttached()) covered_corner++;
        return pointsOnTheCard * covered_corner;
    }
}
