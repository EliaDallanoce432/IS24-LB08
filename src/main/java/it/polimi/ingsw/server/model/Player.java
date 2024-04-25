package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.model.card.ObjectiveCard;
import it.polimi.ingsw.server.model.card.PlaceableCard;
import it.polimi.ingsw.server.model.card.StarterCard;
import it.polimi.ingsw.util.customexceptions.*;
import it.polimi.ingsw.util.supportclasses.Color;

import java.util.ArrayList;

import static it.polimi.ingsw.util.supportclasses.Constants.MAX_HAND_SIZE;

public class Player {
    private final String username;
    private final Color token;
    private int score;
    private GameField gamefield;
    private ArrayList<PlaceableCard> hand;
    private ObjectiveCard secretObjective;

    public Player(String username, Color token) {
        this.username = username;
        this.token = token;
        this.score = 0;
        this.gamefield = new GameField(this);
        this.hand = new ArrayList<>();
        this.secretObjective = null;
    }

    public String getUsername() {
        return username;
    }

    public GameField getGamefield() {
        return gamefield;
    }

    public Color getToken() {
        return token;
    }

    public int getScore() {
        return score;
    }

    public ArrayList<PlaceableCard> getHand() {
        return hand;
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
    public void addToHand(PlaceableCard card) throws FullHandException {

        if (this.hand.size() >= MAX_HAND_SIZE) throw new FullHandException();
        else hand.add(card);

    }

    /**
     * takes the card from the player's hand
     * @param card card to be taken
     */
    public PlaceableCard removeFromHand(PlaceableCard card) throws CardNotInHandException {

        if(!hand.remove(card)) throw new CardNotInHandException();
        else return card;
    }

    public void place(StarterCard card, boolean facingUp){
        gamefield.place(card, facingUp);
    }

    public void place(PlaceableCard card, boolean facingUp, int x, int y) throws CannotPlaceCardException {
        gamefield.place(card,facingUp,x,y);
    }
}
