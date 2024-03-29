package it.polimi.ingsw.server.model.GoldCardStrategy;
import it.polimi.ingsw.server.model.GameField;
public class GoldCardContext {
    private ConditionStrategy strategy;

    public GoldCardContext(ConditionStrategy strategy) {
        this.strategy = strategy;
    }
    public int executePointsCalculation(int pointsOnTheCard, GameField gamefield) {
        return strategy.calculatePoints(pointsOnTheCard, gamefield);
    }
}
