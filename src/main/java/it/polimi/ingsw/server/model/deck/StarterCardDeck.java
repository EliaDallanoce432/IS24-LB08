package it.polimi.ingsw.server.model.deck;

import it.polimi.ingsw.server.model.card.StarterCard;

import java.util.ArrayList;
import java.util.Collections;

/**
 * This class represents Starter card's deck
 */
public class StarterCardDeck extends Deck {
    public StarterCardDeck() {
        cards = new ArrayList<>();
        for (int i=81; i<87; i++) {
            cards.add(new StarterCard(i));
        }
        Collections.shuffle(cards);
    }
}
