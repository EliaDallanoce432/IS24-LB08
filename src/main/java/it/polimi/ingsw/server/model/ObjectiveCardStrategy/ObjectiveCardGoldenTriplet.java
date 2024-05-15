package it.polimi.ingsw.server.model.ObjectiveCardStrategy;

import it.polimi.ingsw.server.model.GameField;

public class ObjectiveCardGoldenTriplet implements ObjectiveStrategy {
    /**
     * Used to calculate the amount of points given for each special triplet (Feather, InkPot, Scroll)
     * found on the game-field
     * @param pointsOnTheCard points multiplier written on the card
     * @param gamefield game-field on which the objective has to be checked
     * @return the amount of calculated points
     */
    public int calculatePoints(int pointsOnTheCard, GameField gamefield) {
        int goldTriplets = Math.min(Math.min(gamefield.getFeatherCount(), gamefield.getInkPotCount()), gamefield.getScrollCount());
        for (int i=0; i < goldTriplets; i++) {
            gamefield.getPlayer().increaseNumOfCompletedObjective();
        }
        return pointsOnTheCard * goldTriplets;
    }
}
