package it.polimi.ingsw.server.model.ObjectiveCardStrategy;

import it.polimi.ingsw.server.model.GameField;

public class ObjectiveCardThreeAnimal implements ObjectiveStrategy{
    public int calculatePoints(int pointsOnTheCard, GameField gamefield) {
        for (int i=0; i < gamefield.getAnimalCount()/3; i++) {
            gamefield.getPlayer().increaseNumOfCompletedObjective();
        }
        return pointsOnTheCard * (gamefield.getAnimalCount()/3);
    }
}
