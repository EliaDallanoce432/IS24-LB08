package it.polimi.ingsw.client.view;

import it.polimi.ingsw.client.controller.ClientController;
import it.polimi.ingsw.client.model.AvailableGamesModel;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

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

        stage.setScene(StageManager.loadWelcomeScene());
        stage.show();
    }

    @FXML
    private void okPressed() throws IOException {

        if(selectedGame == null){
            alertLabel.setText("Please select a game first");
        }
        else {

            ClientController.getInstance().sendJoinGameMessage(selectedGame);
            Stage stage = (Stage) backButton.getScene().getWindow();
            stage.setScene(StageManager.loadWaitForPlayersScene());
            stage.show();


        }


    }

    @FXML
    private void refreshPressed() throws IOException {
        availableGamesChoiceBox.getItems().clear();
        ClientController.getInstance().sendGetAvailableGamesMessage();
    }

    @Override
    public void updateAvailableGames(){
        availableGamesChoiceBox.getItems().clear();
        availableGamesChoiceBox.getItems().addAll(AvailableGamesModel.getIstance().getGames());
    }
}
