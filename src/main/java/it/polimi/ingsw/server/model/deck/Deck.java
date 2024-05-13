package it.polimi.ingsw.server.model.deck;

import it.polimi.ingsw.server.model.card.Card;
import it.polimi.ingsw.util.customexceptions.EmptyDeckException;
import java.util.ArrayList;

public abstract class Deck {
    protected ArrayList<Card> cards;

    /**
     * returns a card from the deck
     * @return Card
     */
    public Card directDraw() throws EmptyDeckException {
        Card drawnCard;
        if(cards.isEmpty()) {
            throw new EmptyDeckException();
        }
        drawnCard = cards.getFirst();
        cards.removeFirst();

        return drawnCard;
    }

    /**
     * returns top card id from the deck
     * @return int
     */
    public int getTopCardID() {
        if(cards.isEmpty()) {return 0;}
        return cards.getFirst().getId();
    }

    /**
     * check if deck is empty
     * @return boolean
     */
    public boolean isEmpty() {
        return cards.isEmpty();
    }
}
