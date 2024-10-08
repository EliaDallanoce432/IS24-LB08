package it.polimi.ingsw.client.view.utility;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import java.util.Objects;
import static it.polimi.ingsw.util.supportclasses.ViewConstants.*;

/**
 * This class manages the visual representation of a card.
 */
public class CardRepresentation {
    private final String frontCardTexturePath;
    private final String backCardTexturePath;
    private final int id;
    private boolean facingUp;
    private Rectangle cardFront;
    private Rectangle cardBack;

    //only used in placementHistory array to memorize coordinates
    private int x;
    private int y;

    /**
     * Sets up the CardRepresentation.
     * @param id The ID of the card to be represented.
     * @param isFacingUp Whether the card is facing up or not.
     */
    public CardRepresentation(int id, boolean isFacingUp) {
        this.id = id;
        this.facingUp = isFacingUp;
        this.frontCardTexturePath = "/Images/cards/front/" + id + ".png";
        this.backCardTexturePath = "/Images/cards/back/" + id + ".png";
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
     * Returns the representation of the current state of the card.
     * @return the Rectangle representing the card.
     */
    public Rectangle getCard() {
        loadCardRectangle();
        if (facingUp) return cardFront;
        else return cardBack;
    }

    /**
     * Returns the card representation with a custom size multiplier.
     * @param size the size multiplier.
     * @return the Rectangle representing the card.
     */
    public Rectangle getCard(double size){
        loadCardRectangle();
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

    private void loadCardRectangle() {
        Image frontTextureImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream(frontCardTexturePath)));
        Image backTextureImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream(backCardTexturePath)));

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