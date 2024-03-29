package it.polimi.ingsw.server.model.ObjectiveCardStrategy;

import it.polimi.ingsw.server.model.GameField;

public class ObjectiveCardDoubleInkpot implements ObjectiveStrategy{
    public int calculatePoints(int pointsOnTheCard, GameField gamefield) {
        //manca il collegamento a gamefield
        return pointsOnTheCard * gamefield.getInkPotCount()/2;
    }
}
