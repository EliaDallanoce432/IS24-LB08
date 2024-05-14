package it.polimi.ingsw.client.model;

import it.polimi.ingsw.client.view.utility.CardRepresentation;

import java.util.ArrayList;

public class HandModel extends ObservableModel {

    private ArrayList<CardRepresentation> cardsInHand = new ArrayList<>();

    private static HandModel istance;

    public static HandModel getIstance(){

        if (istance==null) istance = new HandModel();
        return istance;

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

