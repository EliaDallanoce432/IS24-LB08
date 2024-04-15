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

class ObjectiveCardDiagonalAnimalTest {

    @Test
    void calculatePointsSimpleCase() {
        GameField gameField = new GameField(new Player("test", Color.red));
        ObjectiveCard objectiveCard = new ObjectiveCard(89);
        try {
            gameField.place(new StarterCard(81), true);
            gameField.place(new ResourceCard(28), true, 1,1);
            gameField.place(new ResourceCard(28), true, 2,2);
            gameField.place(new ResourceCard(28), true, 3,3);
        } catch (CannotPlaceCardException e) {
            throw new RuntimeException(e);
        }
        assertEquals(2,objectiveCard.getEarnedPoints(gameField));
    }

    @Test
    void calculatePointsDiagonalWith4CardsCase() {
        GameField gameField = new GameField(new Player("test", Color.red));
        ObjectiveCard objectiveCard = new ObjectiveCard(89);
        try {
            gameField.place(new StarterCard(81), true);
            gameField.place(new ResourceCard(28), true, 1,1);
            gameField.place(new ResourceCard(28), true, 2,2);
            gameField.place(new ResourceCard(28), true, 3,3);
            gameField.place(new ResourceCard(28), true, 4,4);
        } catch (CannotPlaceCardException e) {
            throw new RuntimeException(e);
        }
        assertEquals(2,objectiveCard.getEarnedPoints(gameField));
    }

    @Test
    void calculatePointsNoTripletsCase() {
        GameField gameField = new GameField(new Player("test", Color.red));
        ObjectiveCard objectiveCard = new ObjectiveCard(89);
        try {
            gameField.place(new StarterCard(81), true);
            gameField.place(new ResourceCard(28), true, 1,1);
            gameField.place(new ResourceCard(28), true, 2,2);
        } catch (CannotPlaceCardException e) {
            throw new RuntimeException(e);
        }
        assertEquals(0,objectiveCard.getEarnedPoints(gameField));
    }

    @Test
    void calculatePointsDoubleTripletCase() {
        GameField gameField = new GameField(new Player("test", Color.red));
        ObjectiveCard objectiveCard = new ObjectiveCard(89);
        try {
            gameField.place(new StarterCard(81), true);
            gameField.place(new ResourceCard(28), true, 1,1);
            gameField.place(new ResourceCard(28), true, 2,2);
            gameField.place(new ResourceCard(28), true, 3,3);
            gameField.place(new ResourceCard(28), true, 2,4);
            gameField.place(new ResourceCard(28), true, 3,5);
            gameField.place(new ResourceCard(28), true, 4,6);
        } catch (CannotPlaceCardException e) {
            throw new RuntimeException(e);
        }
        assertEquals(4,objectiveCard.getEarnedPoints(gameField));
    }
}