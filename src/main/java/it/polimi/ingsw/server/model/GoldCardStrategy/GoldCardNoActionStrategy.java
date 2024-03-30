package it.polimi.ingsw.server.model.GoldCardStrategy;

import it.polimi.ingsw.server.model.GameField;
import it.polimi.ingsw.server.model.card.GoldCard;

public class GoldCardNoActionStrategy implements ConditionStrategy{
    public int calculatePoints(int pointsOnTheCard, GameField gamefield, GoldCard goldcard) {
        return pointsOnTheCard;
    }
}
