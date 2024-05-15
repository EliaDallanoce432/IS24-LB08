package it.polimi.ingsw.server.model.card.ObjectiveCardStrategy;

import it.polimi.ingsw.server.model.GameField;

public class ObjectiveCardDoubleScroll implements ObjectiveStrategy {
    /**
     * Used to calculate the amount of points given for each couple of Scroll found on the game-field
     * @param pointsOnTheCard points multiplier written on the card
     * @param gamefield game-field on which the objective has to be checked
     * @return the amount of calculated points
     */
    public int calculatePoints(int pointsOnTheCard, GameField gamefield) {
        for (int i=0; i < gamefield.getScrollCount()/2; i++) {
            gamefield.getPlayer().increaseNumOfCompletedObjective();
        }
        return pointsOnTheCard * (gamefield.getScrollCount()/2);
    }
}
