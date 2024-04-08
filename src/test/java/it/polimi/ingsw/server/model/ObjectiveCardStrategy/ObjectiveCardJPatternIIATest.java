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

class ObjectiveCardJPatternIIATest {

    @Test
    void calculatePointsSimpleCase() {

        GameField gameField = new GameField(new Player("test", Color.red));
        ObjectiveCard objectiveCard = new ObjectiveCard(94);
        try {
            gameField.place(new StarterCard(81));
            gameField.place(new ResourceCard(31),1,1);
            gameField.place(new ResourceCard(32),1,-1);
            gameField.place(new ResourceCard(28),0,2);
        } catch (CannotPlaceCardException e) {
            throw new RuntimeException(e);
        }
        assertEquals(3,objectiveCard.getEarnedPoints(gameField));
    }

    @Test
    void calculatePointsSimpleCaseWithNegativeCoordinates() {

        GameField gameField = new GameField(new Player("test", Color.red));
        ObjectiveCard objectiveCard = new ObjectiveCard(94);
        try {
            gameField.place(new StarterCard(81));
            gameField.place(new ResourceCard(31),-1,1);
            gameField.place(new ResourceCard(32),-1,-1);
            gameField.place(new ResourceCard(28),-2,2);
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
            gameField.place(new ResourceCard(31),1,1);
            gameField.place(new ResourceCard(32),1,-1);
            gameField.place(new ResourceCard(33),0,2);
        } catch (CannotPlaceCardException e) {
            throw new RuntimeException(e);
        }
        assertEquals(0,objectiveCard.getEarnedPoints(gameField));
    }

    @Test
    void calculatePointsConcatenatedCase() {

        GameField gameField = new GameField(new Player("test", Color.red));
        ObjectiveCard objectiveCard = new ObjectiveCard(94);
        try {
            gameField.place(new StarterCard(81));
            gameField.place(new ResourceCard(31),1,1);
            gameField.place(new ResourceCard(32),1,-1);
            gameField.place(new ResourceCard(28),0,2);
            gameField.place(new ResourceCard(36),1,3);
            gameField.place(new ResourceCard(26),0,4);

        } catch (CannotPlaceCardException e) {
            throw new RuntimeException(e);
        }
        assertEquals(3,objectiveCard.getEarnedPoints(gameField));
    }

    @Test
    void calculatePointsDoubleVerticalJPatternCase() {

        GameField gameField = new GameField(new Player("test", Color.red));
        ObjectiveCard objectiveCard = new ObjectiveCard(94);
        try {
            gameField.place(new StarterCard(81));
            gameField.place(new ResourceCard(31),1,1);
            gameField.place(new ResourceCard(32),1,-1);
            gameField.place(new ResourceCard(28),0,2);
            gameField.place(new ResourceCard(36),1,3);
            gameField.place(new ResourceCard(26),0,4);
            gameField.place(new ResourceCard(39),1,5);
            gameField.place(new ResourceCard(27),0,6);

        } catch (CannotPlaceCardException e) {
            throw new RuntimeException(e);
        }
        assertEquals(6,objectiveCard.getEarnedPoints(gameField));
    }

    @Test
    void calculatePointsDoubleHorizontalJPatternCase() {

        GameField gameField = new GameField(new Player("test", Color.red));
        ObjectiveCard objectiveCard = new ObjectiveCard(94);
        try {
            gameField.place(new StarterCard(81));
            gameField.place(new ResourceCard(31),1,1);
            gameField.place(new ResourceCard(32),1,-1);
            gameField.place(new ResourceCard(28),0,2);
            gameField.place(new ResourceCard(29),2,2);
            gameField.place(new ResourceCard(35),3,1);
            gameField.place(new ResourceCard(3),4,0);
            gameField.place(new ResourceCard(36),3,-1);

        } catch (CannotPlaceCardException e) {
            throw new RuntimeException(e);
        }
        assertEquals(6,objectiveCard.getEarnedPoints(gameField));
    }
}
