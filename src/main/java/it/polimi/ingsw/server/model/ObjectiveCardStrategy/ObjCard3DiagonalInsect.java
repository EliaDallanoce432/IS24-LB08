package it.polimi.ingsw.server.model.ObjectiveCardStrategy;

import it.polimi.ingsw.server.model.GameField;

public class ObjCard3DiagonalInsect implements ObjectiveStrategy {
    public int calculatePoints(int pointsOnTheCard, GameField gamefield) {
        return 1;
    }
}
