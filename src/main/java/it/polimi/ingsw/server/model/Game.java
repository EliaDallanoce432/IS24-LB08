package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.controller.GameObserver;
import it.polimi.ingsw.server.model.card.ObjectiveCard;
import it.polimi.ingsw.server.model.deck.GoldCardDeck;
import it.polimi.ingsw.server.model.deck.ObjectiveCardDeck;
import it.polimi.ingsw.server.model.deck.ResourceCardDeck;
import it.polimi.ingsw.server.model.deck.StarterCardDeck;
import it.polimi.ingsw.util.supportclasses.Color;
import it.polimi.ingsw.util.supportclasses.GameState;

import java.util.ArrayList;
import java.util.HashMap;

public class Game {
    public ObjectiveCardDeck objectiveCardDeck;
    public ResourceCardDeck resourceCardDeck;
    public GoldCardDeck goldCardDeck;
    public StarterCardDeck starterCardDeck;
    public HashMap<Color, Player> players;
    public GameObserver gameObserver;
    public GameState gameState;
    public ArrayList<ObjectiveCard> commonObjectives;

    public Game(int numberOfPlayers, GameObserver gameObserver) {
        players = new HashMap<>();
        objectiveCardDeck = new ObjectiveCardDeck();
        resourceCardDeck = new ResourceCardDeck();
        goldCardDeck = new GoldCardDeck();
        starterCardDeck = new StarterCardDeck();
        commonObjectives = new ArrayList<>();
        commonObjectives.add((ObjectiveCard) objectiveCardDeck.getLeftRevealedCard());
        commonObjectives.add((ObjectiveCard) objectiveCardDeck.getRightRevealedCard());
        gameState = GameState.waitingForPlayers;
        this.gameObserver= gameObserver;
    }
    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gamestate) {
        this.gameState = gamestate;
    }

    public ArrayList<Player> getPlayers() {
        return new ArrayList<>(players.values());
    }

//    public void starterCardsSelection()
//    {
//        for (int i = 0; i < players.length; i++) {
//            try {
//                drawnStarterCards.add((StarterCard) game.starterCardDeck.directDraw());
//            } catch (EmptyDeckException ignored) {
//                System.out.println(gameName + " has no drawn starter cards");
//            }
//        }
//    }

}
