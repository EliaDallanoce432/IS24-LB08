package it.polimi.ingsw.server.model.deck;

import it.polimi.ingsw.server.model.card.Card;
import it.polimi.ingsw.util.customexceptions.EmptyDeckException;
import java.util.ArrayList;

/**
 * This abstract class represents a general Deck of cards.
 */
public abstract class Deck {
    protected ArrayList<Card> cards;

    /**
     * Draws the top card from the deck.
     * @throws EmptyDeckException If the deck is empty when trying to draw a card.
     * @return The drawn card.
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
     * Gets the ID of the card that is currently at the top of the deck.
     * @return The top card ID.
     */
    public int getTopCardID() {
        if(cards.isEmpty()) {return 0;}
        return cards.getFirst().getId();
    }

    /**
     * Checks if deck is empty.
     * @return true if deck is empty, false otherwise.
     */
    public boolean isEmpty() {
        return cards.isEmpty();
    }
}
