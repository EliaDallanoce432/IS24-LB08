package it.polimi.ingsw.server.model.deck;

import it.polimi.ingsw.server.model.card.Card;
import it.polimi.ingsw.server.model.card.ResourceCard;
import it.polimi.ingsw.util.customexceptions.EmptyDeckException;

import java.util.Collections;

public class ResourceCardDeck extends DeckWithRevealedCards {
    public ResourceCardDeck() {
        for (int i=1; i<41; i++) {
            cards.add(i);
        }
        Collections.shuffle(cards);
        try {
            leftRevealedCard = this.directDraw();
            rightRevealedCard = this.directDraw();
        } catch (EmptyDeckException e) {
            throw new RuntimeException(e);
        }
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
        return new ResourceCard(cardId);
    }

    /**
     * returns the left revealed card on the table
     *
     * @return Card
     */
    public Card getLeftRevealedCard() {
        Card selectedCard;
        selectedCard = leftRevealedCard;
        try {
            leftRevealedCard = this.directDraw();
        } catch (EmptyDeckException e) {
            return null; //no cards left
        }
        return selectedCard;
    }

    /**
     * returns the right revealed card on the table
     *
     * @return Card
     */
    public Card getRightRevealedCard() {
        Card selectedCard;
        selectedCard = rightRevealedCard;
        try {
            rightRevealedCard = this.directDraw();
        } catch (EmptyDeckException e) {
            return null; //no cards left
        }
        return selectedCard;
    }
}
