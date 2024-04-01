package it.polimi.ingsw.server.model.deck;

import it.polimi.ingsw.server.model.card.Card;

public abstract class DeckWithRevealedCards extends Deck{
    protected Card leftRevealedCard;
    protected Card rightRevealedCard;

    /**
     * returns the left revealed card on the table
     * @return Card
     */
    public abstract Card getLeftRevealedCard();

    /**
     * returns the right revealed card on the table
     * @return Card
     */
    public abstract Card getRightRevealedCard() ;
}
