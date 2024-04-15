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

class ObjectiveCardDoubleFeatherTest {

    @Test
    void calculatePointsCase1() {
        GameField gameField = new GameField(new Player("test", Color.red));
        ObjectiveCard objectiveCard = new ObjectiveCard(102);
        try {
            gameField.place(new StarterCard(81), true);
            gameField.place(new ResourceCard(15), true, -1,1);
            gameField.place(new ResourceCard(15), true, -2,0);
        } catch (CannotPlaceCardException e) {
            throw new RuntimeException(e);
        }
        assertEquals(0,objectiveCard.getEarnedPoints(gameField));
    }

    @Test
    void calculatePointsCase2() {
        GameField gameField = new GameField(new Player("test", Color.red));
        ObjectiveCard objectiveCard = new ObjectiveCard(102);
        try {
            gameField.place(new StarterCard(81), true);
            gameField.place(new ResourceCard(15), true, -1,1);
            gameField.place(new ResourceCard(15), true, -2,0);
            gameField.place(new ResourceCard(15), true, -2,2);
        } catch (CannotPlaceCardException e) {
            throw new RuntimeException(e);
        }
        assertEquals(2,objectiveCard.getEarnedPoints(gameField));
    }

    @Test
    void calculatePointsCase3() {
        GameField gameField = new GameField(new Player("test", Color.red));
        ObjectiveCard objectiveCard = new ObjectiveCard(102);
        try {
            gameField.place(new StarterCard(81), true);
            gameField.place(new ResourceCard(15), true, -1,1);
            gameField.place(new ResourceCard(15), true, -2,0);
            gameField.place(new ResourceCard(15), true, -2,2);
            gameField.place(new ResourceCard(15), true, 1,-1);
            gameField.place(new ResourceCard(15), true, 2,-2);
        } catch (CannotPlaceCardException e) {
            throw new RuntimeException(e);
        }
        assertEquals(4,objectiveCard.getEarnedPoints(gameField));
    }
    @Test
    void calculatePointsCase4() {
        GameField gameField = new GameField(new Player("test", Color.red));
        ObjectiveCard objectiveCard = new ObjectiveCard(102);
        try {
            gameField.place(new StarterCard(81), true);
            gameField.place(new ResourceCard(15), true, -1,1);
            gameField.place(new ResourceCard(15), true, -2,0);
            gameField.place(new ResourceCard(15), true, -2,2);
            gameField.place(new ResourceCard(15), true, 1,-1);
        } catch (CannotPlaceCardException e) {
            throw new RuntimeException(e);
        }
        assertEquals(2,objectiveCard.getEarnedPoints(gameField));
    }

}