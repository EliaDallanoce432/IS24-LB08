package it.polimi.ingsw.server.model.ObjectiveCardStrategy;

import it.polimi.ingsw.server.model.GameField;

public class ObjectiveCardThreeFungi implements ObjectiveStrategy{
    public int calculatePoints(int pointsOnTheCard, GameField gamefield) {
        for (int i=0; i < gamefield.getFungiCount()/3; i++) {
            gamefield.getPlayer().increaseNumOfCompletedObjective();
        }
        return pointsOnTheCard * (gamefield.getFungiCount()/3);
    }
}
