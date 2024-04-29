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

    /**
     * returns the ids of the drawable cards
     * @return cardsId
     */
    public ArrayList<Integer> getDrawableCardsId() {
        ArrayList<Integer> cardsId= new ArrayList<>();
        cardsId.add(cards.getFirst().getId()) ;
        cardsId.add(leftRevealedCard.getId()) ;
        cardsId.add(rightRevealedCard.getId()) ;
        return cardsId;
    }
}
