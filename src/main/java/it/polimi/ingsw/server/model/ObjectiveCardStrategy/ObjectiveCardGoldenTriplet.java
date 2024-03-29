package it.polimi.ingsw.server.model.ObjectiveCardStrategy;

import it.polimi.ingsw.server.model.GameField;
import it.polimi.ingsw.server.model.card.PlaceableCard;
import it.polimi.ingsw.util.supportclasses.Resource;

import java.util.ArrayList;

public class ObjectiveCardGoldenTriplet implements ObjectiveStrategy{
    public int calculatePoints(int pointsOnTheCard, GameField gamefield) {
        return pointsOnTheCard*Math.min(Math.min(gamefield.getFeatherCount(), gamefield.getInkPotCount()), gamefield.getScrollCount());
    }
}
