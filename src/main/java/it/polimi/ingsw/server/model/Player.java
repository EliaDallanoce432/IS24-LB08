package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.model.card.*;
import it.polimi.ingsw.util.customexceptions.*;
import it.polimi.ingsw.util.supportclasses.Token;

import java.util.ArrayList;

import static it.polimi.ingsw.util.supportclasses.Constants.MAX_HAND_SIZE;

/**
 * This class represents a player participating in the game. It manages the player's state,
 *  including their assigned token color, score, hand of cards, starter card, secret objective,
 *  drawn objective card choices, and various flags indicating player actions.
 */
public class Player {
    private final Token token;
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

    public Player(Game game) {
        this.game = game;
        this.token = game.getRandomToken();
        this.score = 0;
        this.gamefield = new GameField(this);
        this.hand = new ArrayList<>();
        this.starterCard = null;
        this.secretObjective = null;
        this.drawnObjectiveCards = new ObjectiveCard[2];
        this.isReady = false;
        this.starterCardOrientationSelected = false;
    }
    //GETTERS and SETTERS
    public ArrayList<PlaceableCard> getHand() {
        return hand;
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

    public StarterCard getStarterCard() {
        return starterCard;
    }

    public void setStarterCard(StarterCard starterCard) {
        this.starterCard = starterCard;
    }

    public boolean isStarterCardOrientationSelected() {
        return starterCardOrientationSelected;
    }

    public Token getToken() {
        return token;
    }

    public int getScore() {
        return score;
    }

    public ObjectiveCard[] getDrawnObjectiveCards() {
        return drawnObjectiveCards;
    }

    public ObjectiveCard getSecretObjective() {
        return secretObjective;
    }

    public void setDrawnObjectiveCards(ObjectiveCard[] drawnObjectiveCards) {this.drawnObjectiveCards = drawnObjectiveCards;}

    public boolean isReady() {
        return isReady;
    }

    public int getNumOfCompletedObjectiveCards() {
        return numOfCompletedObjectiveCards;
    }

    /**
     * sets the player state to ready and notifies the GameObserver
     * @param ready is the state of the player
     */
    public void setReady(boolean ready) {
        isReady = ready;
        game.getGameObserver().notifyReady();
    }
    /**
     * sets the starter-card orientation chosen by the player and notifies the GameObserver
     * @param starterCardOrientationSelected is the orientation selected by the player
     */
    public void setStarterCardOrientationSelected(boolean starterCardOrientationSelected) {
        this.starterCardOrientationSelected = starterCardOrientationSelected;
        game.getGameObserver().notifyStarterCardAndSecretObjectiveSelected();
    }

    /**
     * adds to the player's hand the first 3 cards of his game
     */
    public void initializeHand() {
        hand.clear();
        try {
            addToHand((ResourceCard) game.getResourceCardDeck().directDraw());
            addToHand((ResourceCard) game.getResourceCardDeck().directDraw());
            addToHand((GoldCard) game.getGoldCardDeck().directDraw());
        } catch (FullHandException | EmptyDeckException ignored) {
        }
    }

    /**
     * updates the player's score and notifies the GameObserver
     * @param newScore is the player's updated score
     */
    public void setScore(int newScore) {
        this.score = newScore;
        game.getGameObserver().notifyLastRound();
    }

    /**
     * calculates player's final score
     * @param amount is the amount of points to be added
     */
    public void increaseScore(int amount) {
        setScore(getScore() + amount);
    }

    /**
     * sets the secrete objective card chosen by the player
     * @param objectiveCard chosen by the player at the beginning of the match
     */
    public void setSecretObjective(ObjectiveCard objectiveCard) {
        if(this.secretObjective != null) return;
        this.secretObjective = objectiveCard;
        game.getGameObserver().notifyStarterCardAndSecretObjectiveSelected();
    }

    /**
     * adds the drawn card to the player's hand
     * @param card card to be added
     */
    public void addToHand(PlaceableCard card) throws FullHandException {
        if (this.hand.size() >= MAX_HAND_SIZE) throw new FullHandException();
        hand.add(card);
    }

    /**
     * removes the card from the player's hand
     * @param card card to be taken
     * @return placeable card deleted
     */
    public PlaceableCard removeFromHand(PlaceableCard card) throws CardNotInHandException {
        if(!hand.remove(card)) throw new CardNotInHandException();
        return card;
    }

    /**
     * invokes the place method for the starter card in the player's game-field
     * @param card is the starter card the player owns
     * @param facingUp is the orientation of the starter card
     */
    public void place(StarterCard card, boolean facingUp){
        gamefield.place(card, facingUp);
        alreadyPlaced = true;
    }

    /**
     * invokes the place method for a generic placeable card in the player's game-field
     * @param id that identifies the card
     * @param facingUp is the card's orientation
     * @param x horizontal coordinate
     * @param y vertical coordinate
     * @throws CannotPlaceCardException thrown when the player executes an illegal placement
     * @throws CardNotInHandException thrown when the card isn't owned by the player
     */
    public void place(int id, boolean facingUp, int x, int y) throws CannotPlaceCardException, CardNotInHandException {
        if (hasAlreadyPlaced()) throw new CannotPlaceCardException("You have already placed!");
        PlaceableCard cardInHand = null;
        for (PlaceableCard placeableCard : hand) {
            if (placeableCard.getId() == id) {
                cardInHand = placeableCard;
                break;
            }
        }
        if(cardInHand == null) throw new CardNotInHandException();
        gamefield.place(cardInHand,facingUp,x,y);
        removeFromHand(cardInHand);
        alreadyPlaced = true;
    }

    /**
     * calculates the points given by the secrete objective card and the 2 common objective
     */
    public void calculateFinalScore() {
        increaseScore(secretObjective.getEarnedPoints(getGamefield()));
        increaseScore(game.getCommonObjectives().get(0).getEarnedPoints(getGamefield()));
        increaseScore(game.getCommonObjectives().get(1).getEarnedPoints(getGamefield()));
    }

    /**
     * increases the number of completed objective cards
     */
    public void increaseNumOfCompletedObjective () {
        this.numOfCompletedObjectiveCards ++;
    }

    /**
     * compares a player's attribute with another player
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
