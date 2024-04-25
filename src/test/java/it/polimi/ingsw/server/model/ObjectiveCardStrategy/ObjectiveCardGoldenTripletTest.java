package it.polimi.ingsw.server.model.ObjectiveCardStrategy;

import it.polimi.ingsw.server.model.GameField;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.card.ObjectiveCard;
import it.polimi.ingsw.server.model.card.ResourceCard;
import it.polimi.ingsw.server.model.card.StarterCard;
import it.polimi.ingsw.util.customexceptions.CannotPlaceCardException;
import it.polimi.ingsw.util.supportclasses.Color;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ObjectiveCardGoldenTripletTest {

    private Player player;

    @BeforeEach
    void setup() {
        player = new Player("aaa",Color.black);
    }

    @AfterEach
    void teardown() {player = null;}
    @Test
    void calculatePointsCase1() {

        ObjectiveCard objectiveCard = new ObjectiveCard(99);
        try {
            player.place(new StarterCard(81), true);
            player.place(new ResourceCard(5), true, 1,1);
        } catch (CannotPlaceCardException e) {
            throw new RuntimeException(e);
        }
        assertEquals(0,objectiveCard.getEarnedPoints(player.getGamefield()));
    }

    @Test
    void calculatePointsCase2() {

        ObjectiveCard objectiveCard = new ObjectiveCard(99);
        try {
            player.place(new StarterCard(81), true);
            player.place(new ResourceCard(6), true, 1,1);
        } catch (CannotPlaceCardException e) {
            throw new RuntimeException(e);
        }
        assertEquals(0,objectiveCard.getEarnedPoints(player.getGamefield()));
    }
    @Test
    void calculatePointsCase3() {

        ObjectiveCard objectiveCard = new ObjectiveCard(99);
        try {
            player.place(new StarterCard(81), true);
            player.place(new ResourceCard(7), true, 1,1);
        } catch (CannotPlaceCardException e) {
            throw new RuntimeException(e);
        }
        assertEquals(0,objectiveCard.getEarnedPoints(player.getGamefield()));
    }

    @Test
    void calculatePointsCase4() {

        ObjectiveCard objectiveCard = new ObjectiveCard(99);
        try {
            player.place(new StarterCard(81), true);
            player.place(new ResourceCard(5), true, 1,1);
            player.place(new ResourceCard(6), true, 2,0);
        } catch (CannotPlaceCardException e) {
            throw new RuntimeException(e);
        }
        assertEquals(0,objectiveCard.getEarnedPoints(player.getGamefield()));
    }

    @Test
    void calculatePointsCase5() {

        ObjectiveCard objectiveCard = new ObjectiveCard(99);
        try {
            player.place(new StarterCard(81), true);
            player.place(new ResourceCard(5), true, 1,1);
            player.place(new ResourceCard(7), true, 2,0);
        } catch (CannotPlaceCardException e) {
            throw new RuntimeException(e);
        }
        assertEquals(0,objectiveCard.getEarnedPoints(player.getGamefield()));
    }

    @Test
    void calculatePointsCase6() {

        ObjectiveCard objectiveCard = new ObjectiveCard(99);
        try {
            player.place(new StarterCard(81), true);
            player.place(new ResourceCard(6), true, 1,1);
            player.place(new ResourceCard(7), true, 2,0);
        } catch (CannotPlaceCardException e) {
            throw new RuntimeException(e);
        }
        assertEquals(0,objectiveCard.getEarnedPoints(player.getGamefield()));
    }

    @Test
    void calculatePointsCase7() {

        ObjectiveCard objectiveCard = new ObjectiveCard(99);
        try {
            player.place(new StarterCard(81), true);
            player.place(new ResourceCard(6), true, 1,1);
            player.place(new ResourceCard(7), true, 2,0);
            player.place(new ResourceCard(5), true, 2,2);
        } catch (CannotPlaceCardException e) {
            throw new RuntimeException(e);
        }
        assertEquals(3,objectiveCard.getEarnedPoints(player.getGamefield()));
    }

    @Test
    void calculatePointsCase8() {

        ObjectiveCard objectiveCard = new ObjectiveCard(99);
        try {
            player.place(new StarterCard(81), true);
            player.place(new ResourceCard(6), true, 1,1);
            player.place(new ResourceCard(7), true, 2,0);
            player.place(new ResourceCard(5), true, 2,2);
            player.place(new ResourceCard(5), true, 3,1);
        } catch (CannotPlaceCardException e) {
            throw new RuntimeException(e);
        }
        assertEquals(3,objectiveCard.getEarnedPoints(player.getGamefield()));
    }

    @Test
    void calculatePointsCase9() {

        ObjectiveCard objectiveCard = new ObjectiveCard(99);
        try {
            player.place(new StarterCard(81), true);
            player.place(new ResourceCard(6), true, 1,1);
            player.place(new ResourceCard(7), true, 2,0);
            player.place(new ResourceCard(5), true, 2,2);
            player.place(new ResourceCard(5), true, -1,-1);
            player.place(new ResourceCard(6), true, -2,-2);
            player.place(new ResourceCard(7), true, -1,-3);
        } catch (CannotPlaceCardException e) {
            throw new RuntimeException(e);
        }
        assertEquals(6,objectiveCard.getEarnedPoints(player.getGamefield()));
    }



}