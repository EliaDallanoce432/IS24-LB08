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

class GoldCardFeatherStrategyTest {

    private Player player;

    @BeforeEach
    void setup() {
        player = new Player("aaa",Color.black);
    }

    @AfterEach

    void teardown() {player = null;}
    @Test
    void calculatePointsCase1() {
        //carta gold piazzabile e unico punto dato da feather che possiede la gold stessa

        player.place(new StarterCard(82), true);
        try {
            player.place(new ResourceCard(23), true, 1,1);
            player.place(new ResourceCard(12), true, -1,-1);
            player.place(new ResourceCard(1), true, 1,-1);
            player.place(new GoldCard(41),true,-1,1);

        } catch (CannotPlaceCardException e) {
            throw new RuntimeException(e);
        }

        assertEquals(1,player.getScore());
    }

    @Test
    void calculatePointsCase2() {
        //carta gold piazzabile e 1 feather già sul tavolo

        player.place(new StarterCard(82), true);
        try {
            player.place(new ResourceCard(23), true, 1,1);
            player.place(new ResourceCard(12), true, -1,-1);
            player.place(new ResourceCard(1), true, 1,-1);
            player.place(new ResourceCard(5), true, 2,0);
            player.place(new GoldCard(41),true,-1,1);

        } catch (CannotPlaceCardException e) {
            throw new RuntimeException(e);
        }

        assertEquals(2,player.getScore());
    }

    @Test
    void calculatePointsCase3() {
        //carta gold piazzabile e copre la feather già sul tavolo

        player.place(new StarterCard(82), true);
        try {
            player.place(new ResourceCard(23), true, 1,1);
            player.place(new ResourceCard(12), true, -1,-1);
            player.place(new ResourceCard(1), true, 1,-1);
            player.place(new ResourceCard(5), true, 2,0);
            player.place(new GoldCard(41),true,3,1);

        } catch (CannotPlaceCardException e) {
            throw new RuntimeException(e);
        }

        assertEquals(1,player.getScore());
    }


    @Test
    void calculatePointsCase4() {
        //starter card girata, carta gold piazzabile e unica feather della goldcard

        player.place(new StarterCard(85), false);
        try {
            player.place(new ResourceCard(23), true, 1,1);
            player.place(new ResourceCard(12), true, -1,1);
            player.place(new GoldCard(51),true,2,0);

        } catch (CannotPlaceCardException e) {
            throw new RuntimeException(e);
        }
        assertEquals(1,player.getScore());
    }


    @Test
    void calculatePointsCase5() {
        //starter card girata, carta gold piazzabile, 2 gold piazzate e 5 punti attesi

        player.place(new StarterCard(85), false);
        try {
            player.place(new ResourceCard(23), true, 1,1);
            player.place(new ResourceCard(12), true, -1,1);
            player.place(new ResourceCard(4), true, -2,2);
            player.place(new ResourceCard(3), true, -3,1);
            player.place(new GoldCard(48),true,-1,3);
            player.place(new GoldCard(51),true,2,0);

        } catch (CannotPlaceCardException e) {
            throw new RuntimeException(e);
        }
        assertEquals(5,player.getScore());
    }
}