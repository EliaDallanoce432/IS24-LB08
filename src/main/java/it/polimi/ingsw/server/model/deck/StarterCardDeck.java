package it.polimi.ingsw.server.model.deck;

import it.polimi.ingsw.server.model.card.Card;
import it.polimi.ingsw.server.model.card.StarterCard;
import it.polimi.ingsw.util.customexceptions.EmptyDeckException;

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
     *
     * @return Card
     */
    public Card directDraw() throws EmptyDeckException {
        int cardId;
        if(cards.isEmpty()) {
            throw new EmptyDeckException();
        }
        cardId = cards.getFirst();
        cards.removeFirst();
        return new StarterCard(cardId);
    }
}
