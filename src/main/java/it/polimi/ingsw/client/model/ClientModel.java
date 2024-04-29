package it.polimi.ingsw.client.model;

import it.polimi.ingsw.client.view.VirtualCard;
import it.polimi.ingsw.util.supportclasses.Color;

import java.util.ArrayList;

import static it.polimi.ingsw.util.supportclasses.ViewConstants.HAND_SIZE;

public class ClientModel {

    private String username;
    private Color color;
    private ArrayList<VirtualCard> cardsInHand = new ArrayList<>();
    private int resourceDeckTopCardId;
    private int resourceDeckLeftCardId;
    private int resourceDeckRightCardId;
    private int goldDeckTopCardId;
    private int goldDeckLeftCardId;
    private int goldDeckRightCardId;
    private int starterCardId;

    public ClientModel(String username) {
        this.username = username;
    }

    public  int getStarterCardId() {
        return starterCardId;
    }

    public void setStarterCardId(int starterCardId ) {
        this.starterCardId = starterCardId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public ArrayList<VirtualCard> getCardsInHand() {
        return cardsInHand;
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
