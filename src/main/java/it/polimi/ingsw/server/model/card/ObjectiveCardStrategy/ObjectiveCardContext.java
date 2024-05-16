package it.polimi.ingsw.server.model.card.ObjectiveCardStrategy;

import it.polimi.ingsw.server.model.GameField;

/**
 * This class is used to apply the strategy pattern on the objective cards' method for the final score calculation
 */
public class ObjectiveCardContext {
    private final ObjectiveStrategy strategy;

    public ObjectiveCardContext(ObjectiveStrategy strategy) {
        this.strategy = strategy;
    }

    /**
     * Method used to calculate the amount of points given by a specific objective card
     * @param pointsOnTheCard points given by the objective card
     * @param gameField that is being analysed
     * @return the amount of calculated points on the specific game-field
     */
    public int executePointsCalculation(int pointsOnTheCard, GameField gameField) {
        return strategy.calculatePoints(pointsOnTheCard,gameField);
    }
}
