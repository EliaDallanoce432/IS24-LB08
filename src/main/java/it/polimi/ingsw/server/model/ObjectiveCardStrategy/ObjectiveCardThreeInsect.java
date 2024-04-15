package it.polimi.ingsw.server.model.ObjectiveCardStrategy;

import it.polimi.ingsw.server.model.GameField;

public class ObjectiveCardThreeInsect implements ObjectiveStrategy {
    public int calculatePoints(int pointsOnTheCard, GameField gamefield) {

        return pointsOnTheCard * (gamefield.getInsectCount()/3);
    }
}
