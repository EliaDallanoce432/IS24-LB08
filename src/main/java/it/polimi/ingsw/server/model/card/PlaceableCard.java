package it.polimi.ingsw.server.model.card;

import it.polimi.ingsw.util.supportclasses.Resource;

public abstract class PlaceableCard extends Card {
    protected int points;
    protected boolean facingUp;
    //protected ArrayList<Resource> requirements;
    protected int requiredFungiResourceAmount;
    protected int requiredInsectResourceAmount;
    protected int requiredAnimalResourceAmount;
    protected int requiredPlantResourceAmount;

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
        else  return backBottomRightCorner;
    }

    public boolean isFacingUp() {
        return facingUp;
    }

    public void setFacingUp(boolean facingUp) {
        this.facingUp = facingUp;
    }

    public int getRequiredFungiResourceAmount() {
        return requiredFungiResourceAmount;
    }

    public int getRequiredInsectResourceAmount() {
        return requiredInsectResourceAmount;
    }

    public int getRequiredAnimalResourceAmount() {
        return requiredAnimalResourceAmount;
    }

    public int getRequiredPlantResourceAmount() {
        return requiredPlantResourceAmount;
    }

    public Resource getCardKingdom(){
        return this.cardKingdom;
    }

    public int getCardID(){
        return this.id;
    }

    public int getPoints() {
        return points;
    }

    /**
     * method returns the points received by placing the card
     * @return int
     */
    public abstract int placementPoints();

}
