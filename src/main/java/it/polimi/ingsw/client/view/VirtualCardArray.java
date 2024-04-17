package it.polimi.ingsw.client.view;

import javafx.scene.Node;

import java.util.ArrayList;

import static it.polimi.ingsw.util.supportclasses.ViewConstants.HAND_SIZE;


public class VirtualCardArray {

    private ArrayList<VirtualCard> cards;

    public VirtualCardArray() {
        cards = new ArrayList<>();
    }

    public ArrayList<VirtualCard> getCards() {
        return cards;
    }

    public void addCard(VirtualCard card) {

        if (cards.size() >= HAND_SIZE) {
            System.out.println("full hand");
        }
        else {
            cards.add(card);
        }
    }

    public void removeCard(Node card) {

        cards.removeIf(vc -> vc.getCard().equals(card));
    }
}
