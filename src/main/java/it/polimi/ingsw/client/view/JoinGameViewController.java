package it.polimi.ingsw.client.view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class JoinGameViewController {



    @FXML
    private Button backButton;

    @FXML
    private Label alertLabel;

    @FXML
    private Button okButton;

    @FXML
    private ChoiceBox<String> availableGamesChoiceBox;
    private String selectedGame;

    private SceneLoader sceneLoader;

    @FXML
    public void initialize() {

        sceneLoader = new SceneLoader();

        availableGamesChoiceBox.getItems().addAll("carlo" , "piero");
        availableGamesChoiceBox.setOnAction(event -> {
            selectedGame = availableGamesChoiceBox.getSelectionModel().getSelectedItem();
            System.out.println("Selected game: " + selectedGame);
        });
    }

    @FXML
    private void goBack() throws IOException {
        Stage stage = (Stage) backButton.getScene().getWindow();

        stage.setScene(sceneLoader.loadWelcomeScene());
        stage.show();
    }

    @FXML
    private void okPressed() throws IOException {

        if(selectedGame == null){
            alertLabel.setText("Please select a game first");
        }
        else {

            System.out.println("joined game: " + selectedGame);

            Stage stage = (Stage) backButton.getScene().getWindow();

            stage.setScene(sceneLoader.loadWaitForPlayersScene());
            stage.show();

        }


    }
}
