package it.polimi.ingsw.server.model.ObjectiveCardStrategy;

import it.polimi.ingsw.server.model.GameField;

public class ObjectiveCardDoubleFeather implements ObjectiveStrategy {
    public int calculatePoints(int pointsOnTheCard, GameField gamefield) {
        for (int i=0; i < gamefield.getFeatherCount()/2; i++) {
            gamefield.getPlayer().increaseNumOfCompletedObjective();
        }
        return pointsOnTheCard * (gamefield.getFeatherCount()/2);
    }
}
