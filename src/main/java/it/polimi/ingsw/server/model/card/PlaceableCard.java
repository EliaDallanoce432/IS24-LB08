package it.polimi.ingsw.server.model.card;

import it.polimi.ingsw.server.model.GameField;
import it.polimi.ingsw.util.supportclasses.Resource;

/**
 * This class represents a placeable card.
 */
public abstract class PlaceableCard extends Card {
    protected int points;
    protected Resource cardKingdom; //animal, plant, fungi or insect
    protected boolean facingUp;
    protected int x;
    protected int y;
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

    public Resource getCardKingdom() {
        return cardKingdom;
    }

    public void setCardKingdom(Resource cardKingdom) {
        this.cardKingdom = cardKingdom;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

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

    public void setRequiredFungiResourceAmount(int requiredFungiResourceAmount) {
        this.requiredFungiResourceAmount = requiredFungiResourceAmount;
    }

    public void setRequiredInsectResourceAmount(int requiredInsectResourceAmount) {
        this.requiredInsectResourceAmount = requiredInsectResourceAmount;
    }

    public void setRequiredAnimalResourceAmount(int requiredAnimalResourceAmount) {
        this.requiredAnimalResourceAmount = requiredAnimalResourceAmount;
    }

    public void setRequiredPlantResourceAmount(int requiredPlantResourceAmount) {
        this.requiredPlantResourceAmount = requiredPlantResourceAmount;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public void setFrontTopLeftCorner(Corner frontTopLeftCorner) {
        this.frontTopLeftCorner = frontTopLeftCorner;
    }

    public void setFrontTopRightCorner(Corner frontTopRightCorner) {
        this.frontTopRightCorner = frontTopRightCorner;
    }

    public void setFrontBottomLeftCorner(Corner frontBottomLeftCorner) {
        this.frontBottomLeftCorner = frontBottomLeftCorner;
    }

    public void setFrontBottomRightCorner(Corner frontBottomRightCorner) {
        this.frontBottomRightCorner = frontBottomRightCorner;
    }

    public void setBackTopLeftCorner(Corner backTopLeftCorner) {
        this.backTopLeftCorner = backTopLeftCorner;
    }

    public void setBackTopRightCorner(Corner backTopRightCorner) {
        this.backTopRightCorner = backTopRightCorner;
    }

    public void setBackBottomLeftCorner(Corner backBottomLeftCorner) {
        this.backBottomLeftCorner = backBottomLeftCorner;
    }

    public void setBackBottomRightCorner(Corner backBottomRightCorner) {
        this.backBottomRightCorner = backBottomRightCorner;
    }

    public int getPoints() {
        return points;
    }

    /**
     * Calculates the points earned with this card.
     * @return Points earned by placing the card.
     */
    public abstract int placementPoints(GameField gameField);

    @Override
    @SuppressWarnings("all")
    public boolean equals(Object obj) {
        if(!(obj instanceof PlaceableCard card)) return false;
        if(this.id!=card.id) return false;
        else if (this.cardKingdom != card.cardKingdom) return false;
        else if (this.facingUp != card.facingUp) return false;
        else if (this.x != card.x) return false;
        else if (this.y != card.y) return false;
        else if (this.requiredAnimalResourceAmount != card.requiredAnimalResourceAmount) return false;
        else if (this.requiredFungiResourceAmount != card.requiredFungiResourceAmount) return false;
        else if (this.requiredInsectResourceAmount != card.requiredInsectResourceAmount) return false;
        else if (this.requiredPlantResourceAmount != card.requiredPlantResourceAmount) return false;
        else if (!this.backTopLeftCorner.equals(card.backTopLeftCorner)) return false;
        else if (!this.backTopRightCorner.equals(card.backTopRightCorner)) return false;
        else if (!this.backBottomLeftCorner.equals(card.backBottomLeftCorner)) return false;
        else if (!this.backBottomRightCorner.equals(card.backBottomRightCorner)) return false;
        else if (!this.frontTopLeftCorner.equals(card.frontTopLeftCorner)) return false;
        else if (!this.frontTopRightCorner.equals(card.frontTopRightCorner)) return false;
        else if (!this.frontBottomLeftCorner.equals(card.frontBottomLeftCorner)) return false;
        else if (!this.frontBottomRightCorner.equals(card.frontBottomRightCorner)) return false;
        return true;
    }

}
