package it.polimi.ingsw.server.model.GoldCardStrategy;

import it.polimi.ingsw.server.model.GameField;
import it.polimi.ingsw.server.model.card.GoldCard;

public class GoldCardFeatherStrategy implements ConditionStrategy {

    public int calculatePoints(int pointsOnTheCard, GameField gamefield, GoldCard goldcard) {
            return pointsOnTheCard * gamefield.getFeatherCount() + pointsOnTheCard;
            /*moltiplica i punti della carta per il numero delle risorse + la risorsa che si trova sulla carta stessa*/
    }
}
