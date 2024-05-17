package it.polimi.ingsw.client.view.viewControllers;

import it.polimi.ingsw.client.controller.ClientController;
import it.polimi.ingsw.client.model.AvailableGamesModel;
import it.polimi.ingsw.client.view.StageManager;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

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
    private ComboBox<String> availableGamesComboBox;
    private String selectedGame;

    @FXML
    public void initialize() {


        availableGamesComboBox.setOnAction(event -> {
            selectedGame = availableGamesComboBox.getSelectionModel().getSelectedItem();
            System.out.println("Selected game: " + selectedGame);
        });

    }

    @FXML
    private void goBack() throws IOException {

        StageManager.loadWelcomeScene();
    }

    @FXML
    private void okPressed() throws IOException {

        if(selectedGame == null){
            alertLabel.setText("Please select a game first");
        }
        else {

            ClientController.getInstance().sendJoinGameMessage(selectedGame);
            StageManager.loadWaitForPlayersScene();


        }


    }


    @FXML
    private void refresh()  {
        availableGamesComboBox.getItems().clear();
        ClientController.getInstance().sendGetAvailableGamesMessage();
    }


    @Override
    public void updateAvailableGames(){
        Platform.runLater(()->{
            availableGamesComboBox.getItems().clear();
            availableGamesComboBox.getItems().addAll(AvailableGamesModel.getInstance().getGames());
        });
    }
}
