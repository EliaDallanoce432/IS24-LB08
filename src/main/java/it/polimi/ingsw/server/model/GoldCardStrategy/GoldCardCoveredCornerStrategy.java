package it.polimi.ingsw.server.model.GoldCardStrategy;

import it.polimi.ingsw.server.model.GameField;

public class GoldCardCoveredCornerStrategy implements ConditionStrategy {
    public int calculatePoints(int pointsOnTheCard, GameField gamefield) {
        int covered_corner = 0;
        // check sugli angoli coperti

        return pointsOnTheCard * covered_corner;
    }
}
