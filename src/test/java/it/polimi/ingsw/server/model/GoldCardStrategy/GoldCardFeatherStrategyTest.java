package it.polimi.ingsw.server.model.GoldCardStrategy;

import it.polimi.ingsw.server.model.GameField;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.card.GoldCard;
import it.polimi.ingsw.server.model.card.ResourceCard;
import it.polimi.ingsw.server.model.card.StarterCard;
import it.polimi.ingsw.util.customexceptions.CannotPlaceCardException;
import it.polimi.ingsw.util.supportclasses.Color;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GoldCardFeatherStrategyTest {

    @Test
    void calculatePointsCase1() {
        //carta gold piazzabile e unico punto dato da feather che possiede la gold stessa
        GameField gamefield = new GameField(new Player("player", Color.black));
        gamefield.place(new StarterCard(82), true);
        try {
            gamefield.place(new ResourceCard(23), true, 1,1);
            gamefield.place(new ResourceCard(12), true, -1,-1);
            gamefield.place(new ResourceCard(1), true, 1,-1);
            gamefield.place(new GoldCard(41),true,-1,1);

        } catch (CannotPlaceCardException e) {
            throw new RuntimeException(e);
        }

        assertEquals(1,gamefield.getPlayer().getScore());
    }

    @Test
    void calculatePointsCase2() {
        //carta gold piazzabile e 1 feather già sul tavolo
        GameField gamefield = new GameField(new Player("player", Color.black));
        gamefield.place(new StarterCard(82), true);
        try {
            gamefield.place(new ResourceCard(23), true, 1,1);
            gamefield.place(new ResourceCard(12), true, -1,-1);
            gamefield.place(new ResourceCard(1), true, 1,-1);
            gamefield.place(new ResourceCard(5), true, 2,0);
            gamefield.place(new GoldCard(41),true,-1,1);

        } catch (CannotPlaceCardException e) {
            throw new RuntimeException(e);
        }

        assertEquals(2,gamefield.getPlayer().getScore());
    }

    @Test
    void calculatePointsCase3() {
        //carta gold piazzabile e copre la feather già sul tavolo
        GameField gamefield = new GameField(new Player("player", Color.black));
        gamefield.place(new StarterCard(82), true);
        try {
            gamefield.place(new ResourceCard(23), true, 1,1);
            gamefield.place(new ResourceCard(12), true, -1,-1);
            gamefield.place(new ResourceCard(1), true, 1,-1);
            gamefield.place(new ResourceCard(5), true, 2,0);
            gamefield.place(new GoldCard(41),true,3,1);

        } catch (CannotPlaceCardException e) {
            throw new RuntimeException(e);
        }

        assertEquals(1,gamefield.getPlayer().getScore());
    }


    @Test
    void calculatePointsCase4() {
        //starter card girata, carta gold piazzabile e unica feather della goldcard
        GameField gamefield = new GameField(new Player("player", Color.black));
        gamefield.place(new StarterCard(85), false);
        try {
            gamefield.place(new ResourceCard(23), true, 1,1);
            gamefield.place(new ResourceCard(12), true, -1,1);
            gamefield.place(new GoldCard(51),true,2,0);

        } catch (CannotPlaceCardException e) {
            throw new RuntimeException(e);
        }
        assertEquals(1,gamefield.getPlayer().getScore());
    }


    @Test
    void calculatePointsCase5() {
        //starter card girata, carta gold piazzabile, 2 gold piazzate e 5 punti attesi
        GameField gamefield = new GameField(new Player("player", Color.black));
        gamefield.place(new StarterCard(85), false);
        try {
            gamefield.place(new ResourceCard(23), true, 1,1);
            gamefield.place(new ResourceCard(12), true, -1,1);
            gamefield.place(new ResourceCard(4), true, -2,2);
            gamefield.place(new ResourceCard(3), true, -3,1);
            gamefield.place(new GoldCard(48),true,-1,3);
            gamefield.place(new GoldCard(51),true,2,0);

        } catch (CannotPlaceCardException e) {
            throw new RuntimeException(e);
        }
        assertEquals(5,gamefield.getPlayer().getScore());
    }
}