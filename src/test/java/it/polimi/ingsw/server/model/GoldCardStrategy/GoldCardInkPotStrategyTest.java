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

class GoldCardInkPotStrategyTest {

    @Test
    void calculatePointsCase1() {
        //carta gold piazzabile e unico punto dato da Inkpot che possiede la gold stessa
        GameField gamefield = new GameField(new Player("player", Color.black));
        gamefield.place(new StarterCard(82), true);
        try {
            gamefield.place(new ResourceCard(34), true, 1, 1);
            gamefield.place(new GoldCard(73), true,1, -1);
        } catch (CannotPlaceCardException e) {
            throw new RuntimeException(e);
        }

        assertEquals(1,gamefield.getPlayer().getScore());
    }

    @Test
    void calculatePointsCase2() {
        //carta gold piazzabile e unico punto dato da Inkpot che possiede la gold stessa
        GameField gamefield = new GameField(new Player("player", Color.black));
        gamefield.place(new StarterCard(82), true);
        try {
            gamefield.place(new ResourceCard(34), true, 1, 1);
            gamefield.place(new ResourceCard(25), true, 2, 2);
            gamefield.place(new GoldCard(73), true,1, -1);
        } catch (CannotPlaceCardException e) {
            throw new RuntimeException(e);
        }

        assertEquals(2,gamefield.getPlayer().getScore());
    }
}