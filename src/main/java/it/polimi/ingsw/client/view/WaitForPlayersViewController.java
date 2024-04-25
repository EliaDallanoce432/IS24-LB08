package it.polimi.ingsw.client.view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class WaitForPlayersViewController {

    @FXML
    private Button backButton;

    @FXML
    private Label alertLabel;

    @FXML
    private Button readyButton;

    @FXML
    private ChoiceBox<String> availableGamesChoiceBox;
    private String selectedGame;

    private SceneLoader sceneLoader;

    @FXML
    public void initialize() {

        sceneLoader = new SceneLoader();




    }

    @FXML
    private void goBack() throws IOException {
        Stage stage = (Stage) backButton.getScene().getWindow();

        stage.setScene(sceneLoader.loadJoinGameScene());
        stage.show();
    }

    @FXML
    private void readyPressed() throws IOException {

        System.out.println("Ready");

        Stage stage = (Stage) backButton.getScene().getWindow();

        stage.setScene(sceneLoader.loadGameBoardScene());
        stage.show();


    }
}
