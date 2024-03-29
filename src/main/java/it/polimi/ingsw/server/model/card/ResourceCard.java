package it.polimi.ingsw.server.model.card;

import it.polimi.ingsw.server.model.card.PlaceableCard;

public class ResourceCard extends PlaceableCard {

    public ResourceCard() {

    }

    public int placementPoints() {
        return this.getPoints();
    }

}
