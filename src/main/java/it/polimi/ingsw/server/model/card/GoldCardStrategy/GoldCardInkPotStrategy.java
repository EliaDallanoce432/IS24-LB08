package it.polimi.ingsw.server.model.card.GoldCardStrategy;

import it.polimi.ingsw.server.model.GameField;
import it.polimi.ingsw.server.model.card.GoldCard;

/**
 * This class is used to apply the InkPot strategy on a gold-card
 */
public class GoldCardInkPotStrategy implements ConditionStrategy {

    /**
     * Used to calculate the amount of points given by the gold-card for each visible InkPot on the game-field
     * @param pointsOnTheCard points given by the card
     * @param gamefield game-field that is being analysed
     * @param goldcard  the gold-card used for the calculation
     * @return the amount of calculated points
     */
    public int calculatePoints(int pointsOnTheCard, GameField gamefield, GoldCard goldcard) {
        return pointsOnTheCard * gamefield.getInkPotCount();
        /*moltiplica i punti della carta per il numero delle risorse*/
    }
}
