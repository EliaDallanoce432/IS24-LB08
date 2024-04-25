package it.polimi.ingsw.client.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
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
    @FXML
    private Pane setUsernamePane;

    private TextField usernameTextField;
    private Button confirmButton;

    SceneLoader sceneLoader = new SceneLoader();

    @FXML
    private void joinGame() throws IOException {

        Stage stage = (Stage) joinGameButton.getScene().getWindow();

        stage.setScene(sceneLoader.loadJoinGameScene());
        stage.show();
    }

    @FXML
    private void createGame() throws IOException {
        Stage stage = (Stage) createGameButton.getScene().getWindow();

        stage.setScene(sceneLoader.loadCreateGameScene());
        stage.show();

    }

    @FXML
    private void setUsername() throws IOException {

        joinGameButton.setVisible(false);
        createGameButton.setVisible(false);
        exitButton.setVisible(false);
        setUsernameButton.setVisible(false);

        // Create and configure username text field
        usernameTextField = new TextField();
        usernameTextField.setPromptText("Enter Username");
        setUsernamePane.getChildren().add(usernameTextField);

        confirmButton = new Button("Confirm");
        confirmButton.setLayoutY(100);
        confirmButton.setOnAction(event -> {
            saveUsername();
        });
        setUsernamePane.getChildren().add(confirmButton);

    }

    private void saveUsername() {
        String username = usernameTextField.getText();
        System.out.println("Username: " + username);

        // Show other buttons
        joinGameButton.setVisible(true);
        createGameButton.setVisible(true);
        exitButton.setVisible(true);
        setUsernameButton.setVisible(true);

        // Clear the text field and remove it from the pane
        usernameTextField.clear();
        setUsernamePane.getChildren().remove(usernameTextField);

        // Remove the confirm button from the pane
        setUsernamePane.getChildren().remove(confirmButton);
    }

    @FXML
    private void exit() throws IOException {

        Stage stage = (Stage) joinGameButton.getScene().getWindow();
        stage.close();

    }


}
