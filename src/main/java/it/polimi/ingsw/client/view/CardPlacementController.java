package it.polimi.ingsw.client.view;

import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

import static it.polimi.ingsw.util.supportclasses.ViewConstants.*;
import static java.lang.Math.abs;


public class CardPlacementController {

    private ArrayList<VirtualCard> cardsInHand;
    private ArrayList<VirtualCard> cardsOnBoard;
    private Pane handPane;
    private Pane boardPane;
    private Pane decksPane;
    private HBox commonObjectivesPane;
    private HBox secretObjectivePane;
    private int starterCardId;



    private double mouseX;
    private double mouseY;

    private double offsetX;
    private double offsetY;

    public CardPlacementController(Pane handPane, ScrollPane scrollPane,
                                   Pane decksPane, HBox commonObjectivesPane, HBox secretObjectivePane) {

        this.handPane = handPane;
        this.handPane = handPane;
        this.decksPane = decksPane;
        this.commonObjectivesPane = commonObjectivesPane;
        this.secretObjectivePane = secretObjectivePane;

        cardsInHand = new ArrayList<>();
        cardsOnBoard = new ArrayList<>();
        boardPane = new Pane();
        boardPane.setStyle("-fx-background-color: #17914c; -fx-border-color: black; -fx-border-width: 2px;");
        boardPane.setPrefSize(PANE_WIDTH, PANE_HEIGHT);
        scrollPane.setContent(boardPane);
        scrollPane.setHvalue(0.5);
        scrollPane.setVvalue(0.5);

    }


    public ArrayList<VirtualCard> getCardsInHand() {
        return cardsInHand;
    }

    public void addCardToHand(VirtualCard card) {

        if (cardsInHand.size() >= HAND_SIZE) {
            System.out.println("full hand");
        }
        else {
            cardsInHand.add(card);
        }
    }

    public void removeCardFromHand(Node card) {
        cardsInHand.removeIf(vc -> vc.getCard().equals(card));
    }

    public void unshowCards(){
        handPane.getChildren().clear();
    }

    public void showCards() {

        double currentX = SPACING;

        for(VirtualCard v: cardsInHand){

            Rectangle cardNode = v.getCard();
            cardNode.setLayoutX(currentX);

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

            Point2D cardPositionInTargetPane = boardPane.sceneToLocal(cardBounds.getMinX(),cardBounds.getMinY());

            double snapX = roundToNearest(cardPositionInTargetPane.getX(), X_SNAP_INCREMENT);
            double snapY = roundToNearest(cardPositionInTargetPane.getY(), Y_SNAP_INCREMENT);

            if (!canBePlacedHere(snapX,snapY)) {
                card.setLayoutX(0);
                card.setLayoutY(0);
            }
            else{

                card.setLayoutX(snapX);
                card.setLayoutY(snapY);
                removeCardFromHand(card);
                boardPane.getChildren().add(card);
                handPane.getChildren().remove(card);

                makeUndraggable(card);
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

    public void loadDecks(int[] resourceTopDeck, int[] goldTopDeck ){

        for (int i = 0; i < 3; i++) {

            VirtualCard virtualCard = new VirtualCard(resourceTopDeck[i], i!=0);
            Rectangle cardNode = virtualCard.getCard();

            Button button = new Button("Button " + (i + 1));

            double xLayout = 10 + i * 150;
            double yLayout = 20;

            cardNode.setLayoutX(xLayout);
            cardNode.setLayoutY(yLayout);

            button.setLayoutX(xLayout);
            button.setLayoutY(yLayout+CARD_HEIGHT+20);

            int finalI = i; // For use in lambda expression
            button.setOnAction(event -> {
                // Call a method corresponding to the button
                handleButtonClick(finalI);
            });
            decksPane.getChildren().addAll( virtualCard.getCard() , button);
        }

        for (int i = 0; i < 3; i++) {

            VirtualCard virtualCard = new VirtualCard(goldTopDeck[i], i!=0);
            Rectangle cardNode = virtualCard.getCard();

            Button button = new Button("Button " + (i + 1));


            double xLayout = 10 + i * 150;
            double yLayout = 20 + 2 * CARD_HEIGHT;

            cardNode.setLayoutX(xLayout);
            cardNode.setLayoutY(yLayout);

            button.setLayoutX(xLayout);
            button.setLayoutY(yLayout+CARD_HEIGHT+20);


            int finalI = i+3; // For use in lambda expression
            button.setOnAction(event -> {
                // Call a method corresponding to the button
                handleButtonClick(finalI);
            });
            decksPane.getChildren().addAll( virtualCard.getCard() , button);
        }

    }

    private void handleButtonClick(int buttonIndex) {
        // Implement the logic for each button click
        switch (buttonIndex) {
            case 0:
                System.out.println("Button 1 clicked");
                break;
            case 1:
                System.out.println("Button 2 clicked");
                break;
            case 2:
                System.out.println("Button 3 clicked");
                break;
            case 3:
                System.out.println("Button 4 clicked");
                break;
            case 4:
                System.out.println("Button 5 clicked");
                break;
            case 5:
                System.out.println("Button 6 clicked");
                break;
            default:
                System.out.println("Invalid button index");
        }
    }


}






