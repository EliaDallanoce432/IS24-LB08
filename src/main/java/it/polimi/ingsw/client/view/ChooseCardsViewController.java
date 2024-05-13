package it.polimi.ingsw.client.view;

import it.polimi.ingsw.client.controller.ClientController;
import it.polimi.ingsw.client.model.SelectableCardsModel;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

import static it.polimi.ingsw.util.supportclasses.ViewConstants.*;

public class ChooseCardsViewController extends ViewController {



    @FXML
    private Label alertLabel;

    @FXML
    private Pane cardBox;

    @FXML
    public void initialize() {

        showMessage("Waiting for all players to be ready...");



    }



    @Override
    public void updateSelectableCards() {


            showMessage("Choose the starter card side:");

            int starterCardId = SelectableCardsModel.getIstance().getStarterCardId();

            VirtualCard starterCard = new VirtualCard(starterCardId,true);
            Rectangle faceUpCard = starterCard.getCard(CHOOSE_CARDS_SCALE);
            starterCard.flip();
            Rectangle faceDownCard = starterCard.getCard(CHOOSE_CARDS_SCALE);

            faceDownCard.setLayoutX((CARD_WIDTH * CHOOSE_CARDS_SCALE) + CHOOSE_CARDS_OFFSET);

            faceUpCard.setOnMouseClicked( e -> {
                ClientController.getInstance().sendChosenStarterCardOrientation(starterCardId,true);
                showObjectiveCards();
            });
            faceDownCard.setOnMouseClicked( e -> {
                ClientController.getInstance().sendChosenStarterCardOrientation(starterCardId,false);
                showObjectiveCards();
            });

            cardBox.getChildren().addAll(faceUpCard, faceDownCard);

    }




    public void showObjectiveCards() {

        showMessage("Choose an objective card:");

        int[] ids = SelectableCardsModel.getIstance().getSelectableObjectiveCardsId();

        VirtualCard objectiveCard1 = new VirtualCard(ids[0],true);
        Rectangle card1 = objectiveCard1.getCard(CHOOSE_CARDS_SCALE);


        VirtualCard objectiveCard2 = new VirtualCard(ids[1],true);
        Rectangle card2 = objectiveCard2.getCard(CHOOSE_CARDS_SCALE);

        card2.setLayoutX((CARD_WIDTH * CHOOSE_CARDS_SCALE) + CHOOSE_CARDS_OFFSET);

        card1.setOnMouseClicked( e -> {
            ClientController.getInstance().sendChosenSecretObjectiveMessage(ids[0]);
            cardBox.getChildren().clear();
            StageManager.loadGameBoardScene();
        });

        card2.setOnMouseClicked( e -> {
            ClientController.getInstance().sendChosenSecretObjectiveMessage(ids[1]);
            cardBox.getChildren().clear();
            StageManager.loadGameBoardScene();
        });

        cardBox.getChildren().addAll(card1, card2);
    }

    @Override
    public void showMessage(String message){

        Platform.runLater(()->{
            alertLabel.setText(message);
        });

    }




}
