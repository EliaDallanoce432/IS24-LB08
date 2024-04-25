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

class ObjectiveCardDoubleScrollTest {

    private Player player;

    @BeforeEach
    void setup() {
        player = new Player("aaa",Color.black);
    }

    @AfterEach
    void teardown() {player = null;}
    @Test
    void calculatePointsCase1() {

        ObjectiveCard objectiveCard = new ObjectiveCard(100);
        try {
            player.place(new StarterCard(81), true);
            player.place(new ResourceCard(26), true, 1,-1);
            player.place(new ResourceCard(26), true, 2,-2);
        } catch (CannotPlaceCardException e) {
            throw new RuntimeException(e);
        }
        assertEquals(0,objectiveCard.getEarnedPoints(player.getGamefield()));
    }

    @Test
    void calculatePointsCase2() {

        ObjectiveCard objectiveCard = new ObjectiveCard(100);
        try {
            player.place(new StarterCard(81), true);
            player.place(new ResourceCard(26), true, 1,-1);
            player.place(new ResourceCard(26), true, 2,-2);
            player.place(new ResourceCard(26), true, 3,-1);
        } catch (CannotPlaceCardException e) {
            throw new RuntimeException(e);
        }
        assertEquals(2,objectiveCard.getEarnedPoints(player.getGamefield()));
    }

    @Test
    void calculatePointsCase3() {

        ObjectiveCard objectiveCard = new ObjectiveCard(100);
        try {
            player.place(new StarterCard(81), true);
            player.place(new ResourceCard(26), true, 1,-1);
            player.place(new ResourceCard(26), true, 2,-2);
            player.place(new ResourceCard(26), true, 3,-1);
            player.place(new ResourceCard(26), true, 4,0);
            player.place(new ResourceCard(26), true, 5,1);
        } catch (CannotPlaceCardException e) {
            throw new RuntimeException(e);
        }
        assertEquals(4,objectiveCard.getEarnedPoints(player.getGamefield()));
    }
    @Test
    void calculatePointsCase4() {

        ObjectiveCard objectiveCard = new ObjectiveCard(100);
        try {
            player.place(new StarterCard(81), true);
            player.place(new ResourceCard(26), true, 1,-1);
            player.place(new ResourceCard(26), true, 2,-2);
            player.place(new ResourceCard(26), true, 3,-1);
            player.place(new ResourceCard(26), true, 4,0);
        } catch (CannotPlaceCardException e) {
            throw new RuntimeException(e);
        }
        assertEquals(2,objectiveCard.getEarnedPoints(player.getGamefield()));
    }
}