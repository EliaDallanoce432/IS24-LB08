package it.polimi.ingsw.server.model.card;

/**
 * This class represents a general card.
 */
public abstract class Card {
    protected int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
