package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.model.deck.GoldCardDeck;
import it.polimi.ingsw.server.model.deck.ObjectiveCardDeck;
import it.polimi.ingsw.server.model.deck.ResourceCardDeck;
import it.polimi.ingsw.server.model.deck.StarterCardDeck;
import it.polimi.ingsw.util.supportclasses.GameState;

public class Game {
    public ObjectiveCardDeck objectiveCardDeck;
    public ResourceCardDeck resourceCardDeck;
    public GoldCardDeck goldCardDeck;
    public StarterCardDeck starterCardDeck;
    public Player[] players;
    public GameState gamestate;

    public Game(int numberOfPlayers) {
        players = new Player[numberOfPlayers];
        objectiveCardDeck = new ObjectiveCardDeck();
        resourceCardDeck = new ResourceCardDeck();
        goldCardDeck = new GoldCardDeck();
        starterCardDeck = new StarterCardDeck();
        gamestate = GameState.waitingForPlayers;
    }

    public void changeGameState () {

    }
}
