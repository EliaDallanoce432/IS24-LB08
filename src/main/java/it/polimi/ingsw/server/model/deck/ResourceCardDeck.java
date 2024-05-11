package it.polimi.ingsw.server.model.deck;

import it.polimi.ingsw.server.controller.GameObserver;
import it.polimi.ingsw.server.model.card.Card;
import it.polimi.ingsw.server.model.card.ResourceCard;
import it.polimi.ingsw.util.customexceptions.EmptyDeckException;

import java.util.ArrayList;
import java.util.Collections;

public class ResourceCardDeck extends DeckWithRevealedCards {

    GameObserver gameObserver;
    public ResourceCardDeck(GameObserver gameObserver) {
        cards = new ArrayList<>();
        for (int i=1; i<41; i++) {
            cards.add(new ResourceCard(i));
        }
        Collections.shuffle(cards);
        try {
            leftRevealedCard = this.directDraw();
            rightRevealedCard = this.directDraw();
        } catch (EmptyDeckException e) {
            throw new RuntimeException(e);
        }

        this.gameObserver = gameObserver;
    }

    @Override
    public Card directDraw() throws EmptyDeckException {
        Card tempCard = super.directDraw();
        //aggiunto if statement per i test sui deck
        if (gameObserver != null) gameObserver.notifyLastRound();
        return tempCard;
    }
}
