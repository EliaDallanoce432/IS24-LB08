package it.polimi.ingsw.client.view.utility;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import static it.polimi.ingsw.util.supportclasses.ViewConstants.*;

/**
 * This class manages the visual representation of a card
 */

public class CardRepresentation {

    private String frontCardTexturePath;
    private String backCardTexturePath;
    private int id;
    private boolean facingUp;
    Rectangle cardFront;
    Rectangle cardBack;


    //only used in placementHistory array to memorize coordinates
    private int x;
    private int y;

    /**
     * Sets up the CardRepresentation
     * @param id the ID of the card to be represented
     * @param isFacingUp whether the card is facing up or not
     */

    public CardRepresentation(int id, boolean isFacingUp) {

        this.id = id;
        this.facingUp = isFacingUp;


        this.frontCardTexturePath = "/view/cards/front/" + id + ".png";
        this.backCardTexturePath = "/view/cards/back/" + id + ".png";

        Image frontTextureImage = new Image(getClass().getResourceAsStream(frontCardTexturePath));
        Image backTextureImage = new Image(getClass().getResourceAsStream(backCardTexturePath));


        cardFront = new Rectangle(CARD_WIDTH, CARD_HEIGHT);
        cardFront.setArcWidth(10);
        cardFront.setArcHeight(10);
        cardFront.setStroke(Color.BLACK);
        cardFront.setStrokeWidth(2);
        cardFront.setFill(new ImagePattern(frontTextureImage));

        cardBack = new Rectangle(CARD_WIDTH, CARD_HEIGHT);
        cardBack.setArcWidth(10);
        cardBack.setArcHeight(10);
        cardBack.setStroke(Color.BLACK);
        cardBack.setStrokeWidth(2);
        cardBack.setFill(new ImagePattern(backTextureImage));

    }

    public void setFacingUp(boolean facingUp) {
        this.facingUp = facingUp;
    }

    public boolean isFacingUp() {
        return facingUp;
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

    /**
     * returns the representation of the current state of the card
     * @return the Rectangle representing the card
     */

    public Rectangle getCard() {

        if (facingUp) return cardFront;
        else return cardBack;
    }

    /**
     * returns the card representation with a custom size multiplier
     * @param size the size multiplier
     * @return the Rectangle representing the card
     */

    public Rectangle getCard(double size){

        if (facingUp) {
            cardFront.setWidth(size*CARD_WIDTH);
            cardFront.setHeight(size*CARD_HEIGHT);
            return cardFront;
        }
        else{
            cardBack.setWidth(size*CARD_WIDTH);
            cardBack.setHeight(size*CARD_HEIGHT);
            return cardBack;
        }

    }

    public int getId() {
        return id;
    }

    /**
     * changes the current facingUp variable to its opposite
     */

    public void flip(){
        facingUp = !facingUp;
    }
}