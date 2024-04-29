package it.polimi.ingsw.client.view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class JoinGameViewController extends ViewController {



    @FXML
    private Button backButton;

    @FXML
    private Label alertLabel;

    @FXML
    private Button okButton;

    @FXML
    private Button refreshButton;

    @FXML
    private ChoiceBox<String> availableGamesChoiceBox;
    private String selectedGame;

    @FXML
    public void initialize() {


        availableGamesChoiceBox.setOnAction(event -> {
            selectedGame = availableGamesChoiceBox.getSelectionModel().getSelectedItem();
            System.out.println("Selected game: " + selectedGame);
        });
    }

    @FXML
    private void goBack() throws IOException {
        Stage stage = (Stage) backButton.getScene().getWindow();

        stage.setScene(SceneLoader.loadWelcomeScene());
        stage.show();
    }

    @FXML
    private void okPressed() throws IOException {

        if(selectedGame == null){
            alertLabel.setText("Please select a game first");
        }
        else {

            if(!clientController.sendJoinGameMessage(selectedGame)){
                alertLabel.setText("Something went wrong");
            }
            else {
                System.out.println("joined game: " + selectedGame);

                Stage stage = (Stage) backButton.getScene().getWindow();

                stage.setScene(SceneLoader.loadWaitForPlayersScene());
                stage.show();
            }

        }


    }

    @FXML
    private void refreshPressed() throws IOException {
        String[] games = clientController.sendGetAvailableGamesMessage();
        if (games!= null) availableGamesChoiceBox.getItems().addAll(games);

    }
}
