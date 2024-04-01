package it.polimi.ingsw.server.model.card;

import it.polimi.ingsw.util.supportclasses.Resource;

public abstract class Card {
    protected int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Resource getCardKingdom() {
        return cardKingdom;
    }

    public void setCardKingdom(Resource cardKingdom) {
        this.cardKingdom = cardKingdom;
    }

    protected Resource cardKingdom; //animal, plant, fungi or insect
}
