package it.polimi.ingsw.server.model.card.ObjectiveCardStrategy;

import it.polimi.ingsw.server.model.GameField;

/**
 * This class is used to implement the double feather strategy.
 */
public class ObjectiveCardDoubleFeather implements ObjectiveStrategy {
    /**
     * Used to calculate the amount of points given for each couple of feathers found on the game-field.
     * @param pointsOnTheCard Points given by the objective card.
     * @param gamefield Reference to the game-field that is being analysed.
     * @return The amount of calculated points on the specific game-field.
     */
    public int calculatePoints(int pointsOnTheCard, GameField gamefield) {
        for (int i=0; i < gamefield.getFeatherCount()/2; i++) {
            gamefield.getPlayer().increaseNumOfCompletedObjective();
        }
        return pointsOnTheCard * (gamefield.getFeatherCount()/2);
    }
}
