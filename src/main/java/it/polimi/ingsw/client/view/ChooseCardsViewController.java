package it.polimi.ingsw.client.view;

import it.polimi.ingsw.client.controller.ClientController;
import it.polimi.ingsw.client.model.SelectableCardsModel;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.IOException;

public class ChooseCardsViewController extends ViewController {



    @FXML
    private Label alertLabel;

    @FXML
    private Pane cardBox;

    @FXML
    public void initialize() {

        showMessage("Waiting for all players to be ready...");

    }

    @FXML
    private void confirmPressed() throws IOException {


        if (!ClientController.getInstance().sendReadyMessage()) showMessage("Someting went wrong");
        else showMessage("ok");


    }

    @Override
    public void updateSelectableCards() {

        showMessage("Choose the starter card side:");

        int starterCardId = SelectableCardsModel.getIstance().getStarterCardId();
        VirtualCard starterCard1 = new VirtualCard(starterCardId,true);
        Rectangle card1 = starterCard1.getCard();


        VirtualCard starterCard2 = new VirtualCard(starterCardId,false);
        Rectangle card2 = starterCard2.getCard();

        card2.setLayoutX(300);


        Button button1 = new Button("Choose");
        button1.setLayoutX(0);
        button1.setLayoutY(170);

        Button button2 = new Button("Choose");
        button2.setLayoutX(300);
        button2.setLayoutY(170);


        button1.setOnAction(event -> {
            ClientController.getInstance().sendChosenStarterCardOrientation(starterCardId,true);
            cardBox.getChildren().clear();
            showObjectiveCards();
        });

        button2.setOnAction(event -> {
            ClientController.getInstance().sendChosenStarterCardOrientation(starterCardId,false);
            cardBox.getChildren().clear();
            showObjectiveCards();
        });

        cardBox.getChildren().addAll(card1, card2, button1, button2);
    }

    public void showObjectiveCards() {

        showMessage("Choose an objective card:");

        int[] ids = SelectableCardsModel.getIstance().getSelectableObjectiveCardsId();

        VirtualCard starterCard1 = new VirtualCard(ids[0],true);
        Rectangle card1 = starterCard1.getCard();


        VirtualCard starterCard2 = new VirtualCard(ids[1],true);
        Rectangle card2 = starterCard2.getCard();

        card2.setLayoutX(300);


        Button button1 = new Button("Choose");
        button1.setLayoutX(0);
        button1.setLayoutY(170);

        Button button2 = new Button("Choose");
        button2.setLayoutX(300);
        button2.setLayoutY(170);


        button1.setOnAction(event -> {
            ClientController.getInstance().sendChosenSecretObjectiveMessage(ids[0]);
            cardBox.getChildren().clear();
            Stage stage = StageManager.getCurrentStage();
            stage.setScene(StageManager.loadGameBoardScene());
            stage.show();

        });

        button2.setOnAction(event -> {
            ClientController.getInstance().sendChosenSecretObjectiveMessage(ids[1]);
            cardBox.getChildren().clear();
            Stage stage = StageManager.getCurrentStage();
            stage.setScene(StageManager.loadGameBoardScene());
            stage.show();
        });

        cardBox.getChildren().addAll(card1, card2, button1, button2);
    }




}
