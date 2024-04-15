package it.polimi.ingsw.server.model.ObjectiveCardStrategy;

import it.polimi.ingsw.server.model.GameField;

public class ObjectiveCardThreePlant implements ObjectiveStrategy {
    public int calculatePoints(int pointsOnTheCard, GameField gamefield) {

        return pointsOnTheCard * (gamefield.getPlantCount()/3);
    }
}
