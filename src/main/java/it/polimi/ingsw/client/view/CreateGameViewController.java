package it.polimi.ingsw.client.view;

import it.polimi.ingsw.client.controller.ClientController;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class CreateGameViewController extends ViewController {

    @FXML
    private TextField gameNameField;

    @FXML
    private Button backButton;

    @FXML
    private Label alertLabel;

    @FXML
    private Button okButton;

    @FXML
    private ChoiceBox<String> numberOfPlayersChoiceBox;


    @FXML
    public void initialize() {


        gameNameField.setPromptText("Game Name Here");

        numberOfPlayersChoiceBox.getItems().addAll("2", "3", "4");
        numberOfPlayersChoiceBox.setValue("2");
        numberOfPlayersChoiceBox.setOnAction(event -> {
            int selectedItem = Integer.parseInt(numberOfPlayersChoiceBox.getValue());
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
        String gameName = gameNameField.getText();
        int numberOfPlayers = Integer.parseInt(numberOfPlayersChoiceBox.getValue());

        if ( gameName.startsWith(" ") || gameName.equals("\n") || gameName.endsWith(" ") ) {
            alertLabel.setText("Invalid Game Name (no spaces allowed!)");
        }
        else if (gameName.isEmpty()) {
            alertLabel.setText("Game Name cannot be empty!");
        }
        else if (numberOfPlayers < 2) {
            alertLabel.setText("Number of Players must be greater than 2");
        }

        else{

            if (!ClientController.getInstance().sendSetUpGameMessage(gameName, numberOfPlayers)) showMessage("ERROR");

            else {

                System.out.println("created game: " + gameName + ", numberOfPlayers: " + numberOfPlayers);

                System.out.println("joining game: " + gameName);

                Stage stage = (Stage) backButton.getScene().getWindow();

                stage.setScene(StageManager.loadWaitForPlayersScene());
                stage.show();
            }

        }


    }

}
