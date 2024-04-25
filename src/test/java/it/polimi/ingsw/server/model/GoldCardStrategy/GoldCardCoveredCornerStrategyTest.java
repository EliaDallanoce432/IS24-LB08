package it.polimi.ingsw.server.model.GoldCardStrategy;

import it.polimi.ingsw.server.model.GameField;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.card.GoldCard;
import it.polimi.ingsw.server.model.card.ResourceCard;
import it.polimi.ingsw.server.model.card.StarterCard;
import it.polimi.ingsw.util.customexceptions.CannotPlaceCardException;
import it.polimi.ingsw.util.supportclasses.Color;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GoldCardCoveredCornerStrategyTest {

    private Player player;

    @BeforeEach
    void setup() {
        player = new Player("aaa",Color.black);
    }

    @AfterEach

    void teardown() {player = null;}

    @Test
    void calculatePoints1() {

        player.place(new StarterCard(82), true);
        try {
            player.place(new ResourceCard(4), true, -1,1);
            player.place(new GoldCard(44), true,1, -1);
        } catch (CannotPlaceCardException e) {
            throw new RuntimeException(e);
        }

        assertEquals(2,player.getScore());
    }
    @Test
    void calculatePoints2() {

        player.place(new StarterCard(82), true);
        try {
            player.place(new ResourceCard(4), true, -1,1);
            player.place(new ResourceCard(5), false, 1,-1);
            player.place(new ResourceCard(6), false, 2,0);
            player.place(new GoldCard(44), true,1, 1);
        } catch (CannotPlaceCardException e) {
            throw new RuntimeException(e);
        }
        assertEquals(4,player.getScore());
    }
}