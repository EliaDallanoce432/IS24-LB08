package it.polimi.ingsw.server.model.ObjectiveCardStrategy;

import it.polimi.ingsw.server.model.GameField;
import it.polimi.ingsw.server.model.card.PlaceableCard;
import it.polimi.ingsw.util.supportclasses.Resource;

import java.util.ArrayList;

public class ObjectiveCardJPatternAAF implements ObjectiveStrategy{
    public int calculatePoints(int pointsOnTheCard, GameField gamefield) {
        int num_jpattern=0;
        ArrayList<PlaceableCard> visitedCard= new ArrayList<PlaceableCard>();
        for (PlaceableCard c : gamefield.getFungiCards())
        {
            int x=c.getX();
            int y= c.getY();
            PlaceableCard card_tempA= gamefield.lookAtCoordinates(x-1, y-1);
            PlaceableCard card_tempB= gamefield.lookAtCoordinates(x-1, y-3);
            if(card_tempA!=null && card_tempA.getCardKingdom() == Resource.animal && !visitedCard.contains(card_tempA))
            {
                if(card_tempB!=null && card_tempB.getCardKingdom() == Resource.animal && !visitedCard.contains(card_tempB)) {
                    visitedCard.add(card_tempA);
                    visitedCard.add(card_tempB);
                    num_jpattern++;
                }
            }
        }

        return pointsOnTheCard*num_jpattern;
    }
}
