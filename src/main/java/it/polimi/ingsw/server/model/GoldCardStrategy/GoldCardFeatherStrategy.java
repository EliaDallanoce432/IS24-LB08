package it.polimi.ingsw.server.model.GoldCardStrategy;

import it.polimi.ingsw.server.model.GameField;

public class GoldCardFeatherStrategy implements ConditionStrategy {

    /**
     *
     * @param pointsOnTheCard points given by the card
     * @param gamefield gamefield that is being analysed
     * @return the points given by the card
     */
    public int calculatePoints(int pointsOnTheCard, GameField gamefield) {
            return pointsOnTheCard * gamefield.getFeatherCount() + pointsOnTheCard;
            /*moltiplica i punti della carta per il numero delle risorse + la risorsa che si trova sulla carta stessa*/
    }
}
