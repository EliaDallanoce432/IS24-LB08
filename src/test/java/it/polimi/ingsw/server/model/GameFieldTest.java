package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.model.card.GoldCard;
import it.polimi.ingsw.server.model.card.ResourceCard;
import it.polimi.ingsw.server.model.card.StarterCard;
import it.polimi.ingsw.util.customexceptions.CannotPlaceCardException;
import it.polimi.ingsw.util.supportclasses.Color;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameFieldTest {
    private Player player;
    @BeforeEach
    void setup() {
        player = new Player("aaa",Color.black);
    }

    @AfterEach
    void teardown() {
        player= null;
    }
    @Test
    void lookAtCoordinates() {

        StarterCard card = new StarterCard(82);
        player.place(card, true);
        assertEquals(card,player.getGamefield().lookAtCoordinates(0,0));
    }

    @Test
    void lookAtCoordinatesEmptyPosition() {

        assertNull(player.getGamefield().lookAtCoordinates(0,0));
    }

    @Test
    void lookAtCoordinatesOutOfBounds() {

        assertNull(player.getGamefield().lookAtCoordinates(90,-110));
    }

    @Test
    void placeStarterCard() {

        StarterCard card = new StarterCard(82);
        player.place(card, true);
        assertEquals(card,player.getGamefield().lookAtCoordinates(0,0));
    }

    @Test
    void placeStarterCardFungiResource() {

        player.place(new StarterCard(82), true);
        assertEquals(1, player.getGamefield().getFungiCount());
    }

    @Test
    void placeMultipleCardsFungiResource() {
        player.place(new StarterCard(82), true);
        try {
            player.place(new ResourceCard(1),true,1,1);
            player.place(new ResourceCard(3),true,2,2);
        } catch (CannotPlaceCardException e) {
            throw new RuntimeException(e);
        }
        assertEquals(5, player.getGamefield().getFungiCount());
    }

    @Test
    void placeGoldCardWithEnoughResources() {

        player.place(new StarterCard(82), true);
        try {
            player.place(new ResourceCard(1),true,1,1);
            player.place(new ResourceCard(3),true,2,2);
        } catch (CannotPlaceCardException e) {
            throw new RuntimeException(e);
        }
        assertAll(()->player.place(new GoldCard(49),true,-1,1));
    }

    @Test
    void placeCheckExceptionOccupiedSpot() {

        player.place(new StarterCard(82), true);
        assertThrows(CannotPlaceCardException.class, () -> player.place(new ResourceCard(1),true,0,0));
    }

    @Test
    void placeCheckExceptionImpossiblePosition() {

        player.place(new StarterCard(82), true);
        assertThrows(CannotPlaceCardException.class, () -> player.place(new ResourceCard(1),true,1,0));
    }

    @Test
    void placeCheckExceptionNoValidNeighbours() {

        player.place(new StarterCard(82), true);
        assertThrows(CannotPlaceCardException.class, () -> player.place(new ResourceCard(1),true,5,5));
    }

    @Test
    void placeCheckExceptionGoldCardNotEnoughRequirements() {

        player.place(new StarterCard(82), true);
        assertThrows(CannotPlaceCardException.class, () -> player.place(new GoldCard(72),true,1,1));
    }
}