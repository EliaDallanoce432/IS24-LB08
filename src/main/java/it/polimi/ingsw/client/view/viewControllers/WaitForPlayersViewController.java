package it.polimi.ingsw.client.view.viewControllers;

import it.polimi.ingsw.client.controller.ClientController;
import it.polimi.ingsw.client.model.ClientStateModel;
import it.polimi.ingsw.client.view.StageManager;
import it.polimi.ingsw.util.supportclasses.ClientState;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;

import java.io.IOException;

public class WaitForPlayersViewController extends ViewController {

    @FXML
    private Button backButton;

    @FXML
    private Label alertLabel;

    @FXML
    private Button readyButton;

    @FXML
    private ChoiceBox<String> availableGamesChoiceBox;
    private String selectedGame;

    @FXML
    public void initialize() {

        readyButton.setVisible(false);
        readyButton.setOnMouseEntered(mouseEvent -> readyButton.setCursor(Cursor.HAND));
        readyButton.setOnMouseExited(mouseEvent -> readyButton.setCursor(Cursor.DEFAULT));
        backButton.setVisible(false);
        backButton.setOnMouseEntered(mouseEvent -> backButton.setCursor(Cursor.HAND));
        backButton.setOnMouseExited(mouseEvent -> backButton.setCursor(Cursor.DEFAULT));
        showMessage("Joining Game...");
        //System.out.println("INITIALIZE: " + ClientStateModel.getInstance().getClientState());

        Platform.runLater(this::updateSceneStatus); //ensures that the updateSceneStatus method is executed after the initialization


    }

    @FXML
    private void goBack() throws IOException {

        if (ClientStateModel.getInstance().getClientState() == ClientState.SETUP_STATE) {
            ClientController.getInstance().sendLeaveMessage();
        }

        StageManager.loadWelcomeScene();
    }

    @FXML
    private void readyPressed() throws IOException {

        System.out.println("Ready");

        ClientController.getInstance().sendReadyMessage();

        StageManager.loadChooseCardsScene();


    }

    @FXML
    public void showMessage(String message){
        alertLabel.setText(message);
    }

    @Override
    public void updateSceneStatus(){

        System.out.println("UPDATE STATUS: " + ClientStateModel.getInstance().getClientState());

        switch (ClientStateModel.getInstance().getClientState()) {
            case SETUP_STATE -> loadGetReadyScene();
            case ERROR_JOINING_STATE -> loadErrorJoiningScene();
            default -> {
            }
        }

    }

    private void loadGetReadyScene(){
        Platform.runLater(()-> {
            showMessage("Waiting for other players to join...");
            readyButton.setVisible(true);
            backButton.setVisible(true);
        });
    }

    private void loadErrorJoiningScene(){
        Platform.runLater(()-> {
            showMessage("Error while joining: " + ClientStateModel.getInstance().getReason());
            backButton.setVisible(true);
        });
    }
}
