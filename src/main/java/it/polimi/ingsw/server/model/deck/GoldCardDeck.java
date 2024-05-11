package it.polimi.ingsw.server.model.deck;

import it.polimi.ingsw.server.controller.GameObserver;
import it.polimi.ingsw.server.model.card.Card;
import it.polimi.ingsw.server.model.card.GoldCard;
import it.polimi.ingsw.util.customexceptions.EmptyDeckException;

import java.util.ArrayList;
import java.util.Collections;

public class GoldCardDeck extends DeckWithRevealedCards {
    GameObserver gameObserver;
    public GoldCardDeck(GameObserver gameObserver){
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
        this.gameObserver = gameObserver;
    }

    @Override
    public Card directDraw() throws EmptyDeckException {
        Card tempGoldCard = super.directDraw();
        //aggiunto if statement per i test sui deck
        if (gameObserver!=null) gameObserver.notifyLastRound();
        return tempGoldCard;
    }
}
