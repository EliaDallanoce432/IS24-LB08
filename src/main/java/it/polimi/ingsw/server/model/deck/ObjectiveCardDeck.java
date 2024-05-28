package it.polimi.ingsw.server.model.deck;

import it.polimi.ingsw.server.model.card.ObjectiveCard;
import java.util.ArrayList;
import java.util.Collections;

/**
 * This class represents the objective card deck.
 */
public class ObjectiveCardDeck extends Deck {
    public ObjectiveCardDeck() {
        cards = new ArrayList<>();
        for (int i = 87; i < 103; i++) {
            cards.add(new ObjectiveCard(i));
        }
        Collections.shuffle(cards);
    }
}
