package it.polimi.ingsw.server.model.ObjectiveCardStrategy;

import it.polimi.ingsw.server.model.GameField;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.card.ObjectiveCard;
import it.polimi.ingsw.server.model.card.ResourceCard;
import it.polimi.ingsw.server.model.card.StarterCard;
import it.polimi.ingsw.util.customexceptions.CannotPlaceCardException;
import it.polimi.ingsw.util.supportclasses.Color;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ObjectiveCardJPatternFFPTest {

    @Test
    void calculatePointsSimpleCase() {

        GameField gameField = new GameField(new Player("test", Color.red));
        ObjectiveCard objectiveCard = new ObjectiveCard(91);
        try {
            gameField.place(new StarterCard(81));
            gameField.place(new ResourceCard(1),1,1);
            gameField.place(new ResourceCard(2),1,-1);
            gameField.place(new ResourceCard(16),2,-2);
        } catch (CannotPlaceCardException e) {
            throw new RuntimeException(e);
        }
        assertEquals(3,objectiveCard.getEarnedPoints(gameField));
    }

    @Test
    void calculatePointsSimpleCaseWithNegativeCoordinates() {

        GameField gameField = new GameField(new Player("test", Color.red));
        ObjectiveCard objectiveCard = new ObjectiveCard(91);
        try {
            gameField.place(new StarterCard(81));
            gameField.place(new ResourceCard(1),-1,1);
            gameField.place(new ResourceCard(2),-1,-1);
            gameField.place(new ResourceCard(16),0,-2);
        } catch (CannotPlaceCardException e) {
            throw new RuntimeException(e);
        }
        assertEquals(3,objectiveCard.getEarnedPoints(gameField));
    }

    @Test
    void calculatePointsNoPattern() {

        GameField gameField = new GameField(new Player("test", Color.red));
        ObjectiveCard objectiveCard = new ObjectiveCard(91);
        try {
            gameField.place(new StarterCard(81));
            gameField.place(new ResourceCard(1),1,1);
            gameField.place(new ResourceCard(2),1,-1);
            gameField.place(new ResourceCard(16),2,0);
        } catch (CannotPlaceCardException e) {
            throw new RuntimeException(e);
        }
        assertEquals(0,objectiveCard.getEarnedPoints(gameField));
    }

    @Test
    void calculatePointsConcatenatedCase() {

        GameField gameField = new GameField(new Player("test", Color.red));
        ObjectiveCard objectiveCard = new ObjectiveCard(91);
        try {
            gameField.place(new StarterCard(81));
            gameField.place(new ResourceCard(1),1,1);
            gameField.place(new ResourceCard(2),1,-1);
            gameField.place(new ResourceCard(17),2,-2);
            gameField.place(new ResourceCard(4),1,-3);
            gameField.place(new ResourceCard(18),2,-4);
        } catch (CannotPlaceCardException e) {
            throw new RuntimeException(e);
        }
        assertEquals(3,objectiveCard.getEarnedPoints(gameField));
    }

    @Test
    void calculatePointsDoubleVerticalJPatternCase() {

        GameField gameField = new GameField(new Player("test", Color.red));
        ObjectiveCard objectiveCard = new ObjectiveCard(91);
        try {
            gameField.place(new StarterCard(81));
            gameField.place(new ResourceCard(1),1,1);
            gameField.place(new ResourceCard(2),1,-1);
            gameField.place(new ResourceCard(17),2,-2);
            gameField.place(new ResourceCard(4),1,-3);
            gameField.place(new ResourceCard(18),2,-4);
            gameField.place(new ResourceCard(9),1,-5);
            gameField.place(new ResourceCard(11),2,-6);

        } catch (CannotPlaceCardException e) {
            throw new RuntimeException(e);
        }
        assertEquals(6,objectiveCard.getEarnedPoints(gameField));
    }

    @Test
    void calculatePointsDoubleHorizontalJPatternCase() {

        GameField gameField = new GameField(new Player("test", Color.red));
        ObjectiveCard objectiveCard = new ObjectiveCard(91);
        try {
            gameField.place(new StarterCard(81));
            gameField.place(new ResourceCard(1),1,1);
            gameField.place(new ResourceCard(2),1,-1);
            gameField.place(new ResourceCard(16),2,-2);
            gameField.place(new ResourceCard(4),3,-1);
            gameField.place(new ResourceCard(18),4,-2);
            gameField.place(new ResourceCard(19),4,0);
            gameField.place(new ResourceCard(6),3,1);

        } catch (CannotPlaceCardException e) {
            throw new RuntimeException(e);
        }
        assertEquals(6,objectiveCard.getEarnedPoints(gameField));
    }
}