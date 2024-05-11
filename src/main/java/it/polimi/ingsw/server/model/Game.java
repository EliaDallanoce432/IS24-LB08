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
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

public class Game {
    public int numberOfPlayers;
    private GameState gameState;
    public int turnCounter;

    public ObjectiveCardDeck objectiveCardDeck;
    public ResourceCardDeck resourceCardDeck;
    public GoldCardDeck goldCardDeck;
    public StarterCardDeck starterCardDeck;
    public ArrayList<ObjectiveCard> commonObjectives;
    public HashMap<String, Player> players;

    public GameObserver gameObserver;
    public ArrayList<Color> availableTokens;

    public Game(int numberOfPlayers, GameObserver gameObserver) {
        this.numberOfPlayers = numberOfPlayers;
        players = new HashMap<>();
        objectiveCardDeck = new ObjectiveCardDeck();
        resourceCardDeck = new ResourceCardDeck(gameObserver);
        goldCardDeck = new GoldCardDeck(gameObserver);
        starterCardDeck = new StarterCardDeck();
        commonObjectives = new ArrayList<>();
        availableTokens = new ArrayList<>(Arrays.asList(Color.red,Color.yellow,Color.green,Color.blue));
        commonObjectives.add((ObjectiveCard) objectiveCardDeck.drawLeftRevealedCard());
        commonObjectives.add((ObjectiveCard) objectiveCardDeck.drawRightRevealedCard());
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

}
