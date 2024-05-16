package it.polimi.ingsw.server.model.card.GoldCardStrategy;
import it.polimi.ingsw.server.model.GameField;
import it.polimi.ingsw.server.model.card.GoldCard;

/**
 * This class is used to apply the strategy pattern on the gold-cards' method for the points calculation
 */
public class GoldCardContext {
    private final ConditionStrategy strategy;

    public GoldCardContext(ConditionStrategy strategy) {
        this.strategy = strategy;
    }

    /**
     * Used to calculate the amount of points given by the specific gold-card
     * @param pointsOnTheCard points given by the gold-card
     * @param gamefield identifies the player's game-field
     * @param goldcard used for the calculation
     * @return the amount of calculated points
     */
    public int executePointsCalculation(int pointsOnTheCard, GameField gamefield, GoldCard goldcard) {
        return strategy.calculatePoints(pointsOnTheCard, gamefield,goldcard );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        return o != null && this.getClass() == o.getClass();
    }
}
