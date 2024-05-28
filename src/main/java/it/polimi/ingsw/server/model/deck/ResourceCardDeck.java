package it.polimi.ingsw.server.model.deck;

import it.polimi.ingsw.server.model.card.ResourceCard;
import it.polimi.ingsw.util.customexceptions.EmptyDeckException;
import java.util.ArrayList;
import java.util.Collections;

/**
 * This class represents the resource card deck.
 */
public class ResourceCardDeck extends DeckWithRevealedCards {
    public ResourceCardDeck() {
        cards = new ArrayList<>();
        for (int i=1; i<41; i++) {
            cards.add(new ResourceCard(i));
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
