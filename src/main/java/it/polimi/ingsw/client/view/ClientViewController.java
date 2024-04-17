package it.polimi.ingsw.client.view;



import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.IOException;


import static it.polimi.ingsw.util.supportclasses.ViewConstants.*;
import static java.lang.Math.abs;
import static java.lang.Math.random;

public class ClientViewController {

    @FXML
    private Pane cardsInHandContainer;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private Label alertLabel;
    @FXML
    private ImageView scoreTrackImageView;

    private VirtualCardArray hand;


    private Pane boardPane;

    private double mouseX;
    private double mouseY;

    private double offsetX;
    private double offsetY;

    @FXML
    private void initialize() {



        boardPane = new Pane();
        boardPane.setStyle("-fx-background-color: #17914c; -fx-border-color: black; -fx-border-width: 2px;");
        boardPane.setPrefSize(PANE_WIDTH, PANE_HEIGHT);
        scrollPane.setContent(boardPane);
        scrollPane.setHvalue(0.5);
        scrollPane.setVvalue(0.5);
        hand = new VirtualCardArray();

        //Image scoreTrackTextureImage = new Image(getClass().getResourceAsStream("/scoreTrack.png"));
        //scoreTrackImageView = new ImageView(scoreTrackTextureImage);



        VirtualCard virtualCard = new VirtualCard(81, true);
        Rectangle cardNode = virtualCard.getCard();
        cardNode.setLayoutX(Math.round((PANE_WIDTH/X_SNAP_INCREMENT)/2) * X_SNAP_INCREMENT);
        cardNode.setLayoutY(Math.round((PANE_HEIGHT/Y_SNAP_INCREMENT)/2) * Y_SNAP_INCREMENT);

        System.out.println(cardNode.getLayoutX() + " - " + cardNode.getLayoutY() );
        boardPane.getChildren().add(cardNode);

    }

    @FXML
    private void drawHand() {

        for(int i=0; i<3 ; i++) {

            int randomId = (int) (39*random()+1);
            hand.addCard(new VirtualCard(randomId, true));

        }

        showHand(hand);

    }

    @FXML
    private void clearCards() {
        boardPane.getChildren().clear();
        initialize();
    }
    @FXML
    private void openDrawWindow() throws IOException {

        Stage newStage = new Stage();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("DrawWindow.fxml"));
        Parent newRoot = loader.load();


        StackPane stackPane = new StackPane();
        stackPane.getChildren().add(newRoot);

        newStage.setScene(new Scene(stackPane, 500, 500));
        newStage.setTitle("Draw card");
        newStage.show();


    }
    @FXML
    private void flipCardsInHand() {

        unshowHand();

        for(VirtualCard card : hand.getCards()) {
            card.flip();
        }

        showHand(hand);
    }

    public void updateLabel(String newText) {
        alertLabel.setText(newText);
    }



    public void makeDraggable(Node card) {
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
            double cardX = cardBounds.getMinX() ;
            double cardY = cardBounds.getMinY() ;

            System.out.println("cardX: " + cardX + " cardY: " + cardY);

            Point2D cardPositionInTargetPane = boardPane.sceneToLocal(cardX,cardY);

            System.out.println("XinBoardPane: " + cardPositionInTargetPane.getX() + " YinBoardPane: " + cardPositionInTargetPane.getY()  );

            double snapX = roundToNearest(cardPositionInTargetPane.getX(), X_SNAP_INCREMENT);
            double snapY = roundToNearest(cardPositionInTargetPane.getY(), Y_SNAP_INCREMENT);

            System.out.println("snapX: " + snapX + " snapY: " + snapY);

            if (!canBePlacedHere(snapX,snapY)) {
                updateLabel("Can't Place card in ( " + absoluteToRelativeX(snapX) + " " + absoluteToRelativeY(snapY) + " ) !");
                card.setLayoutX(0);
                card.setLayoutY(0);
            }
            else{
                updateLabel("Placed card in ( " + absoluteToRelativeX(snapX) + " " + absoluteToRelativeY(snapY) + " ) !");
                card.setLayoutX(snapX);
                card.setLayoutY(snapY);
                hand.removeCard(card);
                boardPane.getChildren().add(card);
                cardsInHandContainer.getChildren().remove(card);

                makeUndraggable(card);
            }

            System.out.println("--------------------");


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

    private void showHand(VirtualCardArray cards) {

        double currentX = SPACING;

        for(VirtualCard v: cards.getCards()){

            Rectangle cardNode = v.getCard();
            cardNode.setLayoutX(currentX);
            makeDraggable(cardNode);
            cardsInHandContainer.getChildren().add(cardNode);
            currentX += CARD_WIDTH + SPACING;

        }
    }

    private void unshowHand(){
        cardsInHandContainer.getChildren().clear();
    }


}



