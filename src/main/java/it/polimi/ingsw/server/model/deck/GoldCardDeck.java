package it.polimi.ingsw.server.model.deck;

import it.polimi.ingsw.server.model.card.GoldCard;
import it.polimi.ingsw.util.customexceptions.EmptyDeckException;

import java.util.ArrayList;
import java.util.Collections;

/**
 * This class represents Gold card's deck
 */
public class GoldCardDeck extends DeckWithRevealedCards {
    public GoldCardDeck(){
        cards = new ArrayList<>();
        for (int i=41; i<81; i++) {
            cards.add(new GoldCard(i));
        }
        Collections.shuffle(cards);
        try {
            leftRevealedCard = this.directDraw();
            rightRevealedCard = this.directDraw();
        } catch (EmptyDeckException e) {
            throw new RuntimeException(e);
        }
    }
}
