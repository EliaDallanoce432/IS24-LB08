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

class ObjectiveCardThreeFungiTest {

    @Test
    void calculatePointsCase1() {
        GameField gamefield = new GameField(new Player("player", Color.black));
        ObjectiveCard testedcard = new ObjectiveCard(95);
        gamefield.place(new StarterCard(82), true);
        try {
            gamefield.place(new ResourceCard(37), true, 1,1);
            gamefield.place(new ResourceCard(12), true, -1,-1);
        } catch (CannotPlaceCardException e) {
            throw new RuntimeException(e);
        }
        assertEquals(0,testedcard.getEarnedPoints(gamefield));
    }

    @Test
    void calculatePointsCase2() {
        GameField gamefield = new GameField(new Player("player", Color.black));
        ObjectiveCard testedcard = new ObjectiveCard(95);
        gamefield.place(new StarterCard(82), false);
        try {
            gamefield.place(new ResourceCard(36), true, 1,1);
            gamefield.place(new ResourceCard(12), true, -1,-1);
        } catch (CannotPlaceCardException e) {
            throw new RuntimeException(e);
        }
        assertEquals(2,testedcard.getEarnedPoints(gamefield));
    }

    @Test
    void calculatePointsCase3() {
        GameField gamefield = new GameField(new Player("player", Color.black));
        ObjectiveCard testedcard = new ObjectiveCard(95);
            gamefield.place(new StarterCard(82), false);
        try {
            gamefield.place(new ResourceCard(36), true, 1,1);
            gamefield.place(new ResourceCard(13), true, -1,-1);
            gamefield.place(new ResourceCard(1), true, -2,-2);
            gamefield.place(new ResourceCard(2), true, 2,2);
        } catch (CannotPlaceCardException e) {
            throw new RuntimeException(e);
        }
        assertEquals(4,testedcard.getEarnedPoints(gamefield));
    }

    @Test
    void calculatePointsCase4() {
        GameField gamefield = new GameField(new Player("player", Color.black));
        ObjectiveCard testedcard = new ObjectiveCard(95);
        gamefield.place(new StarterCard(82), true);
        try {
            gamefield.place(new ResourceCard(36), true, 1,1);
            gamefield.place(new ResourceCard(13), true, -1,-1);
            gamefield.place(new ResourceCard(1), true, -2,-2);
            gamefield.place(new ResourceCard(2), true, -3,-1);
            gamefield.place(new ResourceCard(39), true, -3,-3);
        } catch (CannotPlaceCardException e) {
            throw new RuntimeException(e);
        }
        assertEquals(2,testedcard.getEarnedPoints(gamefield));
    }
}