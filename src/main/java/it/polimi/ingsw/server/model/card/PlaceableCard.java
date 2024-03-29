package it.polimi.ingsw.server.model.card;

import it.polimi.ingsw.util.supportclasses.Resource;

import java.util.ArrayList;

public abstract class PlaceableCard extends Card {
    protected boolean facingUp;
    protected ArrayList<Resource> requirements;
    protected Corner frontTopLeftCorner;
    protected Corner frontTopRightCorner;
    protected Corner frontBottomLeftCorner;
    protected Corner frontBottomRightCorner;
    protected Corner backTopLeftCorner;
    protected Corner backTopRightCorner;
    protected Corner backBottomLeftCorner;
    protected Corner backBottomRightCorner;

    public Corner getTopLeftCorner() {
        if (facingUp) return frontTopLeftCorner;
        else return backTopLeftCorner;
    }

    public Corner getTopRightCorner() {
        if (facingUp) return frontTopRightCorner;
        else return backTopRightCorner;
    }

    public Corner getBottomLeftCorner() {
        if (facingUp) return frontBottomLeftCorner;
        else return backBottomLeftCorner;
    }

    public Corner getBottomRightCorner() {
        if (facingUp) return frontBottomRightCorner;
        else  return backBottomLeftCorner;
    }
}
