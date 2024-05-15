package it.polimi.ingsw.server.model.card.ObjectiveCardStrategy;

import it.polimi.ingsw.server.model.GameField;
import it.polimi.ingsw.server.model.card.PlaceableCard;
import it.polimi.ingsw.util.supportclasses.Resource;

import java.util.ArrayList;

public class ObjectiveCardJPatternPPI implements ObjectiveStrategy {
    /**
     * Used to calculate the amount of points given for each Plant-Plant-Insect pattern
     * found on the game-field
     * @param pointsOnTheCard points multiplier written on the card
     * @param gamefield game-field on which the objective has to be checked
     * @return
     */
    public int calculatePoints(int pointsOnTheCard, GameField gamefield) {
        int num_jpattern=0;
        ArrayList<PlaceableCard> visitedCards= new ArrayList<>();
        for (PlaceableCard c : gamefield.getInsectCards())
        {
            int x=c.getX();
            int y= c.getY();
            PlaceableCard card_tempA= gamefield.lookAtCoordinates(x+1, y+1);
            PlaceableCard card_tempB= gamefield.lookAtCoordinates(x+1, y+3);
            if(card_tempA!=null && card_tempA.getCardKingdom() == Resource.plant && !visitedCards.contains(card_tempA))
            {
                if(card_tempB!=null && card_tempB.getCardKingdom() == Resource.plant && !visitedCards.contains(card_tempB)) {
                    visitedCards.add(card_tempA);
                    visitedCards.add(card_tempB);
                    num_jpattern++;
                    gamefield.getPlayer().increaseNumOfCompletedObjective();
                }
            }
        }

        return pointsOnTheCard*num_jpattern;
    }

}
