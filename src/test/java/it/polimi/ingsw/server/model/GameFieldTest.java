package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.model.card.GoldCard;
import it.polimi.ingsw.server.model.card.ResourceCard;
import it.polimi.ingsw.server.model.card.StarterCard;
import it.polimi.ingsw.util.customexceptions.CannotPlaceCardException;
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
    void lookAtCoordinatesOutOfBounds() {
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
    void placeMultipleCardsFungiResource() {
        GameField gamefield = new GameField(new Player("player", Color.black));
        gamefield.place(new StarterCard(82), true);
        try {
            gamefield.place(new ResourceCard(1),true,1,1);
            gamefield.place(new ResourceCard(3),true,2,2);
        } catch (CannotPlaceCardException e) {
            throw new RuntimeException(e);
        }
        assertEquals(5, gamefield.getFungiCount());
    }

    @Test
    void placeGoldCardWithEnoughResources() {
        GameField gamefield = new GameField(new Player("player", Color.black));
        gamefield.place(new StarterCard(82), true);
        try {
            gamefield.place(new ResourceCard(1),true,1,1);
            gamefield.place(new ResourceCard(3),true,2,2);
        } catch (CannotPlaceCardException e) {
            throw new RuntimeException(e);
        }
        assertAll(()->gamefield.place(new GoldCard(49),true,-1,1));
    }

    @Test
    void placeCheckExceptionOccupiedSpot() {
        GameField gamefield = new GameField(new Player("player", Color.black));
        gamefield.place(new StarterCard(82), true);
        assertThrows(CannotPlaceCardException.class, () -> gamefield.place(new ResourceCard(1),true,0,0));
    }

    @Test
    void placeCheckExceptionImpossiblePosition() {
        GameField gamefield = new GameField(new Player("player", Color.black));
        gamefield.place(new StarterCard(82), true);
        assertThrows(CannotPlaceCardException.class, () -> gamefield.place(new ResourceCard(1),true,1,0));
    }

    @Test
    void placeCheckExceptionNoValidNeighbours() {
        GameField gamefield = new GameField(new Player("player", Color.black));
        gamefield.place(new StarterCard(82), true);
        assertThrows(CannotPlaceCardException.class, () -> gamefield.place(new ResourceCard(1),true,5,5));
    }

    @Test
    void placeCheckExceptionGoldCardNotEnoughRequirements() {
        GameField gamefield = new GameField(new Player("player", Color.black));
        gamefield.place(new StarterCard(82), true);
        assertThrows(CannotPlaceCardException.class, () -> gamefield.place(new GoldCard(72),true,1,1));
    }
}