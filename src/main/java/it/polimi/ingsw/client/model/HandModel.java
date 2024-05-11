package it.polimi.ingsw.client.model;

import it.polimi.ingsw.client.view.VirtualCard;

import java.util.ArrayList;

import static it.polimi.ingsw.util.supportclasses.ViewConstants.HAND_SIZE;

public class HandModel extends ObservableModel {

    private ArrayList<VirtualCard> cardsInHand = new ArrayList<>();

    private static HandModel istance;

    public static HandModel getIstance(){

        if (istance==null) istance = new HandModel();
        return istance;

    }

    private HandModel(){
        cardsInHand = new ArrayList<>();
    }

    public ArrayList<VirtualCard> getCardsInHand() {
        return cardsInHand;
    }

    public void updateCardsInHand(ArrayList<VirtualCard> cardsInHand) {
        this.cardsInHand = cardsInHand;
        notifyObservers();
    }

    public void rollback(){
        notifyObservers();
    }

    public void flipCardsInHand(){
        for (VirtualCard card : cardsInHand) {
            card.flip();
        }
        notifyObservers();
    }


}

