package it.polimi.ingsw.server.model.ObjectiveCardStrategy;

import it.polimi.ingsw.server.model.GameField;

public class ObjectiveCardDoubleScroll implements ObjectiveStrategy {
    public int calculatePoints(int pointsOnTheCard, GameField gamefield) {
        //manca il collegamento a gamefield
        return pointsOnTheCard * gamefield.getScrollCount()/2;
    }
}
