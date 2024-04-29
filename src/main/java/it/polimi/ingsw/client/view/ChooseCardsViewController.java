package it.polimi.ingsw.client.view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class ChooseCardsViewController extends ViewController {

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

    }

    @FXML
    private void goBack() throws IOException {
        Stage stage = (Stage) backButton.getScene().getWindow();

        stage.setScene(SceneLoader.loadWelcomeScene());
        stage.show();
    }

    @FXML
    private void confirmPressed() throws IOException {


        if (!clientController.sendReadyMessage()) showMessage("Someting went wrong");
        else showMessage("ok");


        //TODO choose selection (send message)


    }
}
