package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.controller.GameObserver;
import it.polimi.ingsw.server.model.card.ObjectiveCard;
import it.polimi.ingsw.server.model.card.ResourceCard;
import it.polimi.ingsw.server.model.deck.GoldCardDeck;
import it.polimi.ingsw.server.model.deck.ObjectiveCardDeck;
import it.polimi.ingsw.server.model.deck.ResourceCardDeck;
import it.polimi.ingsw.server.model.deck.StarterCardDeck;
import it.polimi.ingsw.util.supportclasses.Color;
import it.polimi.ingsw.util.supportclasses.GameState;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

public class Game {
    private static Game instance;
    public int numberOfPlayers;
    public ObjectiveCardDeck objectiveCardDeck;
    public ResourceCardDeck resourceCardDeck;
    public GoldCardDeck goldCardDeck;
    public StarterCardDeck starterCardDeck;
    public HashMap<String, Player> players;
    public GameObserver gameObserver;
    public GameState gameState;
    public ArrayList<ObjectiveCard> commonObjectives;
    public ArrayList<Color> availableTokens;

    public static Game getInstance(int numberOfPlayers, GameObserver gameObserver) {
        if(instance == null) { instance = new Game(numberOfPlayers, gameObserver); }
        return instance;
    }

    public static Game getInstance() {
        return instance;
    }

    public Game(int numberOfPlayers, GameObserver gameObserver) {
        this.numberOfPlayers = numberOfPlayers;
        players = new HashMap<>();
        objectiveCardDeck = new ObjectiveCardDeck();
        resourceCardDeck = new ResourceCardDeck();
        goldCardDeck = new GoldCardDeck();
        starterCardDeck = new StarterCardDeck();
        commonObjectives = new ArrayList<>();
        availableTokens = new ArrayList<>(Arrays.asList(Color.red,Color.yellow,Color.green,Color.blue));
        commonObjectives.add((ObjectiveCard) objectiveCardDeck.getLeftRevealedCard());
        commonObjectives.add((ObjectiveCard) objectiveCardDeck.getRightRevealedCard());
        gameState = GameState.waitingForPlayers;
        this.gameObserver= gameObserver;
    }
    public GameState getGameState() {
        return gameState;
    }

    public Color getRandomToken() {
        Collections.shuffle(availableTokens);
        return availableTokens.removeFirst();
    }

    public void reinsertToken(Color token) {
        availableTokens.add(token);
    }

    public void setGameState(GameState gamestate) {
        this.gameState = gamestate;
    }

    public ArrayList<Player> getPlayers() {
        return new ArrayList<>(players.values());
    }

//    public int getNumberOfPlayers() {
//        return numberOfPlayers;
//    }

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
