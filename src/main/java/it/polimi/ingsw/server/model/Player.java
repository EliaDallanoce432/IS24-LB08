package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.model.card.*;
import it.polimi.ingsw.util.customexceptions.*;
import it.polimi.ingsw.util.supportclasses.Color;

import java.util.ArrayList;

import static it.polimi.ingsw.util.supportclasses.Constants.MAX_HAND_SIZE;

public class Player {
    private final String username;
    private final Color token;
    private int score;
    private Game game;
    private final GameField gamefield;
    private final ArrayList<PlaceableCard> hand;
    private StarterCard starterCard;
    private ObjectiveCard secretObjective;
    private ObjectiveCard[] drawnObjectiveCards;
    private boolean isReady;
    private boolean starterCardOrientationSelected;
    private int numOfCompletedObjectiveCards;
    //TODO spostare qui il booleano already placed dal clienthandler
    public Player(String username, Color token) {
        this.game = Game.getInstance();
        this.username = username;
        this.token = token;
        this.score = 0;
        this.gamefield = new GameField(this);
        this.hand = new ArrayList<>();
        initializeHand();
        this.starterCard = null;
        this.secretObjective = null;
        this.drawnObjectiveCards = new ObjectiveCard[2];
        this.isReady = false;
        this.starterCardOrientationSelected = false;
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

    public ObjectiveCard[] getDrawnObjectiveCards() {
        return drawnObjectiveCards;
    }

    public void setDrawnObjectiveCards(ObjectiveCard[] drawnObjectiveCards) {
        this.drawnObjectiveCards = drawnObjectiveCards;
    }

    public boolean isReady() {
        return isReady;
    }

    public void setReady(boolean ready) {
        isReady = ready;
        game.gameObserver.notifyReady();
    }
    public boolean isStarterCardOrientationSelected() {
        return starterCardOrientationSelected;
    }

    public void setStarterCardOrientationSelected(boolean starterCardOrientationSelected) {
        this.starterCardOrientationSelected = starterCardOrientationSelected;
        game.gameObserver.notifyStarterCardAndSecretObjetiveSelected();
    }

    public ArrayList<PlaceableCard> getHand() {
        return hand;
    }

    /**
     * this method adds to the player's hand the first 3 cards of his game
     */
    private void initializeHand() {
        hand.clear();
        try {
            addToHand((ResourceCard) game.resourceCardDeck.directDraw());
            addToHand((ResourceCard) game.resourceCardDeck.directDraw());
            addToHand((GoldCard) game.goldCardDeck.directDraw());
        } catch (FullHandException | EmptyDeckException ignored) {
        }
    }

    public void setScore(int newScore) {
        this.score = newScore;
    }

    public StarterCard getStarterCard() {
        return starterCard;
    }

    public void setStarterCard(StarterCard starterCard) {
        this.starterCard = starterCard;
    }

    public void setSecretObjective(ObjectiveCard objectiveCard) {
        if(this.secretObjective != null) return;
        this.secretObjective = objectiveCard;
        game.gameObserver.notifyStarterCardAndSecretObjetiveSelected();
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

    /**
     * this method calculates the points given by the secrete objective card and the 2 common objective
     */
    public void calculateFinalScore() {
        //TODO conteggio obiettivi realizzati
        secretObjective.getEarnedPoints(getGamefield());
        game.commonObjectives.get(0).getEarnedPoints(getGamefield());
        game.commonObjectives.get(1).getEarnedPoints(getGamefield());
    }
}
