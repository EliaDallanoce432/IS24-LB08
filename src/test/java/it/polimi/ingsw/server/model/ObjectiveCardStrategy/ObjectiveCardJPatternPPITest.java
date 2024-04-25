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

class ObjectiveCardJPatternPPITest {

    private Player player;

    @BeforeEach
    void setup() {
        player = new Player("aaa",Color.black);
    }

    @AfterEach

    void teardown() {player = null;}

    @Test
    void calculatePointsSimpleCase() {

        ObjectiveCard objectiveCard = new ObjectiveCard(92);
        try {
            player.place(new StarterCard(81), true);
            player.place(new ResourceCard(19), true, 1,1);
            player.place(new ResourceCard(20), true, 1,-1);
            player.place(new ResourceCard(32), true, 0,-2);
        } catch (CannotPlaceCardException e) {
            throw new RuntimeException(e);
        }
        assertEquals(3,objectiveCard.getEarnedPoints(player.getGamefield()));
    }

    @Test
    void calculatePointsSimpleCaseWithNegativeCoordinates() {

        ObjectiveCard objectiveCard = new ObjectiveCard(92);
        try {
            player.place(new StarterCard(81), true);
            player.place(new ResourceCard(19), true, -1,-1);
            player.place(new ResourceCard(20), true, -1,1);
            player.place(new ResourceCard(32), true, -2,-2);
        } catch (CannotPlaceCardException e) {
            throw new RuntimeException(e);
        }
        assertEquals(3,objectiveCard.getEarnedPoints(player.getGamefield()));
    }

    @Test
    void calculatePointsNoPattern() {

        ObjectiveCard objectiveCard = new ObjectiveCard(94);
        try {
            player.place(new StarterCard(81), true);
            player.place(new ResourceCard(19), true, 1,1);
            player.place(new ResourceCard(20), true, 1,-1);
            player.place(new ResourceCard(1), true, 0,-2);
        } catch (CannotPlaceCardException e) {
            throw new RuntimeException(e);
        }
        assertEquals(0,objectiveCard.getEarnedPoints(player.getGamefield()));
    }

    @Test
    void calculatePointsConcatenatedCase() {

        ObjectiveCard objectiveCard = new ObjectiveCard(92);
        try {
            player.place(new StarterCard(81), true);
            player.place(new ResourceCard(19), true, 1,1);
            player.place(new ResourceCard(20), true, 1,-1);
            player.place(new ResourceCard(32), true, 0,-2);
            player.place(new ResourceCard(17), true, 1,-3);
            player.place(new ResourceCard(36), true, 0,-4);

        } catch (CannotPlaceCardException e) {
            throw new RuntimeException(e);
        }
        assertEquals(3,objectiveCard.getEarnedPoints(player.getGamefield()));
    }

    @Test
    void calculatePointsDoubleVerticalJPatternCase() {

        ObjectiveCard objectiveCard = new ObjectiveCard(92);
        try {
            player.place(new StarterCard(81), true);
            player.place(new ResourceCard(19), true, 1,1);
            player.place(new ResourceCard(20), true, 1,-1);
            player.place(new ResourceCard(32), true, 0,-2);
            player.place(new ResourceCard(17), true, 1,-3);
            player.place(new ResourceCard(36), true, 0,-4);
            player.place(new ResourceCard(15), true, 1,-5);
            player.place(new ResourceCard(32), true, 0,-6);

        } catch (CannotPlaceCardException e) {
            throw new RuntimeException(e);
        }
        assertEquals(6,objectiveCard.getEarnedPoints(player.getGamefield()));
    }

    @Test
    void calculatePointsDoubleHorizontalJPatternCase() {

        ObjectiveCard objectiveCard = new ObjectiveCard(92);
        try {
            player.place(new StarterCard(81), true);
            player.place(new ResourceCard(19), true, 1,1);
            player.place(new ResourceCard(20), true, 1,-1);
            player.place(new ResourceCard(32), true, 0,-2);
            player.place(new ResourceCard(18), true, 2,0);
            player.place(new ResourceCard(17), true, 2,-2);
            player.place(new ResourceCard(33), true, 1,-3);

        } catch (CannotPlaceCardException e) {
            throw new RuntimeException(e);
        }
        assertEquals(6,objectiveCard.getEarnedPoints(player.getGamefield()));
    }
}