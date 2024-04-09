package it.polimi.ingsw.server.model.ObjectiveCardStrategy;

import it.polimi.ingsw.server.model.GameField;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.card.ObjectiveCard;
import it.polimi.ingsw.server.model.card.ResourceCard;
import it.polimi.ingsw.server.model.card.StarterCard;
import it.polimi.ingsw.util.customexceptions.CannotPlaceCardException;
import it.polimi.ingsw.util.supportclasses.Color;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ObjectiveCardThreePlantTest {

    @Test
    void calculatePointsCase1() {
        GameField gamefield = new GameField(new Player("player", Color.black));
        ObjectiveCard testedcard = new ObjectiveCard(96);
        gamefield.place(new StarterCard(85));
        try {
            gamefield.place(new ResourceCard(36),1,1);
            gamefield.place(new ResourceCard(12),-1,-1);
        } catch (CannotPlaceCardException e) {
            throw new RuntimeException(e);
        }
        assertEquals(0,testedcard.getEarnedPoints(gamefield));

    }

    @Test
    void calculatePointsCase2() {
        GameField gamefield = new GameField(new Player("player", Color.black));
        ObjectiveCard testedcard = new ObjectiveCard(96);
        gamefield.place(new StarterCard(85));
        try {
            gamefield.place(new ResourceCard(36),1,1);
            gamefield.place(new ResourceCard(12),1,-1);
        } catch (CannotPlaceCardException e) {
            throw new RuntimeException(e);
        }
        assertEquals(2,testedcard.getEarnedPoints(gamefield));

    }

    @Test
    void calculatePointsCase3() {
        GameField gamefield = new GameField(new Player("player", Color.black));
        ObjectiveCard testedcard = new ObjectiveCard(96);
        gamefield.place(new StarterCard(85));
        try {
            gamefield.place(new ResourceCard(36),1,1);
            gamefield.place(new ResourceCard(12),1,-1);
            gamefield.place(new ResourceCard(13),2,-2);
            gamefield.place(new ResourceCard(14),3,-3);
        } catch (CannotPlaceCardException e) {
            throw new RuntimeException(e);
        }
        assertEquals(4,testedcard.getEarnedPoints(gamefield));

    }
}