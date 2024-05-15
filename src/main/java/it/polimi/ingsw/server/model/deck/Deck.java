package it.polimi.ingsw.server.model.deck;

import it.polimi.ingsw.server.model.card.Card;
import it.polimi.ingsw.util.customexceptions.EmptyDeckException;
import java.util.ArrayList;

/**
 * This class represents a general Deck
 */
public abstract class Deck {
    protected ArrayList<Card> cards;

    /**
     * get first card from the deck
     * @return Card drawn
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
     * get top card id from the deck
     * @return top card id
     */
    public int getTopCardID() {
        if(cards.isEmpty()) {return 0;}
        return cards.getFirst().getId();
    }

    /**
     * check if deck is empty
     * @return true if deck is empty
     */
    public boolean isEmpty() {
        return cards.isEmpty();
    }
}
