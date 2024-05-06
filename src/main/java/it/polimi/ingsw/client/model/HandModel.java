package it.polimi.ingsw.client.model;

import it.polimi.ingsw.client.view.VirtualCard;

import java.util.ArrayList;

import static it.polimi.ingsw.util.supportclasses.ViewConstants.HAND_SIZE;

public class HandModel extends ObservableModel {

    private ArrayList<VirtualCard> cardsInHand = new ArrayList<>();

    public ArrayList<VirtualCard> getCardsInHand() {
        return cardsInHand;
    }

    private static HandModel istance;

    public static HandModel getIstance(){

        if (istance==null) istance = new HandModel();
        return istance;

    }


    public void addCardToHand(VirtualCard card) {
        if (cardsInHand.size() >= HAND_SIZE) {
            System.out.println("full hand");
        }
        else {
            cardsInHand.add(card);
        }
    }


}

