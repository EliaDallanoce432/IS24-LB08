package it.polimi.ingsw.server.model.ObjectiveCardStrategy;

import it.polimi.ingsw.server.model.GameField;

public class ObjectiveCardContext {
    private final ObjectiveStrategy strategy;

    public ObjectiveCardContext(ObjectiveStrategy strategy) {
        this.strategy = strategy;
    }
    public int executePointsCalculation(int pointsOnTheCard, GameField gameField) {
        return strategy.calculatePoints(pointsOnTheCard,gameField);
    }
}
