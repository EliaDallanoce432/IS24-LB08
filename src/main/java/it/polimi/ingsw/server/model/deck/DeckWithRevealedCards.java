package it.polimi.ingsw.server.model.deck;

import it.polimi.ingsw.server.model.card.Card;
import it.polimi.ingsw.util.customexceptions.EmptyDeckException;

import java.util.ArrayList;

public abstract class DeckWithRevealedCards extends Deck{
    protected Card leftRevealedCard;
    protected Card rightRevealedCard;

    public int getLeftRevealedCardID() {
        if (leftRevealedCard == null) { return 0;}
        return leftRevealedCard.getId();
    }

    public int getRightRevealedCardID() {
        if (rightRevealedCard == null) { return 0;}
        return rightRevealedCard.getId();
    }

    /**
     * returns the left revealed card on the table
     * @return Card
     */
    public Card drawLeftRevealedCard() {
        Card selectedCard;
        selectedCard = leftRevealedCard;
        try {
            leftRevealedCard = this.directDraw();
        } catch (EmptyDeckException e) {
            leftRevealedCard = null; //no cards left
        }
        return selectedCard;
    }

    /**
     * returns the right revealed card on the table
     * @return Card
     */
    public Card drawRightRevealedCard() {
        Card selectedCard;
        selectedCard = rightRevealedCard;
        try {
            rightRevealedCard = this.directDraw();
        } catch (EmptyDeckException e) {
            rightRevealedCard = null; //no cards left
        }
        return selectedCard;
    }
}
