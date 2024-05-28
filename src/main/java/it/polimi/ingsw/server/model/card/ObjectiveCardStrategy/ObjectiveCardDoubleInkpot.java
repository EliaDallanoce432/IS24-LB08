package it.polimi.ingsw.server.model.card.ObjectiveCardStrategy;

import it.polimi.ingsw.server.model.GameField;

/**
 * This class is used to implement the double InkPot strategy.
 */
public class ObjectiveCardDoubleInkpot implements ObjectiveStrategy {
    /**
     * Used to calculate the amount of points given for each couple of InkPot found on the game-field.
     * @param pointsOnTheCard Points given by the objective card.
     * @param gamefield Reference to the game-field that is being analysed.
     * @return The amount of calculated points on the specific game-field.
     */
    public int calculatePoints(int pointsOnTheCard, GameField gamefield) {
        for (int i=0; i < gamefield.getInkPotCount()/2; i++) {
            gamefield.getPlayer().increaseNumOfCompletedObjective();
        }
        return pointsOnTheCard * (gamefield.getInkPotCount()/2);
    }
}
