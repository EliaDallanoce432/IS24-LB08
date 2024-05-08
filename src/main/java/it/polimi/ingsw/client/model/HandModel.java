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

    public ArrayList<VirtualCard> getCardsInHand() {
        return cardsInHand;
    }

    public void updateCardsInHand(ArrayList<VirtualCard> cardsInHand) {
        this.cardsInHand = cardsInHand;
        notifyObservers();
    }
}

