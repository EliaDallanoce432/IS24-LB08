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
        assertEquals(16, objectivedecktest.cards.size());
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
        for(int i=0; i < 16; i++) {
            try {
                objectivedecktest.directDraw();
            } catch (EmptyDeckException e) {
                throw new RuntimeException(e);
            }
        }
        assertThrows(EmptyDeckException.class, objectivedecktest::directDraw);
    }
}