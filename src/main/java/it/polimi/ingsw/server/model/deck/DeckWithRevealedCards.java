package it.polimi.ingsw.server.model.deck;

public abstract class DeckWithRevealedCards extends Deck{
    protected int leftRevealedCard;
    protected int rightRevealedCard;

    /**
     * returns the left revealed card on the table
     * @return Card
     */
    public abstract int getLeftRevealedCard();

    /**
     * returns the right revealed card on the table
     * @return Card
     */
    public abstract int getRightRevealedCard() ;
}
