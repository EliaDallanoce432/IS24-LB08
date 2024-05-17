package it.polimi.ingsw.client.view.utility;

import it.polimi.ingsw.client.controller.ClientController;
import it.polimi.ingsw.client.model.GameFieldModel;
import it.polimi.ingsw.client.model.HandModel;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.Objects;

import static it.polimi.ingsw.util.supportclasses.ViewConstants.*;
import static java.lang.Math.abs;

/**
 * This class manages the representation of the cards in the hand and on the board, along with all of their proprieties.
 */

public class HandAndBoardRepresentation {

    private final Pane handPane;
    private final Pane boardPane;
    private final ScrollPane scrollPane;

    private final double centerX;
    private final double centerY;

    private double mouseX;
    private double mouseY;

    private double offsetX;
    private double offsetY;

    /**
     * Sets up the HandAndBoardRepresentation
     * @param handPane reference to the Pane where the cards in hand are loaded
     * @param scrollPane reference to the ScrollPane where the game board will be loaded
     */

    public HandAndBoardRepresentation(Pane handPane, ScrollPane scrollPane) {

        this.scrollPane = scrollPane;
        this.handPane = handPane;

        centerX = (Math.round((PANE_WIDTH/X_SNAP_INCREMENT)/2) * X_SNAP_INCREMENT);
        centerY = (Math.round((PANE_HEIGHT/Y_SNAP_INCREMENT)/2) * Y_SNAP_INCREMENT);

        Image patternTile = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/view/background_tile.png")));

        BackgroundImage backgroundImage = new BackgroundImage(
                patternTile,
                BackgroundRepeat.REPEAT,   // Repeat horizontally
                BackgroundRepeat.REPEAT,   // Repeat vertically
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT
        );




        boardPane = new Pane();
        //boardPane.setStyle("-fx-background-color: #dbd3ad; -fx-border-color: black; -fx-border-width: 2px;");
        boardPane.setBackground(new Background(backgroundImage));

        boardPane.setPrefSize(PANE_WIDTH, PANE_HEIGHT);
        scrollPane.setContent(boardPane);
        scrollPane.setHvalue(0.5);
        scrollPane.setVvalue(0.5);

    }



    /**
     * sets the card event listeners so that the card is draggable, droppable and sends a "place" message to the server when dropped.
     * It also makes the card snap to certain coordinates when released, so that the card is correctly placed on the corner of the
     * card on the field.
     * @param card Card node to be made draggable and droppable
     * @param vCard Reference to the associated CardRepresentation
     */

    public void makeDraggableAndDroppable(Node card, CardRepresentation vCard) {

        card.setOnMouseEntered(mouseEvent -> card.setCursor(Cursor.OPEN_HAND));

        card.setOnMouseExited(mouseEvent -> card.setCursor(Cursor.DEFAULT));

        card.setOnMousePressed(event -> {
            card.toFront();

            mouseX = event.getSceneX();
            mouseY = event.getSceneY();

            offsetX = card.getLayoutX();
            offsetY = card.getLayoutY();

            card.setCursor(Cursor.CLOSED_HAND);
        });


        card.setOnMouseDragged(event -> {

            card.setCursor(Cursor.CLOSED_HAND);

            double deltaX = event.getSceneX() - mouseX;
            double deltaY = event.getSceneY() - mouseY;

            card.setLayoutX(offsetX + deltaX);
            card.setLayoutY(offsetY + deltaY);
        });

        card.setOnMouseReleased(event -> {

            Bounds cardBounds = card.localToScene(card.getBoundsInLocal());

            Bounds scrollPaneBounds = scrollPane.getLayoutBounds();

            card.setCursor(Cursor.OPEN_HAND);

            mouseX = event.getSceneX();
            mouseY = event.getSceneY();

            if (!scrollPaneBounds.contains(mouseX, mouseY)){
                card.setLayoutX(offsetX);
                card.setLayoutY(offsetY);
            }

            else {

                Point2D cardPositionInTargetPane = boardPane.sceneToLocal(cardBounds.getMinX(), cardBounds.getMinY());

                double snapX = roundToNearest(cardPositionInTargetPane.getX(), X_SNAP_INCREMENT);
                double snapY = roundToNearest(cardPositionInTargetPane.getY(), Y_SNAP_INCREMENT);

                card.setLayoutX(snapX);
                card.setLayoutY(snapY);
                boardPane.getChildren().add(card);
                handPane.getChildren().remove(card);
                makeUndraggable(card);

                int relX = absoluteToRelativeX(snapX);
                int relY = absoluteToRelativeY(snapY);

                ClientController.getInstance().sendPlaceMessage(vCard.getId(), relX, relY, vCard.isFacingUp());
            }


        });

    }

