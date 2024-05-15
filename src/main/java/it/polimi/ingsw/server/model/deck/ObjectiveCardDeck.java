package it.polimi.ingsw.server.model.deck;

import it.polimi.ingsw.server.model.card.ObjectiveCard;
import it.polimi.ingsw.util.customexceptions.EmptyDeckException;

import java.util.ArrayList;
import java.util.Collections;

/**
 * This class represents Objective card's deck
 */
public class ObjectiveCardDeck extends DeckWithRevealedCards {
    public ObjectiveCardDeck() {
        cards = new ArrayList<>();
        for (int i = 87; i < 103; i++) {
            cards.add(new ObjectiveCard(i));
        }
        Collections.shuffle(cards);

        try {
            leftRevealedCard = this.directDraw();
            rightRevealedCard = this.directDraw();
        } catch (EmptyDeckException e) {
            throw new RuntimeException(e);
        }

    }
}
