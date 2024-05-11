package it.polimi.ingsw.server.model.deck;

import it.polimi.ingsw.server.controller.GameObserver;
import it.polimi.ingsw.server.model.card.Card;
import it.polimi.ingsw.server.model.card.ResourceCard;
import it.polimi.ingsw.util.customexceptions.EmptyDeckException;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class ResourceCardDeckTest {
    ResourceCardDeck resourceCardDeck;

    @BeforeEach
    void setUp() {
        resourceCardDeck = new ResourceCardDeck();
    }

    @AfterEach
    void tearDown() {
        resourceCardDeck = null;
    }

    @Test
    void generateDeck() {

        assertEquals(38, resourceCardDeck.cards.size());
    }
    @Test
    void getLeftRevealedCard() {

        Card leftrevealedcard = resourceCardDeck.drawLeftRevealedCard();
        assertEquals(leftrevealedcard.getClass(), ResourceCard.class);
    }

    @Test
    void getRightRevealedCard() {

        Card rightrevealedcard = resourceCardDeck.drawRightRevealedCard();
        assertEquals(rightrevealedcard.getClass(), ResourceCard.class);
    }

    @Test
    void directDraw() {

        Card resourcecard;
        try {
            resourcecard=resourceCardDeck.directDraw();
        } catch (EmptyDeckException e) {
            throw new RuntimeException(e);
        }
        assertEquals(resourcecard.getClass(),ResourceCard.class);
    }

    @Test
    void directDrawEmptyDeck() {

        for (int i=0; i<38; i++) {
            try {
                resourceCardDeck.directDraw();
            } catch (EmptyDeckException e) {
                throw new RuntimeException(e);
            }
        }
        assertThrows(EmptyDeckException.class,()-> resourceCardDeck.directDraw());
    }
}