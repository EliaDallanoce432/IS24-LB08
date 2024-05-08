package it.polimi.ingsw.server.model.deck;

import it.polimi.ingsw.server.model.card.Card;
import it.polimi.ingsw.server.model.card.GoldCard;
import it.polimi.ingsw.util.customexceptions.EmptyDeckException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GoldCardDeckTest {

    @Test
    void generateDeck() {
        GoldCardDeck goldcarddecktest = new GoldCardDeck();
        assertEquals(38, goldcarddecktest.cards.size());
    }
    @Test
    void getLeftRevealedCard() {
        GoldCardDeck goldcarddecktest = new GoldCardDeck();
        Card leftrevealedcard = goldcarddecktest.drawLeftRevealedCard();
        assertEquals(leftrevealedcard.getClass(), GoldCard.class);
    }

    @Test
    void getRightRevealedCard() {
        GoldCardDeck goldcarddecktest = new GoldCardDeck();
        Card rightrevealedcard = goldcarddecktest.drawRightRevealedCard();
        assertEquals(rightrevealedcard.getClass(), GoldCard.class);
    }

    @Test
    void directDraw() {
        GoldCardDeck goldcarddecktest = new GoldCardDeck();
        try {
            goldcarddecktest.directDraw();
        } catch (EmptyDeckException e) {
            throw new RuntimeException(e);
        }
        assertEquals(37, goldcarddecktest.cards.size());
    }

    @Test
    void directDrawEmptyDeck() {
        GoldCardDeck goldcarddecktest = new GoldCardDeck();

        for (int i=0; i<38; i++) {
            try {
               goldcarddecktest.directDraw();
            } catch (EmptyDeckException e) {
                throw new RuntimeException(e);
            }
        }
        assertThrows(EmptyDeckException.class,()-> goldcarddecktest.directDraw());
    }
}