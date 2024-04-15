package it.polimi.ingsw.server.model.deck;

import it.polimi.ingsw.server.model.card.Card;
import it.polimi.ingsw.server.model.card.StarterCard;
import it.polimi.ingsw.util.customexceptions.EmptyDeckException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StarterCardDeckTest {

    @Test
    void directDraw() {
        StarterCardDeck startercarddecktest = new StarterCardDeck();
        Card startercard ;
        try {
            startercard = startercarddecktest.directDraw();
        } catch (EmptyDeckException e) {
            throw new RuntimeException(e);
        }
        assertEquals(startercard.getClass(), StarterCard.class);
    }

    @Test
    void directDrawEmptyDeck() {
        StarterCardDeck startercarddecktest = new StarterCardDeck();

        for (int i=0; i<6; i++) {
            try {
                startercarddecktest.directDraw();
            } catch (EmptyDeckException e) {
                throw new RuntimeException(e);
            }
        }
        assertThrows(EmptyDeckException.class,()-> startercarddecktest.directDraw());
    }

    @Test
    void generateDeck() {
        StarterCardDeck startercarddecktest = new StarterCardDeck();
        assertEquals(6, startercarddecktest.cards.size());
    }
}