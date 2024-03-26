package it.polimi.ingsw.server.model;

import it.polimi.ingsw.util.supportclasses.Color;

import java.util.ArrayList;

public class Player {
    private String username;
    private Color token;
    private GameField gameField;
    private int score;
    private ArrayList<PlaceableCard> hand;
    private ObjectiveCard secretObjective;

    /**
     * adds the drawn card to the player's hand
     * @param card card to be added
     */
    public void addToHand(Card card) {
        
    }
}
