package it.polimi.ingsw.client.model;

import it.polimi.ingsw.client.view.utility.CardRepresentation;
import java.util.ArrayList;

/**
 * This class represents an ObservableModel that keeps track of the player's hand in a card game.
 */
public class HandModel extends ObservableModel {

    private ArrayList<CardRepresentation> cardsInHand;
    private static HandModel instance;

    /**
     * Returns the singleton instance of HandModel.
     * @return The singleton instance of HandModel.
     */
    public static HandModel getInstance(){

        if (instance ==null) instance = new HandModel();
        return instance;

    }

    private HandModel(){
        cardsInHand = new ArrayList<>();
    }

    public ArrayList<CardRepresentation> getCardsInHand() {
        return cardsInHand;
    }

    /**
     * Updates the cards in hand with the provided new list and notifies any registered observers that the data has changed.
     * @param cardsInHand The new list of CardRepresentation objects.
     */
    public void updateCardsInHand(ArrayList<CardRepresentation> cardsInHand) {
        this.cardsInHand = cardsInHand;
        notifyObservers();
    }

    /**
     * Triggers notification to registered observers without modifying the hand state.
     */
    public void rollback(){
        notifyObservers();
    }

    /**
     * Flips all the cards in the player's hand and notifies any registered observers that the data has changed.
     */
    public void flipCardsInHand(){
        for (CardRepresentation card : cardsInHand) {
            card.flip();
        }
        notifyObservers();
    }

    /**
     * Resets the Hand Model.
     */
    public void clear(){
        if (cardsInHand != null) {
            cardsInHand.clear();
        }
    }
}

