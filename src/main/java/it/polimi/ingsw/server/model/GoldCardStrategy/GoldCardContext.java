package it.polimi.ingsw.server.model.GoldCardStrategy;

public class GoldCardContext {
    private ConditionStrategy strategy;

    public GoldCardContext(ConditionStrategy strategy) {
        this.strategy = strategy;
    }
    public int executePointsCalculation(int pointsOnTheCard) {
        return strategy.calculatePoints(pointsOnTheCard);
    }
}
