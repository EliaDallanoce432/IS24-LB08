package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.model.card.StarterCard;
import it.polimi.ingsw.util.supportclasses.Color;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameFieldTest {

    @Test
    void lookAtCoordinates() {
        GameField gamefield = new GameField(new Player("player", Color.black));
        StarterCard card = new StarterCard(82);
        gamefield.place(card, true);
        assertEquals(card,gamefield.lookAtCoordinates(0,0));
    }

    @Test
    void lookAtCoordinatesEmptyPosition() {
        GameField gamefield = new GameField(new Player("player", Color.black));
        assertNull(gamefield.lookAtCoordinates(0,0));
    }

    @Test
    void lookAtCoordinatesOutOfRange() {
        GameField gamefield = new GameField(new Player("player", Color.black));
        assertNull(gamefield.lookAtCoordinates(90,-110));
    }

    @Test
    void placeStarterCard() {
        GameField gamefield = new GameField(new Player("player", Color.black));
        StarterCard card = new StarterCard(82);
        gamefield.place(card, true);
        assertEquals(card,gamefield.lookAtCoordinates(0,0));
    }

    @Test
    void placeStarterCardFungiResource() {
        GameField gamefield = new GameField(new Player("player", Color.black));
        gamefield.place(new StarterCard(82), true);
        assertEquals(1, gamefield.getFungiCount());
    }

    @Test
    void place() {
        GameField gamefield = new GameField(new Player("player", Color.black));
    }

    //TODO more testing for the place method both for starter cards and generic placeable cards
}