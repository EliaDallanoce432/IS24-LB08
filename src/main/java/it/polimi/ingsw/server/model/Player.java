package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.model.card.*;
import it.polimi.ingsw.util.customexceptions.*;
import it.polimi.ingsw.util.supportclasses.Color;

import java.util.ArrayList;

import static it.polimi.ingsw.util.supportclasses.Constants.MAX_HAND_SIZE;

public class Player {
    private final Color token;
    private int score;
    private final Game game;
    private final GameField gamefield;
    private final ArrayList<PlaceableCard> hand;
    private StarterCard starterCard;
    private ObjectiveCard secretObjective;
    private ObjectiveCard[] drawnObjectiveCards;

    private boolean isReady;
    private boolean starterCardOrientationSelected;
    private boolean alreadyPlaced;
    private int numOfCompletedObjectiveCards;
    //TODO spostare qui il booleano already placed dal clienthandler

    public Player(Game game) {
        this.game = game;
        this.token = game.getRandomToken();
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

    public boolean hasAlreadyPlaced() {
        return alreadyPlaced;
    }

    public void clearTurnState() {
        alreadyPlaced = false;
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

    /**
     * this method sets the player state to ready and notifies the GameObserver
     * @param ready is the state of the player
     */
    public void setReady(boolean ready) {
        isReady = ready;
        game.gameObserver.notifyReady();
    }
    public boolean isStarterCardOrientationSelected() {
        return starterCardOrientationSelected;
    }

    public void setStarterCardOrientationSelected(boolean starterCardOrientationSelected) {
        this.starterCardOrientationSelected = starterCardOrientationSelected;
        game.gameObserver.notifyStarterCardAndSecretObjectiveSelected();
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
            System.out.println("hand for player " + this.getToken() + ": " + hand);
        } catch (FullHandException | EmptyDeckException ignored) {
        }
    }

    /**
     * this method updates the player's score and notifies the GameObserver
     * @param newScore is the player's updated score
     */
    public void setScore(int newScore) {
        this.score = newScore;
        game.gameObserver.notifyLastRound();
    }

    public void increaseScore(int amount) {
        setScore(getScore() + amount);
    }

    public StarterCard getStarterCard() {
        return starterCard;
    }

    public void setStarterCard(StarterCard starterCard) {
        this.starterCard = starterCard;
    }

    /**
     * this method sets the secrete objective card chosen by the player
     * @param objectiveCard chosen by the player at the beginning of the match
     */
    public void setSecretObjective(ObjectiveCard objectiveCard) {
        if(this.secretObjective != null) return;
        this.secretObjective = objectiveCard;
        game.gameObserver.notifyStarterCardAndSecretObjectiveSelected();
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

    /**
     * this method invokes the place method for the starter card in the player's game-field
     * @param card is the starter card the player owns
     * @param facingUp is the orientation of the starter card
     */
    public void place(StarterCard card, boolean facingUp){
        gamefield.place(card, facingUp);
        alreadyPlaced = true;
    }

    /**
     * this method invokes the place method for a generic placeable card in the player's game-field
     * @param id that identifies the card
     * @param facingUp is the card's orientation
     * @param x horizontal coordinate
     * @param y vertical coordinate
     * @throws CannotPlaceCardException thrown when the player executes an illegal placement
     * @throws CardNotInHandException thrown when the card isn't owned by the player
     */
    public void place(int id, boolean facingUp, int x, int y) throws CannotPlaceCardException, CardNotInHandException {
        if (hasAlreadyPlaced()) throw new CannotPlaceCardException("You have already placed!");
        //seleziona carta dalla mano
        PlaceableCard cardInHand = null;
        for (int i=0; i< hand.size(); i++) {
            if (hand.get(i).getId() == id) {
                cardInHand = hand.get(i);
                break;
            }
        }
        if(cardInHand == null) throw new CardNotInHandException();
        gamefield.place(cardInHand,facingUp,x,y);
        for (int i=0; i< hand.size(); i++) {
            if (hand.get(i).getId() == cardInHand.getId()) {
                hand.remove(i);
                break;
            }
        }
        alreadyPlaced = true;
    }

    /**
     * this method calculates the points given by the secrete objective card and the 2 common objective
     */
    public void calculateFinalScore() {
        increaseScore(secretObjective.getEarnedPoints(getGamefield()));
        increaseScore(game.commonObjectives.get(0).getEarnedPoints(getGamefield()));
        increaseScore(game.commonObjectives.get(1).getEarnedPoints(getGamefield()));
    }

    /**
     * this method increases the number of completed objective cards
     */
    public void increaseNumOfCompletedObjective () {
        this.numOfCompletedObjectiveCards ++;
    }
    public int getNumOfCompletedObjectiveCards() {
        return numOfCompletedObjectiveCards;
    }

    /**
     * this method is used to compare a player's attribute with another player
     * @param other other player in the comparison
     * @return the result of the comparison
     */
    public int compareTo (Player other){
        if (this.getScore() > other.getScore()) return -1;
        else if (this.getScore() < other.getScore()) return 1;
        else {
            if(this.getNumOfCompletedObjectiveCards() > other.getNumOfCompletedObjectiveCards()) return -1;
            else if (this.getNumOfCompletedObjectiveCards() < other.getNumOfCompletedObjectiveCards()) return 1;
        }
        return 0;
    }
}
