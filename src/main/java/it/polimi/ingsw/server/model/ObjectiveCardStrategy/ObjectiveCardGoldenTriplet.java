package it.polimi.ingsw.server.model.ObjectiveCardStrategy;

import it.polimi.ingsw.server.model.GameField;

public class ObjectiveCardGoldenTriplet implements ObjectiveStrategy{
    public int calculatePoints(int pointsOnTheCard, GameField gamefield) {
        int goldTriplets = Math.min(Math.min(gamefield.getFeatherCount(), gamefield.getInkPotCount()), gamefield.getScrollCount());
        for (int i=0; i < goldTriplets; i++) {
            gamefield.getPlayer().increaseNumOfCompletedObjective();
        }
        return pointsOnTheCard * goldTriplets;
    }
}
