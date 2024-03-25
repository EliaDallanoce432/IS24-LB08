package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.model.ObjectiveCardStrategy.ObjectiveCardContext;

public class ObjectiveCard extends Card{
    private int points;
    private ObjectiveCardContext context;

    public int getPoints() {
        return points;
    }

}
