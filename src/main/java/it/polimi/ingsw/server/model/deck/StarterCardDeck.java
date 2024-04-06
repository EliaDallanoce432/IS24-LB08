package it.polimi.ingsw.server.model.deck;

import it.polimi.ingsw.server.model.card.StarterCard;
import java.util.Collections;

public class StarterCardDeck extends Deck {
    public StarterCardDeck() {
        for (int i=81; i<87; i++) {
            cards.add(new StarterCard(i));
        }
        Collections.shuffle(cards);
    }
}
