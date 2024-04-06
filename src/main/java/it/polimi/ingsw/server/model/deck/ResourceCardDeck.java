package it.polimi.ingsw.server.model.deck;

import it.polimi.ingsw.server.model.card.ResourceCard;
import it.polimi.ingsw.util.customexceptions.EmptyDeckException;

import java.util.Collections;

public class ResourceCardDeck extends DeckWithRevealedCards {
    public ResourceCardDeck() {
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
