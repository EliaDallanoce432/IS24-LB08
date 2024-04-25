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

class ObjectiveCardThreeAnimalTest {

    private Player player;
    @BeforeEach
    void setup() {
        player = new Player("aaa",Color.black);
    }

    @AfterEach
    void teardown() {player = null;}
    @Test
    void calculatePointsCase1() {

        ObjectiveCard testedcard = new ObjectiveCard(97);
        player.place(new StarterCard(82), true);
        try {
            player.place(new ResourceCard(23), true, 1,1);
            player.place(new ResourceCard(12), true, -1,-1);
        } catch (CannotPlaceCardException e) {
            throw new RuntimeException(e);
        }
        assertEquals(0,testedcard.getEarnedPoints(player.getGamefield()));

    }

   @Test
    void calculatePointsCase2() {

        ObjectiveCard testedcard = new ObjectiveCard(97);
        player.place(new StarterCard(82), true);
        try {
            player.place(new ResourceCard(17), true, 1,1);
            player.place(new ResourceCard(12), true, -1,-1);
            player.place(new ResourceCard(21), true, 0,2);
        } catch (CannotPlaceCardException e) {
            throw new RuntimeException(e);
        }
        assertEquals(2,testedcard.getEarnedPoints(player.getGamefield()));

    }

    @Test
    void calculatePointsCase3() {

        ObjectiveCard testedcard = new ObjectiveCard(97);
        player.place(new StarterCard(82), true);
        try {
            player.place(new ResourceCard(17), true, 1,1);
            player.place(new ResourceCard(12), true, -1,-1);
            player.place(new ResourceCard(21), true, 0,2);
            player.place(new ResourceCard(21), true, 1,3);
            player.place(new ResourceCard(21), true, 1,-1);
        } catch (CannotPlaceCardException e) {
            throw new RuntimeException(e);
        }
        assertEquals(4,testedcard.getEarnedPoints(player.getGamefield()));

    }

    @Test
    void calculatePointsCase4() {

        ObjectiveCard testedcard = new ObjectiveCard(97);
        player.place(new StarterCard(82), true);
        try {
            player.place(new ResourceCard(17), true, 1,1);
            player.place(new ResourceCard(23), true, -1,1);
            player.place(new ResourceCard(21), true, -2,2);
            player.place(new ResourceCard(21), true, -2,0);
            player.place(new ResourceCard(21), true, 1,-1);
        } catch (CannotPlaceCardException e) {
            throw new RuntimeException(e);
        }
        assertEquals(4,testedcard.getEarnedPoints(player.getGamefield()));

    }
}