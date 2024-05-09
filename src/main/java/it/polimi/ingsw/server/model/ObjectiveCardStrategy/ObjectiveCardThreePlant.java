package it.polimi.ingsw.server.model.ObjectiveCardStrategy;

import it.polimi.ingsw.server.model.GameField;

public class ObjectiveCardThreePlant implements ObjectiveStrategy {
    public int calculatePoints(int pointsOnTheCard, GameField gamefield) {
        for (int i=0; i < gamefield.getPlantCount()/3; i++) {
            gamefield.getPlayer().increaseNumOfCompletedObjective();
        }
        return pointsOnTheCard * (gamefield.getPlantCount()/3);
    }
}
