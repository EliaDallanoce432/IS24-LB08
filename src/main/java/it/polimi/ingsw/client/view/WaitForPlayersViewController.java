package it.polimi.ingsw.client.view;

import it.polimi.ingsw.client.controller.ClientController;
import it.polimi.ingsw.client.model.ClientStateModel;
import it.polimi.ingsw.util.supportclasses.ClientState;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

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
        backButton.setVisible(false);
        showMessage("Joining Game...");

    }

    @FXML
    private void goBack() throws IOException {

        if (ClientStateModel.getIstance().getClientState() == ClientState.WAITING_STATE) {
            //TODO manda messaggio di leave game
        }

        Stage stage = (Stage) backButton.getScene().getWindow();

        stage.setScene(StageManager.loadWelcomeScene());
        stage.show();
    }

    @FXML
    private void readyPressed() throws IOException {

        System.out.println("Ready");

        if (!ClientController.getInstance().sendReadyMessage()) showMessage("Someting went wrong");
        else showMessage("ok");

        Stage stage = (Stage) backButton.getScene().getWindow();
        stage.setScene(StageManager.loadChooseCardsScene());
        stage.show();


    }

    @FXML
    public void showMessage(String message){
        alertLabel.setText(message);
    }

    public void updateSceneStatus(){
        switch (ClientStateModel.getIstance().getClientState()){
            case WAITING_STATE -> loadGetReadyScene();
            case WELCOME_STATE -> loadErrorJoiningScene();
            default -> {}
        }

    }

    private void loadGetReadyScene(){
        showMessage("Waiting for other players to join...");
        readyButton.setVisible(true);
        backButton.setVisible(true);
    }

    private void loadErrorJoiningScene(){
        showMessage("The Game Is Full or it does not exist anymore");
        backButton.setVisible(true);
    }
}
