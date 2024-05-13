package it.polimi.ingsw.client.view;

import it.polimi.ingsw.client.controller.ClientController;
import it.polimi.ingsw.client.model.HandModel;
import it.polimi.ingsw.util.customexceptions.AlreadyPlacedInThisRoundException;
import it.polimi.ingsw.util.customexceptions.NotValidPlacement;
import it.polimi.ingsw.util.customexceptions.NotYourTurnException;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

import static it.polimi.ingsw.util.supportclasses.ViewConstants.*;
import static java.lang.Math.abs;


public class CardPlacementController {



    private final Label alertLabel;
    private final Pane handPane;
    private final Pane boardPane;
    private final HBox commonObjectivesPane;
    private final HBox secretObjectivePane;
    private final ScrollPane scrollPane;

    private double centerX;
    private double centerY;

    private int selectedCardID;



    private double mouseX;
    private double mouseY;

    private double offsetX;
    private double offsetY;

    public CardPlacementController(Label alertLabel, Pane handPane, ScrollPane scrollPane, HBox commonObjectivesPane, HBox secretObjectivePane) {

        this.scrollPane = scrollPane;
        this.alertLabel = alertLabel;
        this.handPane = handPane;
        this.commonObjectivesPane = commonObjectivesPane;
        this.secretObjectivePane = secretObjectivePane;

        centerX = (Math.round((PANE_WIDTH/X_SNAP_INCREMENT)/2) * X_SNAP_INCREMENT);
        centerY = (Math.round((PANE_HEIGHT/Y_SNAP_INCREMENT)/2) * Y_SNAP_INCREMENT);


        boardPane = new Pane();
        boardPane.setStyle("-fx-background-color: #17914c; -fx-border-color: black; -fx-border-width: 2px;");
        boardPane.setPrefSize(PANE_WIDTH, PANE_HEIGHT);
        scrollPane.setContent(boardPane);
        scrollPane.setHvalue(0.5);
        scrollPane.setVvalue(0.5);

    }



    public void loadHand() {

        handPane.getChildren().clear();


        double currentX = SPACING;

        for(VirtualCard v: HandModel.getIstance().getCardsInHand()){

            Rectangle cardNode = v.getCard();
            cardNode.setLayoutX(currentX);
            cardNode.setLayoutY(0);

            makeDraggableAndDroppable(cardNode, v);

            handPane.getChildren().add(cardNode);
            currentX += CARD_WIDTH + SPACING;

        }
    }




    public void makeDraggableAndDroppable(Node card, VirtualCard vCard) {
        card.setOnMouseEntered(mouseEvent -> {
            card.setCursor(Cursor.OPEN_HAND);
        });

        card.setOnMouseExited(mouseEvent -> {
            card.setCursor(Cursor.DEFAULT);
        });

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

                if (!canBePlacedHere(snapX, snapY)) {
                    card.setLayoutX(offsetX);
                    card.setLayoutY(offsetY);
                } else {

                    card.setLayoutX(snapX);
                    card.setLayoutY(snapY);
                    boardPane.getChildren().add(card);
                    handPane.getChildren().remove(card);
                    makeUndraggable(card);

                    int relX = absoluteToRelativeX(snapX);
                    int relY = absoluteToRelativeY(snapY);

                    ClientController.getInstance().sendPlaceMessage(vCard.getId(), relX, relY, vCard.isFacingUp());


                }
            }


        });

    }

    public void makeUndraggable(Node card) {
        card.setOnMousePressed(null);
        card.setOnMouseDragged(null);
        card.setOnMouseReleased(null);
    }

    private double roundToNearest(double value, double increment) {
        return Math.round(value / increment) * increment;
    }

    private int absoluteToRelativeX(double absX) {
        return ((int) ((absX - centerX) / X_SNAP_INCREMENT) );

    }

    private int absoluteToRelativeY(double absY) {
        return -((int) ((absY - centerY) / Y_SNAP_INCREMENT) );

    }

    private double relativeToAbsoluteX(int relX) {
        return (relX * X_SNAP_INCREMENT) + centerX;

    }

    private double relativeToAbsoluteY(int relY) {
        return ((-relY) * Y_SNAP_INCREMENT) + centerY;

    }



    private boolean canBePlacedHere(double absX, double absY){
        return (abs(absoluteToRelativeX(absX))%2)==(abs(absoluteToRelativeY(absY))%2);
    }

    public void loadCommonObjectives (int[] commonObjIds){

        commonObjectivesPane.getChildren().clear();

        VirtualCard virtualCard = new VirtualCard(commonObjIds[0], true);
        commonObjectivesPane.getChildren().add(virtualCard.getCard());
        virtualCard = new VirtualCard(commonObjIds[1], true);
        commonObjectivesPane.getChildren().add(virtualCard.getCard());

    }

    public void loadSecretObjective (int ObjectiveCardId){

        secretObjectivePane.getChildren().clear();

        VirtualCard virtualCard = new VirtualCard(ObjectiveCardId, true);
        secretObjectivePane.getChildren().add(virtualCard.getCard());

    }

    public void loadFromPlacementHistory (ArrayList<VirtualCard> placementHistory){

        boardPane.getChildren().clear();

        for (VirtualCard virtualCard : placementHistory){

            Rectangle cardNode = virtualCard.getCard();
            int relativeX = virtualCard.getX();
            int relativeY = virtualCard.getY();

            cardNode.setLayoutX(relativeToAbsoluteX(relativeX));
            cardNode.setLayoutY(relativeToAbsoluteY(relativeY));

            makeUndraggable(cardNode);
            boardPane.getChildren().add(cardNode);

        }
    }




}






