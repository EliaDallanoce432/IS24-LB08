package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.controller.GameObserver;
import it.polimi.ingsw.server.model.card.ObjectiveCard;
import it.polimi.ingsw.server.model.deck.GoldCardDeck;
import it.polimi.ingsw.server.model.deck.ObjectiveCardDeck;
import it.polimi.ingsw.server.model.deck.ResourceCardDeck;
import it.polimi.ingsw.server.model.deck.StarterCardDeck;
import it.polimi.ingsw.util.customexceptions.EmptyDeckException;
import it.polimi.ingsw.util.supportclasses.Token;
import it.polimi.ingsw.util.supportclasses.GameState;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

/**
 * This class represents the core game model, managing the game state, decks, players, and overall game logic.
 *  It interacts with other components to receive player actions and update the game state accordingly.
 */
public class Game {
    private int numberOfPlayers;
    private GameState gameState;
    private int turnCounter;

    private final ObjectiveCardDeck objectiveCardDeck;
    private final ResourceCardDeck resourceCardDeck;
    private final GoldCardDeck goldCardDeck;
    private final StarterCardDeck starterCardDeck;
    private final ArrayList<ObjectiveCard> commonObjectives;
    private final HashMap<String, Player> players;

    private GameObserver gameObserver;
    private final ArrayList<Token> availableTokens;

    public Game(int numberOfPlayers, GameObserver gameObserver) {
        this.setNumberOfPlayers(numberOfPlayers);
        players = new HashMap<>();
        objectiveCardDeck = new ObjectiveCardDeck();
        resourceCardDeck = new ResourceCardDeck();
        goldCardDeck = new GoldCardDeck();
        starterCardDeck = new StarterCardDeck();
        commonObjectives = new ArrayList<>();
        availableTokens = new ArrayList<>(Arrays.asList(Token.red, Token.yellow, Token.green, Token.blue));
        try {
            commonObjectives.add((ObjectiveCard) objectiveCardDeck.directDraw());
            commonObjectives.add((ObjectiveCard) objectiveCardDeck.directDraw());
        } catch (EmptyDeckException ignored) {
        }
        gameState = GameState.waitingForPlayers;
        this.setGameObserver(gameObserver);
    }
    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gamestate) {this.gameState = gamestate;}

    public Token getRandomToken() {
        Collections.shuffle(getAvailableTokens());
        return getAvailableTokens().removeFirst();
    }

    public HashMap<String, Player> getPlayersHashMap() { return players;}

    public ArrayList<Player> getPlayers() {
        return new ArrayList<>(players.values());
    }

    /**
    * makes the token available again
    * @param token of the player
     */
    public void reinsertToken(Token token) {
        getAvailableTokens().add(token);
    }


    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public void setNumberOfPlayers(int numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
    }

    public int getTurnCounter() {
        return turnCounter;
    }

    public void setTurnCounter(int turnCounter) {
        this.turnCounter = turnCounter;
    }

    public ObjectiveCardDeck getObjectiveCardDeck() {
        return objectiveCardDeck;
    }

    public ResourceCardDeck getResourceCardDeck() {
        return resourceCardDeck;
    }

    public GoldCardDeck getGoldCardDeck() {
        return goldCardDeck;
    }

    public StarterCardDeck getStarterCardDeck() {
        return starterCardDeck;
    }

    public ArrayList<ObjectiveCard> getCommonObjectives() {
        return commonObjectives;
    }

    public GameObserver getGameObserver() {
        return gameObserver;
    }

    public void setGameObserver(GameObserver gameObserver) {
        this.gameObserver = gameObserver;
    }

    public ArrayList<Token> getAvailableTokens() {
        return availableTokens;
    }

}
