package it.polimi.ingsw.server.model.GoldCardStrategy;

import it.polimi.ingsw.server.model.GameField;

public interface ConditionStrategy {
    public int calculatePoints(int pointsOnTheCard, GameField gamefield);
}
