package it.polimi.ingsw.server.model.deck;

import it.polimi.ingsw.server.model.card.Card;
import it.polimi.ingsw.server.model.card.ObjectiveCard;
import it.polimi.ingsw.util.customexceptions.EmptyDeckException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ObjectiveCardDeckTest {

    @Test
    void generateDeck() {
        ObjectiveCardDeck objectivedecktest = new ObjectiveCardDeck();
        assertEquals(14, objectivedecktest.cards.size());
    }
    @Test
    void getLeftRevealedCard() {
        ObjectiveCardDeck objectivedecktest = new ObjectiveCardDeck();
        Card leftrevealedcard = objectivedecktest.getLeftRevealedCard();
        assertEquals(leftrevealedcard.getClass(), ObjectiveCard.class);
    }

    @Test
    void getRightRevealedCard() {
        ObjectiveCardDeck objectivedecktest = new ObjectiveCardDeck();
        Card rightrevealedcard = objectivedecktest.getRightRevealedCard();
        assertEquals(rightrevealedcard.getClass(), ObjectiveCard.class);
    }

    @Test
    void directDraw() {
        ObjectiveCardDeck objectivedecktest = new ObjectiveCardDeck();
        Card objectivecard;
        try {
            objectivecard = objectivedecktest.directDraw();
        } catch (EmptyDeckException e) {
            throw new RuntimeException(e);
        }
        assertEquals(objectivecard.getClass(), ObjectiveCard.class);
    }

    @Test
    void directDrawEmptyDeck () {
        ObjectiveCardDeck objectivedecktest = new ObjectiveCardDeck();
        for(int i=0; i < 14; i++) {
            try {
                objectivedecktest.directDraw();
            } catch (EmptyDeckException e) {
                throw new RuntimeException(e);
            }
        }
        assertThrows(EmptyDeckException.class, ()-> objectivedecktest.directDraw());
    }
}