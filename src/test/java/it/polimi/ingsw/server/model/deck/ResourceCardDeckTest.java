package it.polimi.ingsw.server.model.deck;

import it.polimi.ingsw.server.model.card.Card;
import it.polimi.ingsw.server.model.card.ResourceCard;
import it.polimi.ingsw.util.customexceptions.EmptyDeckException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ResourceCardDeckTest {

    @Test
    void generateDeck() {
        ResourceCardDeck resourcecarddecktest = new ResourceCardDeck();
        assertEquals(38, resourcecarddecktest.cards.size());
    }
    @Test
    void getLeftRevealedCard() {
        ResourceCardDeck resourcecarddecktest = new ResourceCardDeck();
        Card leftrevealedcard = resourcecarddecktest.getLeftRevealedCard();
        assertEquals(leftrevealedcard.getClass(), ResourceCard.class);
    }

    @Test
    void getRightRevealedCard() {
        ResourceCardDeck resourcecarddecktest = new ResourceCardDeck();
        Card rightrevealedcard = resourcecarddecktest.getRightRevealedCard();
        assertEquals(rightrevealedcard.getClass(), ResourceCard.class);
    }

    @Test
    void directDraw() {
        ResourceCardDeck resourcecarddecktest = new ResourceCardDeck();
        Card resourcecard;
        try {
            resourcecard=resourcecarddecktest.directDraw();
        } catch (EmptyDeckException e) {
            throw new RuntimeException(e);
        }
        assertEquals(resourcecard.getClass(),ResourceCard.class);
    }

    @Test
    void directDrawEmptyDeck() {
        ResourceCardDeck resourcecarddecktest = new ResourceCardDeck();

        for (int i=0; i<38; i++) {
            try {
                resourcecarddecktest.directDraw();
            } catch (EmptyDeckException e) {
                throw new RuntimeException(e);
            }
        }
        assertThrows(EmptyDeckException.class,()-> resourcecarddecktest.directDraw());
    }
}