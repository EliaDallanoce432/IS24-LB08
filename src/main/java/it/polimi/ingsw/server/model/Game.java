package it.polimi.ingsw.server.model;

import it.polimi.ingsw.util.supportclasses.Resource;

public class Game {
    public ObjectiveCardDeck objectiveCardDeck;
    public ResourceCardDeck resourceCardDeck;
    public GoldCardDeck goldCardDeck;
    public StarterCardDeck starterCardDeck;
    public Player[] players;
    public GameField[] gameFields;

    public Game(int numberOfPlayers) {
        players = new Player[numberOfPlayers];
        gameFields = new GameField[numberOfPlayers];
        objectiveCardDeck = new ObjectiveCardDeck();
        resourceCardDeck = new ResourceCardDeck();
        goldCardDeck = new GoldCardDeck();
        starterCardDeck = new StarterCardDeck();
    }
}
