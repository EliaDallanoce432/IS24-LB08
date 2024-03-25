package it.polimi.ingsw.server.model;

import it.polimi.ingsw.util.supportclasses.Color;

public class Player {
    private String username;
    private Color token;
    private GameField gameField;
    private int score;
    private PlaceableCard[] hand;
    private ObjectiveCard secretObjective;

    public void addToHand(Card card) {
        
    }
}
