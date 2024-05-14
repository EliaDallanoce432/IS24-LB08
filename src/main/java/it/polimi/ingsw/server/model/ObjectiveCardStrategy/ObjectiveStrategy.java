package it.polimi.ingsw.server.model.ObjectiveCardStrategy;

import it.polimi.ingsw.server.model.GameField;
import it.polimi.ingsw.server.model.card.PlaceableCard;

import java.util.ArrayList;

public abstract class ObjectiveStrategy {
    /**
     * method performs the calculation of the points received by fulfilling the objective
     * @param pointsOnTheCard points multiplier written on the card
     * @param gamefield game-field on which the objective has to be checked
     * @return the amount of calculated points
     */
    int calculatePoints(int pointsOnTheCard, GameField gamefield){return 0;};

    protected boolean arrayContainsCard(ArrayList<PlaceableCard> arrayList, PlaceableCard card){
        for(PlaceableCard card2 : arrayList){
            if(card2.getId() == card.getId()){
                return true;
            }
        }
        return false;
    }

}
