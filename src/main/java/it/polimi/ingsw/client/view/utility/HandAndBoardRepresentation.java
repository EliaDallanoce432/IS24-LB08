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
     * Sets up the HandAndBoardRepresentation.
     * @param handPane Reference to the Pane where the cards in hand are loaded.
     * @param scrollPane Reference to the ScrollPane where the game board will be loaded.
     */
    public HandAndBoardRepresentation(Pane handPane, ScrollPane scrollPane) {
        this.scrollPane = scrollPane;
        this.handPane = handPane;

        centerX = (Math.round((PANE_WIDTH/X_SNAP_INCREMENT)/2) * X_SNAP_INCREMENT);
        centerY = (Math.round((PANE_HEIGHT/Y_SNAP_INCREMENT)/2) * Y_SNAP_INCREMENT);

        Image patternTile = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Images/background_tile.png")));

        BackgroundImage backgroundImage = new BackgroundImage(
                patternTile,
                BackgroundRepeat.REPEAT,   // Repeat horizontally
                BackgroundRepeat.REPEAT,   // Repeat vertically
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT
        );

        boardPane = new Pane();
        boardPane.setBackground(new Background(backgroundImage));

        boardPane.setPrefSize(PANE_WIDTH, PANE_HEIGHT);
        scrollPane.setContent(boardPane);
        scrollPane.setHvalue(0.5);
        scrollPane.setVvalue(0.503);
    }



    /**
     * Sets the card event listeners so that the card is draggable, droppable and sends a "place" message to the server when dropped.
     * It also makes the card snap to certain coordinates when released, so that it is correctly placed on the corner of the
     * card on the field.
     * @param card Card node to be made draggable and droppable.
     * @param cardRepresentation Reference to the associated CardRepresentation.
     */
    private void makeDraggableAndDroppable(Node card, CardRepresentation cardRepresentation) {
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
            Bounds scrollPaneBounds = scrollPane.localToScene(scrollPane.getBoundsInLocal());

            card.setCursor(Cursor.OPEN_HAND);

            mouseX = event.getSceneX();
            mouseY = event.getSceneY();

            if (!scrollPaneBounds.contains(mouseX, mouseY)) {
                card.setLayoutX(offsetX);
                card.setLayoutY(offsetY);
            } else {
                Point2D cardPositionInTargetPane = boardPane.sceneToLocal(cardBounds.getMinX(), cardBounds.getMinY());

                double snapX = roundToNearest(cardPositionInTargetPane.getX(), X_SNAP_INCREMENT);
                double snapY = roundToNearest(cardPositionInTargetPane.getY(), Y_SNAP_INCREMENT);

                card.setLayoutX(snapX);
                card.setLayoutY(snapY);
                boardPane.getChildren().add(card);
                handPane.getChildren().remove(card);
                makeNotDraggable(card);

                int relX = absoluteToRelativeX(snapX);
                int relY = absoluteToRelativeY(snapY);

                ClientController.getInstance().sendPlaceMessage(cardRepresentation.getId(), relX, relY, cardRepresentation.isFacingUp());
            }
        });
    }

    /**
     * Makes the card Node not draggable.
     * @param card Card to be made not draggable.
     */
    private void makeNotDraggable(Node card) {
        card.setOnMousePressed(null);
        card.setOnMouseDragged(null);
        card.setOnMouseReleased(null);
    }

    /**
     * Rounds the given value to the nearest multiple of the increment.
     * @param value The value to be rounded.
     * @param increment The given increment.
     * @return The rounded value.
     */
    private double roundToNearest(double value, double increment) {
        return Math.round(value / increment) * increment;
    }

    /**
     * Makes the conversion from the Pane X coordinate to the X coordinate relative to the center of the game board.
     * @param absX The absolute X coordinate.
     * @return The relative coordinates.
     */
    private int absoluteToRelativeX(double absX) {
        return ((int) ((absX - centerX) / X_SNAP_INCREMENT) );
    }

    /**
     * Makes the conversion from the Pane Y coordinate to the Y coordinate relative to the center of the game board.
     * @param absY The absolute Y coordinate.
     * @return The relative coordinates.
     */
    private int absoluteToRelativeY(double absY) {
        return -((int) ((absY - centerY) / Y_SNAP_INCREMENT) );
    }

    /**
     * Makes the conversion from the X coordinate relative to the center of the game board to the Pane X coordinate
     * @param relX the relative coordinate
     * @return the absolute coordinates
     */
    private double relativeToAbsoluteX(int relX) {
        return (relX * X_SNAP_INCREMENT) + centerX;
    }

    /**
     * Makes the conversion from the Y coordinate relative to the center of the game board to the Pane Y coordinate.
     * @param relY The relative coordinate.
     * @return The absolute coordinates.
     */
    private double relativeToAbsoluteY(int relY) {
        return ((-relY) * Y_SNAP_INCREMENT) + centerY;
    }

    /**
     * Loads the game board from the placementHistory in the GameFieldModel.
     */
    public void loadBoardFromPlacementHistory(){
        ArrayList<CardRepresentation> placementHistory = GameFieldModel.getInstance().getPlacementHistory();

        boardPane.getChildren().clear();

        for (CardRepresentation cardRepresentation : placementHistory){
            Rectangle cardNode = cardRepresentation.getCard();
            int relativeX = cardRepresentation.getX();
            int relativeY = cardRepresentation.getY();

            cardNode.setLayoutX(relativeToAbsoluteX(relativeX));
            cardNode.setLayoutY(relativeToAbsoluteY(relativeY));

            makeNotDraggable(cardNode);
            boardPane.getChildren().add(cardNode);
        }
    }

    /**
     * Loads the hand from the HandModel and makes the cards draggable.
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