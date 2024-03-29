package it.polimi.ingsw.server.model.GoldCardStrategy;

import it.polimi.ingsw.server.model.GameField;
import it.polimi.ingsw.server.model.card.GoldCard;

public interface ConditionStrategy {
    public int calculatePoints(int pointsOnTheCard, GameField gamefield, GoldCard goldcard);
}
