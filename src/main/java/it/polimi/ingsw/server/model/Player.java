package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.model.card.ObjectiveCard;
import it.polimi.ingsw.server.model.card.PlaceableCard;
import it.polimi.ingsw.util.supportclasses.Color;

import java.util.ArrayList;

public class Player {
    private String username;
    private Color token;
    private GameField gameField;
    private int score;
    private ArrayList<PlaceableCard> hand;
    private ObjectiveCard secretObjective;

    public Player(String username, Color token, GameField gameField) {
        this.username = username;
        this.token = token;
        this.gameField = gameField;
        this.score = 0;
        this.hand = new ArrayList<PlaceableCard>();
        this.secretObjective = null;
    }

    public String getUsername() {
        return username;
    }

    public Color getToken() {
        return token;
    }

    public GameField getGameField() {
        return gameField;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int newScore) {
        this.score = newScore;
    }

    public void setSecretObjective(ObjectiveCard objectiveCard) {
        if(this.secretObjective != null) return;
        this.secretObjective = objectiveCard;
    }


    public ObjectiveCard getSecretObjective() {
        return secretObjective;
    }


    /**
     * adds the drawn card to the player's hand
     * @param card card to be added
     */


    public void addToHand(PlaceableCard card) {

        hand.add(card);

    }

    /**
     * takes the card from the player's hand
     * @param card card to be taken
     */

    public PlaceableCard removeFromHand(PlaceableCard card) {

        return hand.remove(hand.indexOf(card));

    }
}
