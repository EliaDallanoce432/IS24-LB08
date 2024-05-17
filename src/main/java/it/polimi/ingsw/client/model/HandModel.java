package it.polimi.ingsw.client.model;

import it.polimi.ingsw.client.view.utility.CardRepresentation;

import java.util.ArrayList;

public class HandModel extends ObservableModel {

    private ArrayList<CardRepresentation> cardsInHand = new ArrayList<>();

    private static HandModel instance;

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

    public void updateCardsInHand(ArrayList<CardRepresentation> cardsInHand) {
        this.cardsInHand = cardsInHand;
        notifyObservers();
    }

    public void rollback(){
        notifyObservers();
    }

    public void flipCardsInHand(){
        for (CardRepresentation card : cardsInHand) {
            card.flip();
        }
        notifyObservers();
    }


}