    /**
     * Makes the card Node undraggable
     * @param card card to be made undraggable
     */

    public void makeUndraggable(Node card) {
        card.setOnMousePressed(null);
        card.setOnMouseDragged(null);
        card.setOnMouseReleased(null);
    }

    /**
     * Rounds the given value to the nearest multiple of the increment
     * @param value the value to be rounded
     * @param increment the given increment
     * @return the rounded value
     */

    private double roundToNearest(double value, double increment) {
        return Math.round(value / increment) * increment;
    }

    /**
     * Makes the conversion from the Pane X coordinate to the X coordinate relative to the center of the gameboard
     * @param absX the absolute X coordinate
     * @return the relative coordinates
     */

    private int absoluteToRelativeX(double absX) {
        return ((int) ((absX - centerX) / X_SNAP_INCREMENT) );

    }

    /**
     * Makes the conversion from the Pane Y coordinate to the Y coordinate relative to the center of the gameboard
     * @param absY the absolute Y coordinate
     * @return the relative coordinates
     */

    private int absoluteToRelativeY(double absY) {
        return -((int) ((absY - centerY) / Y_SNAP_INCREMENT) );

    }

    /**
     * Makes the conversion from the X coordinate relative to the center of the gameboard to the Pane X coordinate
     * @param relX the relative coordinate
     * @return the absolute coordinates
     */

    private double relativeToAbsoluteX(int relX) {
        return (relX * X_SNAP_INCREMENT) + centerX;

    }

    /**
     * Makes the conversion from the Y coordinate relative to the center of the gameboard to the Pane Y coordinate
     * @param relY the relative coordinate
     * @return the absolute coordinates
     */

    private double relativeToAbsoluteY(int relY) {
        return ((-relY) * Y_SNAP_INCREMENT) + centerY;

    }

    /**
     * Loads the gameboard from the placementHistory in the GameFieldModel
     */

    public void loadFromPlacementHistory (){

        ArrayList<CardRepresentation> placementHistory = GameFieldModel.getInstance().getPlacementHistory();

        boardPane.getChildren().clear();

        for (CardRepresentation cardRepresentation : placementHistory){

            Rectangle cardNode = cardRepresentation.getCard();
            int relativeX = cardRepresentation.getX();
            int relativeY = cardRepresentation.getY();

            cardNode.setLayoutX(relativeToAbsoluteX(relativeX));
            cardNode.setLayoutY(relativeToAbsoluteY(relativeY));

            makeUndraggable(cardNode);
            boardPane.getChildren().add(cardNode);

        }
    }

    /**
     * Loads the hand from the HandModel
     */

    public void loadHand() {

        handPane.getChildren().clear();


        double currentX = SPACING;

        for(CardRepresentation v: HandModel.getInstance().getCardsInHand()){

            Rectangle cardNode = v.getCard();
            cardNode.setLayoutX(currentX);
            cardNode.setLayoutY(0);

            makeDraggableAndDroppable(cardNode, v);

            handPane.getChildren().add(cardNode);
            currentX += CARD_WIDTH + SPACING;

        }
    }




}






