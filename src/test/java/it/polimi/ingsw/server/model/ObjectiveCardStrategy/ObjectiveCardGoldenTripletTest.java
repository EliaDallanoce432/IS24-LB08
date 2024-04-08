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

class ObjectiveCardGoldenTripletTest {

    @Test
    void calculatePointsCase1() {
        GameField gameField = new GameField(new Player("test", Color.red));
        ObjectiveCard objectiveCard = new ObjectiveCard(99);
        try {
            gameField.place(new StarterCard(81));
            gameField.place(new ResourceCard(5),1,1);
        } catch (CannotPlaceCardException e) {
            throw new RuntimeException(e);
        }
        assertEquals(0,objectiveCard.getEarnedPoints(gameField));
    }

    @Test
    void calculatePointsCase2() {
        GameField gameField = new GameField(new Player("test", Color.red));
        ObjectiveCard objectiveCard = new ObjectiveCard(99);
        try {
            gameField.place(new StarterCard(81));
            gameField.place(new ResourceCard(6),1,1);
        } catch (CannotPlaceCardException e) {
            throw new RuntimeException(e);
        }
        assertEquals(0,objectiveCard.getEarnedPoints(gameField));
    }
    @Test
    void calculatePointsCase3() {
        GameField gameField = new GameField(new Player("test", Color.red));
        ObjectiveCard objectiveCard = new ObjectiveCard(99);
        try {
            gameField.place(new StarterCard(81));
            gameField.place(new ResourceCard(7),1,1);
        } catch (CannotPlaceCardException e) {
            throw new RuntimeException(e);
        }
        assertEquals(0,objectiveCard.getEarnedPoints(gameField));
    }

    @Test
    void calculatePointsCase4() {
        GameField gameField = new GameField(new Player("test", Color.red));
        ObjectiveCard objectiveCard = new ObjectiveCard(99);
        try {
            gameField.place(new StarterCard(81));
            gameField.place(new ResourceCard(5),1,1);
            gameField.place(new ResourceCard(6),2,0);
        } catch (CannotPlaceCardException e) {
            throw new RuntimeException(e);
        }
        assertEquals(0,objectiveCard.getEarnedPoints(gameField));
    }

    @Test
    void calculatePointsCase5() {
        GameField gameField = new GameField(new Player("test", Color.red));
        ObjectiveCard objectiveCard = new ObjectiveCard(99);
        try {
            gameField.place(new StarterCard(81));
            gameField.place(new ResourceCard(5),1,1);
            gameField.place(new ResourceCard(7),2,0);
        } catch (CannotPlaceCardException e) {
            throw new RuntimeException(e);
        }
        assertEquals(0,objectiveCard.getEarnedPoints(gameField));
    }

    @Test
    void calculatePointsCase6() {
        GameField gameField = new GameField(new Player("test", Color.red));
        ObjectiveCard objectiveCard = new ObjectiveCard(99);
        try {
            gameField.place(new StarterCard(81));
            gameField.place(new ResourceCard(6),1,1);
            gameField.place(new ResourceCard(7),2,0);
        } catch (CannotPlaceCardException e) {
            throw new RuntimeException(e);
        }
        assertEquals(0,objectiveCard.getEarnedPoints(gameField));
    }

    @Test
    void calculatePointsCase7() {
        GameField gameField = new GameField(new Player("test", Color.red));
        ObjectiveCard objectiveCard = new ObjectiveCard(99);
        try {
            gameField.place(new StarterCard(81));
            gameField.place(new ResourceCard(6),1,1);
            gameField.place(new ResourceCard(7),2,0);
            gameField.place(new ResourceCard(5),2,2);
        } catch (CannotPlaceCardException e) {
            throw new RuntimeException(e);
        }
        assertEquals(3,objectiveCard.getEarnedPoints(gameField));
    }

    @Test
    void calculatePointsCase8() {
        GameField gameField = new GameField(new Player("test", Color.red));
        ObjectiveCard objectiveCard = new ObjectiveCard(99);
        try {
            gameField.place(new StarterCard(81));
            gameField.place(new ResourceCard(6),1,1);
            gameField.place(new ResourceCard(7),2,0);
            gameField.place(new ResourceCard(5),2,2);
            gameField.place(new ResourceCard(5),3,1);
        } catch (CannotPlaceCardException e) {
            throw new RuntimeException(e);
        }
        assertEquals(3,objectiveCard.getEarnedPoints(gameField));
    }

    @Test
    void calculatePointsCase9() {
        GameField gameField = new GameField(new Player("test", Color.red));
        ObjectiveCard objectiveCard = new ObjectiveCard(99);
        try {
            gameField.place(new StarterCard(81));
            gameField.place(new ResourceCard(6),1,1);
            gameField.place(new ResourceCard(7),2,0);
            gameField.place(new ResourceCard(5),2,2);
            gameField.place(new ResourceCard(5),-1,-1);
            gameField.place(new ResourceCard(6),-2,-2);
            gameField.place(new ResourceCard(7),-1,-3);
        } catch (CannotPlaceCardException e) {
            throw new RuntimeException(e);
        }
        assertEquals(6,objectiveCard.getEarnedPoints(gameField));
    }



}