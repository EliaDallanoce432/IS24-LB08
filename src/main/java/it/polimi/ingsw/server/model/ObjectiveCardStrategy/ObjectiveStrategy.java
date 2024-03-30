package it.polimi.ingsw.server.model.ObjectiveCardStrategy;

import it.polimi.ingsw.server.model.GameField;

public interface ObjectiveStrategy {
    /**
     * method performs the calculation of the points received by fulfilling the objective
     * @param pointsOnTheCard points multiplier written on the card
     * @param gamefield gamefield on which the objective has to be checked
     * @return int
     */
    public int calculatePoints(int pointsOnTheCard, GameField gamefield);

}
