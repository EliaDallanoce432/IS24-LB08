package it.polimi.ingsw.server.model.deck;

import it.polimi.ingsw.server.model.card.Card;
import it.polimi.ingsw.server.model.card.GoldCard;
import it.polimi.ingsw.util.customexceptions.EmptyDeckException;

import java.util.Collections;

public class GoldCardDeck extends DeckWithRevealedCards {
    public GoldCardDeck(){
        for (int i=41; i<81; i++) {
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
        return new GoldCard(cardId);
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
