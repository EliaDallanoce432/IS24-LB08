package it.polimi.ingsw.server.model.deck;

import it.polimi.ingsw.server.model.deck.Deck;
import it.polimi.ingsw.util.customexeptions.EmptyDeckException;

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
     * @return Card
     */
    public int directDraw() throws EmptyDeckException {
        int card;
        if(cards.isEmpty()) {
            throw new EmptyDeckException();
        }
        card = cards.getFirst();
        cards.removeFirst();
        return card;
    }

    /**
     * returns the left revealed card on the table
     * @return Card
     */
    public int getLeftRevealedCard() {
        int selectedCard;
        selectedCard = leftRevealedCard;
        try {
            leftRevealedCard = this.directDraw();
        } catch (EmptyDeckException e) {
            return 0; //no cards left
        }
        return selectedCard;
    }

    /**
     * returns the right revealed card on the table
     * @return Card
     */
    public int getRightRevealedCard() {
        int selectedCard;
        selectedCard = rightRevealedCard;
        try {
            rightRevealedCard = this.directDraw();
        } catch (EmptyDeckException e) {
            return 0; //no cards left
        }
        return selectedCard;
    }
}
