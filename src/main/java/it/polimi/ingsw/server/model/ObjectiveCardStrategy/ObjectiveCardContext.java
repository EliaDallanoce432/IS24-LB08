package it.polimi.ingsw.server.model.ObjectiveCardStrategy;

import it.polimi.ingsw.server.model.GoldCardStrategy.ConditionStrategy;

public class ObjectiveCardContext {
    private ObjectiveStrategy strategy;

    public ObjectiveCardContext(ObjectiveStrategy strategy) {
        this.strategy = strategy;
    }
    public int executePointsCalculation(int pointsOnTheCard) {
        return strategy.calculatePoints(pointsOnTheCard);
    }
}