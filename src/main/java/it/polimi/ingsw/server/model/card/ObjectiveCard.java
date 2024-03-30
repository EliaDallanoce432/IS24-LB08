package it.polimi.ingsw.server.model.card;

import it.polimi.ingsw.server.model.GameField;
import it.polimi.ingsw.server.model.ObjectiveCardStrategy.ObjectiveCardContext;
import it.polimi.ingsw.server.model.card.Card;

public class ObjectiveCard extends Card {
    protected int points;
    protected ObjectiveCardContext context;

    public ObjectiveCard() {

    }
    public int getPoints() {
        return points;
    }

    /**
     * method returns the points earned with this objective card
     * @param gameField gamefield on which the objective has to be checked
     * @return int
     */
    public int earnedPoints(GameField gameField) {
        return context.executePointsCalculation(this.getPoints(),gameField);
    }

}
