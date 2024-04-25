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

class GoldCardInkPotStrategyTest {

    private Player player;

    @BeforeEach
    void setup() {
        player = new Player("aaa",Color.black);
    }

    @AfterEach

    void teardown() {player = null;}

    @Test
    void calculatePointsCase1() {
        //carta gold piazzabile e unico punto dato da Inkpot che possiede la gold stessa

        player.place(new StarterCard(82), true);
        try {
            player.place(new ResourceCard(34), true, 1, 1);
            player.place(new GoldCard(73), true,1, -1);
        } catch (CannotPlaceCardException e) {
            throw new RuntimeException(e);
        }

        assertEquals(1,player.getScore());
    }

    @Test
    void calculatePointsCase2() {
        //carta gold piazzabile e unico punto dato da Inkpot che possiede la gold stessa

        player.place(new StarterCard(82), true);
        try {
            player.place(new ResourceCard(34), true, 1, 1);
            player.place(new ResourceCard(25), true, 2, 2);
            player.place(new GoldCard(73), true,1, -1);
        } catch (CannotPlaceCardException e) {
            throw new RuntimeException(e);
        }

        assertEquals(2,player.getScore());
    }
}