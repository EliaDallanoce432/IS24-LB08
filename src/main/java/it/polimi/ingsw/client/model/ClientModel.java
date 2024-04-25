package it.polimi.ingsw.client.model;

import it.polimi.ingsw.client.view.VirtualCard;

import java.util.ArrayList;

import static it.polimi.ingsw.util.supportclasses.ViewConstants.HAND_SIZE;

public class ClientModel {

    private String username;
    private ArrayList<VirtualCard> cardsInHand = new ArrayList<>();
    private VirtualCard[] decks = new VirtualCard[6];
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

    public ArrayList<VirtualCard> getCardsInHand() {
        return cardsInHand;
    }

    public VirtualCard[] getDecks() {
        return decks;
    }

    public void setDecks(VirtualCard[] decks) {
        this.decks = decks;
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
