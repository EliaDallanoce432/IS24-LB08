package it.polimi.ingsw.server.model.card;

import it.polimi.ingsw.server.model.ObjectiveCardStrategy.ObjectiveCardContext;
import it.polimi.ingsw.server.model.card.Card;

public class ObjectiveCard extends Card {
    protected int points;
    protected ObjectiveCardContext context;

    public int getPoints() {
        return points;
    }

}
