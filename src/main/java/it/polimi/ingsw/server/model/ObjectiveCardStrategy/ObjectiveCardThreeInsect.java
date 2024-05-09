package it.polimi.ingsw.server.model.ObjectiveCardStrategy;

import it.polimi.ingsw.server.model.GameField;

public class ObjectiveCardThreeInsect implements ObjectiveStrategy {
    public int calculatePoints(int pointsOnTheCard, GameField gamefield) {
        for (int i=0; i < gamefield.getInsectCount()/3; i++) {
            gamefield.getPlayer().increaseNumOfCompletedObjective();
        }
        return pointsOnTheCard * (gamefield.getInsectCount()/3);
    }
}
