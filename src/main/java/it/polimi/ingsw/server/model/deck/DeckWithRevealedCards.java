package it.polimi.ingsw.server.model.deck;

import it.polimi.ingsw.server.model.card.Card;
import it.polimi.ingsw.util.customexceptions.EmptyDeckException;

import java.util.ArrayList;

public abstract class DeckWithRevealedCards extends Deck{
    protected Card leftRevealedCard;
    protected Card rightRevealedCard;

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

    public int getLeftRevealedCardID() {
        if (leftRevealedCard == null) { return 0;}
        return leftRevealedCard.getId();
    }

    public int getRightRevealedCardID() {
        if (rightRevealedCard == null) { return 0;}
        return rightRevealedCard.getId();
    }

    /**
     * returns the cards that are visible in the deck (top card, left revealed card, right revealed card)
     * @return Arraylist of the visible cards
     */
    public ArrayList<Card> getVisibleCards() {
        ArrayList<Card> cardsId= new ArrayList<>();
        cardsId.add(cards.getFirst()) ;
        cardsId.add(leftRevealedCard) ;
        cardsId.add(rightRevealedCard) ;
        return cardsId;
    }
}
