package it.polimi.ingsw.client.view;

import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
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



    private Label alertLabel;
    private Pane handPane;
    private Pane boardPane;
    private Pane decksPane;
    private HBox commonObjectivesPane;
    private HBox secretObjectivePane;
    private ScrollPane scrollPane;
    private int starterCardId;



    private double mouseX;
    private double mouseY;

    private double offsetX;
    private double offsetY;

    public CardPlacementController(Label alertLabel, Pane handPane, ScrollPane scrollPane,
                                   Pane decksPane, HBox commonObjectivesPane, HBox secretObjectivePane) {

        this.scrollPane = scrollPane;
        this.alertLabel = alertLabel;
        this.handPane = handPane;
        this.decksPane = decksPane;
        this.commonObjectivesPane = commonObjectivesPane;
        this.secretObjectivePane = secretObjectivePane;


        boardPane = new Pane();
        boardPane.setStyle("-fx-background-color: #17914c; -fx-border-color: black; -fx-border-width: 2px;");
        boardPane.setPrefSize(PANE_WIDTH, PANE_HEIGHT);
        scrollPane.setContent(boardPane);
        scrollPane.setHvalue(0.5);
        scrollPane.setVvalue(0.5);

    }

    public void unshowCards(){
        handPane.getChildren().clear();
    }

    public void showCards(ArrayList<VirtualCard> cardsInHand) {

        double currentX = SPACING;

        for(VirtualCard v: cardsInHand){
            //v.setFacingUp(true);

            Rectangle cardNode = v.getCard();
            cardNode.setLayoutX(currentX);
            cardNode.setLayoutY(0);

            makeDraggableAndDroppable(cardNode);

            handPane.getChildren().add(cardNode);
            currentX += CARD_WIDTH + SPACING;

        }
    }


    public void initializeBoard(int starterCardId){

        VirtualCard virtualCard = new VirtualCard(starterCardId, true);
        this.starterCardId = starterCardId;
        Rectangle cardNode = virtualCard.getCard();
        cardNode.setLayoutX(Math.round((PANE_WIDTH/X_SNAP_INCREMENT)/2) * X_SNAP_INCREMENT);
        cardNode.setLayoutY(Math.round((PANE_HEIGHT/Y_SNAP_INCREMENT)/2) * Y_SNAP_INCREMENT);

        boardPane.getChildren().add(cardNode);


    }



    public void clearBoard (){
        boardPane.getChildren().clear();
        initializeBoard(starterCardId);
    }

    public void makeDraggableAndDroppable(Node card) {


        card.setOnMousePressed(event -> {
            mouseX = event.getSceneX();
            mouseY = event.getSceneY();

            offsetX = card.getLayoutX();
            offsetY = card.getLayoutY();
        });


        card.setOnMouseDragged(event -> {
            double deltaX = event.getSceneX() - mouseX;
            double deltaY = event.getSceneY() - mouseY;

            card.setLayoutX(offsetX + deltaX);
            card.setLayoutY(offsetY + deltaY);
        });

        card.setOnMouseReleased(event -> {

            Bounds cardBounds = card.localToScene(card.getBoundsInLocal());

            Bounds scrollPaneBounds = scrollPane.getLayoutBounds();

            mouseX = event.getSceneX();
            mouseY = event.getSceneY();

            if (!scrollPaneBounds.contains(mouseX, mouseY)){
                card.setLayoutX(offsetX);
                card.setLayoutY(offsetY);
                updateLabel("Out of bounds");
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

                    updateLabel("Placed Card In (" + absoluteToRelativeX(snapX) + "," + absoluteToRelativeY(snapY) + ")");

                    makeUndraggable(card);
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
        double centerX = (Math.round((PANE_WIDTH/X_SNAP_INCREMENT)/2) * X_SNAP_INCREMENT);
        return ((int) ((absX - centerX) / X_SNAP_INCREMENT) );

    }

    private int absoluteToRelativeY(double absY) {
        double centerY = (Math.round((PANE_HEIGHT/Y_SNAP_INCREMENT)/2) * Y_SNAP_INCREMENT);
        return -((int) ((absY - centerY) / Y_SNAP_INCREMENT) );

    }

    private boolean canBePlacedHere(double absX, double absY){
        return (abs(absoluteToRelativeX(absX))%2)==(abs(absoluteToRelativeY(absY))%2);
    }

    public void loadCommonObjectives (int firstObjectiveId, int secondObjectiveId){

        VirtualCard virtualCard = new VirtualCard(firstObjectiveId, true);
        commonObjectivesPane.getChildren().add(virtualCard.getCard());
        virtualCard = new VirtualCard(secondObjectiveId, true);
        commonObjectivesPane.getChildren().add(virtualCard.getCard());

    }

    public void loadSecretObjective (int ObjectiveCardId){

        VirtualCard virtualCard = new VirtualCard(ObjectiveCardId, true);
        secretObjectivePane.getChildren().add(virtualCard.getCard());

    }

    public void loadDecks(VirtualCard[] decks){

        updateLabel("Draw a Card");

        for (int i = 0; i < 3; i++) {

            double xLayout = 10 + i * 150;
            double yLayout = 20;



            if(decks[i].getId() != 0) {

                VirtualCard virtualCard = new VirtualCard(decks[i].getId(), i != 0);
                //cardsOnTopOfDecks[i] = virtualCard;
                Rectangle cardNode = virtualCard.getCard();

                cardNode.setLayoutX(xLayout);
                cardNode.setLayoutY(yLayout);

                Button button = getCustomButton("Draw", xLayout, yLayout);

                int finalI = i;
                button.setOnAction(event -> {
                    handleDrawButtonClick(finalI);
                });
                decksPane.getChildren().addAll(virtualCard.getCard(), button);
            }
        }

        for (int i = 3; i < 5; i++) {

            double xLayout = 10 + i * 150;
            double yLayout = 20 + 2 * CARD_HEIGHT;

            if(decks[i].getId() != 0) {

                VirtualCard virtualCard = new VirtualCard(decks[i].getId(), i != 0);
                //cardsOnTopOfDecks[i + 3] = virtualCard;
                Rectangle cardNode = virtualCard.getCard();


                cardNode.setLayoutX(xLayout);
                cardNode.setLayoutY(yLayout);

                Button button = getCustomButton("Draw", xLayout, yLayout);


                int finalI = i ;
                button.setOnAction(event -> {
                    handleDrawButtonClick(finalI);
                });
                decksPane.getChildren().addAll(virtualCard.getCard(), button);
            }
        }

    }

    private void handleDrawButtonClick(int buttonIndex) {

        unshowCards();
        //addCardToHand(cardsOnTopOfDecks[buttonIndex]);
        decksPane.getChildren().clear();
        //TODO send draw message
    }

    public void updateLabel(String newText) {
        alertLabel.setText(newText);
    }

    private Button getCustomButton(String text, double xLayout, double yLayout){
        Button button = new Button(text);

        button.setLayoutX(xLayout);
        button.setPrefWidth(CARD_WIDTH);
        button.setLayoutY(yLayout + CARD_HEIGHT + 20);

        return button;

    }


}






