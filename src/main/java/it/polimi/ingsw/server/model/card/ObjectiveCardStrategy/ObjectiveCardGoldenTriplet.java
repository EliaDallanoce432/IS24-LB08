package it.polimi.ingsw.server.model.card.ObjectiveCardStrategy;

import it.polimi.ingsw.server.model.GameField;

/**
 * This class is used to implement the golden triplet strategy.
 */
public class ObjectiveCardGoldenTriplet implements ObjectiveStrategy {
    /**
     * Used to calculate the amount of points given for each special triplet (Feather, InkPot, Scroll).
     * found on the game-field.
     * @param pointsOnTheCard Points given by the objective card.
     * @param gamefield Reference to the game-field that is being analysed.
     * @return The amount of calculated points on the specific game-field.
     */
    public int calculatePoints(int pointsOnTheCard, GameField gamefield) {
        int goldTriplets = Math.min(Math.min(gamefield.getFeatherCount(), gamefield.getInkPotCount()), gamefield.getScrollCount());
        for (int i=0; i < goldTriplets; i++) {
            gamefield.getPlayer().increaseNumOfCompletedObjective();
        }
        return pointsOnTheCard * goldTriplets;
    }
}
