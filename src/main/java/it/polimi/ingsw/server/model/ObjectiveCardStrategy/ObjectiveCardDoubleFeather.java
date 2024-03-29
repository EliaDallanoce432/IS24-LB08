package it.polimi.ingsw.server.model.ObjectiveCardStrategy;

import it.polimi.ingsw.server.model.GameField;

public class ObjectiveCardDoubleFeather implements ObjectiveStrategy {
    public int calculatePoints(int pointsOnTheCard, GameField gamefield) {
        //manca il collegamento a gamefield
        return pointsOnTheCard * (gamefield.getFeatherCount())/2;
    }
}