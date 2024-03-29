package it.polimi.ingsw.server.model.ObjectiveCardStrategy;

import it.polimi.ingsw.server.model.GameField;

public interface ObjectiveStrategy {
    public int calculatePoints(int pointsOnTheCard, GameField gamefield);

}
