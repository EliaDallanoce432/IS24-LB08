package it.polimi.ingsw.server.model.card.ObjectiveCardStrategy;

import it.polimi.ingsw.server.model.GameField;

/**
 * This class is used to implement the double scroll strategy.
 */
public class ObjectiveCardDoubleScroll implements ObjectiveStrategy {
    /**
     * Used to calculate the amount of points given for each couple of Scroll found on the game-field.
     * @param pointsOnTheCard Points given by the objective card.
     * @param gamefield Reference to the game-field that is being analysed.
     * @return The amount of calculated points on the specific game-field.
     */
    public int calculatePoints(int pointsOnTheCard, GameField gamefield) {
        for (int i=0; i < gamefield.getScrollCount()/2; i++) {
            gamefield.getPlayer().increaseNumOfCompletedObjective();
        }
        return pointsOnTheCard * (gamefield.getScrollCount()/2);
    }
}
