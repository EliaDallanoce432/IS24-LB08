package it.polimi.ingsw.server.model.GoldCardStrategy;

import it.polimi.ingsw.server.model.GameField;
import it.polimi.ingsw.server.model.card.GoldCard;

public class GoldCardScrollStrategy implements ConditionStrategy{
    /**
     * @param pointsOnTheCard points given by the card
     * @param gamefield       gamefield that is being analysed
     * @param goldcard
     * @return the points given by the card
     */
    public int calculatePoints(int pointsOnTheCard, GameField gamefield, GoldCard goldcard) {
        return pointsOnTheCard * gamefield.getScrollCount() + pointsOnTheCard;
        /*moltiplica i punti della carta per il numero delle risorse + la risorsa che si trova sulla carta stessa*/
    }
}
