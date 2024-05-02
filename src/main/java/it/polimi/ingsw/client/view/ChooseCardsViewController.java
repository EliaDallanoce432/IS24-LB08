package it.polimi.ingsw.client.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
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

    }

    @FXML
    private void confirmPressed() throws IOException {


        if (!clientController.sendReadyMessage()) showMessage("Someting went wrong");
        else showMessage("ok");


        //TODO choose selection (send message)


    }

    public void showStarterCard(int id) {
        VirtualCard starterCard1 = new VirtualCard(id,true);
        Rectangle card1 = starterCard1.getCard();


        VirtualCard starterCard2 = new VirtualCard(id,false);
        Rectangle card2 = starterCard2.getCard();

        card2.setLayoutX(300);


        Button button1 = new Button("Choose");
        button1.setLayoutX(0);
        button1.setLayoutY(170);

        Button button2 = new Button("Choose");
        button2.setLayoutX(300);
        button2.setLayoutY(170);


        button1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                clientController.sendChosenStarterCardOrientation(id,true);
            }
        });

        // Button 2 action
        button2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                clientController.sendChosenStarterCardOrientation(id,false);
            }
        });

        cardBox.getChildren().addAll(card1, card2, button1, button2);
    }

    public void showObjectiveCards(int id1, int id2) {
        VirtualCard starterCard1 = new VirtualCard(id1,true);
        Rectangle card1 = starterCard1.getCard();


        VirtualCard starterCard2 = new VirtualCard(id2,true);
        Rectangle card2 = starterCard2.getCard();

        card2.setLayoutX(300);


        Button button1 = new Button("Choose");
        button1.setLayoutX(0);
        button1.setLayoutY(170);

        Button button2 = new Button("Choose");
        button2.setLayoutX(300);
        button2.setLayoutY(170);


        button1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                clientController.sendChosenSecretObjectiveMessage(id1);
            }
        });

        // Button 2 action
        button2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                clientController.sendChosenSecretObjectiveMessage(id2);
            }
        });

        cardBox.getChildren().addAll(card1, card2, button1, button2);
    }




}
