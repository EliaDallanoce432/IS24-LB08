package it.polimi.ingsw.server.model.deck;

import it.polimi.ingsw.server.controller.GameObserver;
import it.polimi.ingsw.server.model.card.Card;
import it.polimi.ingsw.server.model.card.GoldCard;
import it.polimi.ingsw.util.customexceptions.EmptyDeckException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GoldCardDeckTest {
    GoldCardDeck goldCardDeck;
    @BeforeEach
    void setUp () {
         goldCardDeck = new GoldCardDeck();
    }

    @AfterEach
    void tearDown() {
        goldCardDeck = null;
    }

    @Test
    void generateDeck() {

        assertEquals(38, goldCardDeck.cards.size());
    }
    @Test
    void getLeftRevealedCard() {

        Card leftrevealedcard = goldCardDeck.drawLeftRevealedCard();
        assertEquals(leftrevealedcard.getClass(), GoldCard.class);
    }

    @Test
    void getRightRevealedCard() {

        Card rightrevealedcard = goldCardDeck.drawRightRevealedCard();
        assertEquals(rightrevealedcard.getClass(), GoldCard.class);
    }

    @Test
    void directDraw() {

        try {
            goldCardDeck.directDraw();
        } catch (EmptyDeckException e) {
            throw new RuntimeException(e);
        }
        assertEquals(37, goldCardDeck.cards.size());
    }

    @Test
    void directDrawEmptyDeck() {

        for (int i=0; i<38; i++) {
            try {
                goldCardDeck.directDraw();
            } catch (EmptyDeckException e) {
                throw new RuntimeException(e);
            }
        }
        assertThrows(EmptyDeckException.class,()-> goldCardDeck.directDraw());
    }
}