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

class ObjectiveCardJPatternPPITest {

    @Test
    void calculatePointsSimpleCase() {

        GameField gameField = new GameField(new Player("test", Color.red));
        ObjectiveCard objectiveCard = new ObjectiveCard(92);
        try {
            gameField.place(new StarterCard(81));
            gameField.place(new ResourceCard(19),1,1);
            gameField.place(new ResourceCard(20),1,-1);
            gameField.place(new ResourceCard(32),0,-2);
        } catch (CannotPlaceCardException e) {
            throw new RuntimeException(e);
        }
        assertEquals(3,objectiveCard.getEarnedPoints(gameField));
    }

    @Test
    void calculatePointsSimpleCaseWithNegativeCoordinates() {

        GameField gameField = new GameField(new Player("test", Color.red));
        ObjectiveCard objectiveCard = new ObjectiveCard(92);
        try {
            gameField.place(new StarterCard(81));
            gameField.place(new ResourceCard(19),-1,-1);
            gameField.place(new ResourceCard(20),-1,1);
            gameField.place(new ResourceCard(32),-2,-2);
        } catch (CannotPlaceCardException e) {
            throw new RuntimeException(e);
        }
        assertEquals(3,objectiveCard.getEarnedPoints(gameField));
    }

    @Test
    void calculatePointsNoPattern() {

        GameField gameField = new GameField(new Player("test", Color.red));
        ObjectiveCard objectiveCard = new ObjectiveCard(94);
        try {
            gameField.place(new StarterCard(81));
            gameField.place(new ResourceCard(19),1,1);
            gameField.place(new ResourceCard(20),1,-1);
            gameField.place(new ResourceCard(1),0,-2);
        } catch (CannotPlaceCardException e) {
            throw new RuntimeException(e);
        }
        assertEquals(0,objectiveCard.getEarnedPoints(gameField));
    }

    @Test
    void calculatePointsConcatenatedCase() {

        GameField gameField = new GameField(new Player("test", Color.red));
        ObjectiveCard objectiveCard = new ObjectiveCard(92);
        try {
            gameField.place(new StarterCard(81));
            gameField.place(new ResourceCard(19),1,1);
            gameField.place(new ResourceCard(20),1,-1);
            gameField.place(new ResourceCard(32),0,-2);
            gameField.place(new ResourceCard(17),1,-3);
            gameField.place(new ResourceCard(36),0,-4);

        } catch (CannotPlaceCardException e) {
            throw new RuntimeException(e);
        }
        assertEquals(3,objectiveCard.getEarnedPoints(gameField));
    }

    @Test
    void calculatePointsDoubleVerticalJPatternCase() {

        GameField gameField = new GameField(new Player("test", Color.red));
        ObjectiveCard objectiveCard = new ObjectiveCard(92);
        try {
            gameField.place(new StarterCard(81));
            gameField.place(new ResourceCard(19),1,1);
            gameField.place(new ResourceCard(20),1,-1);
            gameField.place(new ResourceCard(32),0,-2);
            gameField.place(new ResourceCard(17),1,-3);
            gameField.place(new ResourceCard(36),0,-4);
            gameField.place(new ResourceCard(15),1,-5);
            gameField.place(new ResourceCard(32),0,-6);

        } catch (CannotPlaceCardException e) {
            throw new RuntimeException(e);
        }
        assertEquals(6,objectiveCard.getEarnedPoints(gameField));
    }

    @Test
    void calculatePointsDoubleHorizontalJPatternCase() {

        GameField gameField = new GameField(new Player("test", Color.red));
        ObjectiveCard objectiveCard = new ObjectiveCard(92);
        try {
            gameField.place(new StarterCard(81));
            gameField.place(new ResourceCard(19),1,1);
            gameField.place(new ResourceCard(20),1,-1);
            gameField.place(new ResourceCard(32),0,-2);
            gameField.place(new ResourceCard(18),2,0);
            gameField.place(new ResourceCard(17),2,-2);
            gameField.place(new ResourceCard(33),1,-3);

        } catch (CannotPlaceCardException e) {
            throw new RuntimeException(e);
        }
        assertEquals(6,objectiveCard.getEarnedPoints(gameField));
    }
}