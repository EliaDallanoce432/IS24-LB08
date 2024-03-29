package it.polimi.ingsw.server.model;

import it.polimi.ingsw.util.supportclasses.Resource;

import java.util.ArrayList;

public abstract class PlaceableCard extends Card{
    private boolean facingUp;
    private ArrayList<Resource> requirements;
    private Corner frontTopLeftCorner;
    private Corner frontTopRightCorner;
    private Corner frontBottomLeftCorner;
    private Corner frontBottomRightCorner;
    private Corner backTopLeftCorner;
    private Corner backTopRightCorner;
    private Corner backBottomLeftCorner;
    private Corner backBottomRightCorner;

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
