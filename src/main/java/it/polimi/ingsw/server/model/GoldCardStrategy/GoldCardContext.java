package it.polimi.ingsw.server.model.GoldCardStrategy;
import it.polimi.ingsw.server.model.GameField;
import it.polimi.ingsw.server.model.card.GoldCard;

import java.util.Objects;

public class GoldCardContext {
    private final ConditionStrategy strategy;

    public GoldCardContext(ConditionStrategy strategy) {
        this.strategy = strategy;
    }
    public int executePointsCalculation(int pointsOnTheCard, GameField gamefield, GoldCard goldcard) {
        return strategy.calculatePoints(pointsOnTheCard, gamefield,goldcard );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        return o != null && this.getClass() == o.getClass();
    }
}
