package it.polimi.ingsw.server.model.GoldCardStrategy;

import it.polimi.ingsw.server.model.GameField;
import it.polimi.ingsw.server.model.card.GoldCard;

public class GoldCardNoActionStrategy implements ConditionStrategy{
    /**
     * @param pointsOnTheCard points given by the card
     * @param gamefield       gamefield that is being analysed
     * @param goldcard
     * @return the card's points
     */
    public int calculatePoints(int pointsOnTheCard, GameField gamefield, GoldCard goldcard) {
        return pointsOnTheCard;
    }
}
