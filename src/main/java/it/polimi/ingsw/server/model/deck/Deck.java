package it.polimi.ingsw.server.model.deck;

import it.polimi.ingsw.util.customexeptions.EmptyDeckException;
import java.util.ArrayList;

public abstract class Deck {
    protected ArrayList<Integer> cards;

    /**
     * returns a card from the deck
     * @return Card
     */
    public abstract int directDraw() throws EmptyDeckException;

}
