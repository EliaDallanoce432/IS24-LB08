package it.polimi.ingsw.server.model.GoldCardStrategy;

import it.polimi.ingsw.server.model.GameField;

public class GoldCardNoActionStrategy implements ConditionStrategy{
    /**
     *
     * @param pointsOnTheCard points given by the card
     * @param gamefield gamefield that is being analysed
     * @return the card's points
     */
    public int calculatePoints(int pointsOnTheCard, GameField gamefield) {
        return pointsOnTheCard;
    }
}
