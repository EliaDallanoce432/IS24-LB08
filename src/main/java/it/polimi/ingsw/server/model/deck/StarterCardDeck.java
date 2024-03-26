package it.polimi.ingsw.server.model.deck;

import it.polimi.ingsw.server.model.deck.Deck;
import it.polimi.ingsw.util.customexeptions.EmptyDeckException;

import java.util.Collections;

public class StarterCardDeck extends Deck {
    public StarterCardDeck() {
        for (int i=81; i<87; i++) {
            cards.add(i);
        }
        Collections.shuffle(cards);
    }

    /**
     * returns a card from the deck
     * @return Card
     */
    public int directDraw() throws EmptyDeckException {
        int card;
        if(cards.isEmpty()) {
            throw new EmptyDeckException();
        }
        card = cards.getFirst();
        cards.removeFirst();
        return card;
    }
}
