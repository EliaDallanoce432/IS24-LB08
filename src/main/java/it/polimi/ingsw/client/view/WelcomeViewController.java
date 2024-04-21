package it.polimi.ingsw.client.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;

public class WelcomeViewController {

    @FXML
    private Button joinGameButton;
    @FXML
    private Button createGameButton;
    @FXML
    private Button setUsernameButton;
    @FXML
    private Button exitButton;

    SceneLoader sceneLoader = new SceneLoader();

    @FXML
    private void joinGame() throws IOException {

        Stage stage = (Stage) joinGameButton.getScene().getWindow();

        stage.setScene(sceneLoader.loadGameScene());
        stage.show();
    }

    @FXML
    private void createGame() throws IOException {

    }

    @FXML
    private void setUsername() throws IOException {

    }

    @FXML
    private void exit() throws IOException {

        Stage stage = (Stage) joinGameButton.getScene().getWindow();
        stage.close();

    }


}
