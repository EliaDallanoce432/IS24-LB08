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

class ObjectiveCardThreePlantTest {

    private Player player;

    @BeforeEach
    void setup() {
        player = new Player("aaa",Color.black);
    }

    @AfterEach
    void teardown() {player = null;}

    @Test
    void calculatePointsCase1() {

        ObjectiveCard testedcard = new ObjectiveCard(96);
        player.place(new StarterCard(85), true);
        try {
            player.place(new ResourceCard(36), true, 1,1);
            player.place(new ResourceCard(12), true, -1,-1);
        } catch (CannotPlaceCardException e) {
            throw new RuntimeException(e);
        }
        assertEquals(0,testedcard.getEarnedPoints(player.getGamefield()));

    }

    @Test
    void calculatePointsCase2() {

        ObjectiveCard testedcard = new ObjectiveCard(96);
        player.place(new StarterCard(85), true);
        try {
            player.place(new ResourceCard(36), true, 1,1);
            player.place(new ResourceCard(12), true, 1,-1);
        } catch (CannotPlaceCardException e) {
            throw new RuntimeException(e);
        }
        assertEquals(2,testedcard.getEarnedPoints(player.getGamefield()));

    }

    @Test
    void calculatePointsCase3() {

        ObjectiveCard testedcard = new ObjectiveCard(96);
        player.place(new StarterCard(85), true);
        try {
            player.place(new ResourceCard(36), true, 1,1);
            player.place(new ResourceCard(12), true, 1,-1);
            player.place(new ResourceCard(13), true, 2,-2);
            player.place(new ResourceCard(14), true, 3,-3);
        } catch (CannotPlaceCardException e) {
            throw new RuntimeException(e);
        }
        assertEquals(4,testedcard.getEarnedPoints(player.getGamefield()));

    }
}