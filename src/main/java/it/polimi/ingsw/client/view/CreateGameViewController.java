package it.polimi.ingsw.client.view;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class CreateGameViewController {

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

    private SceneLoader sceneLoader;

    @FXML
    public void initialize() {

        sceneLoader = new SceneLoader();


        gameNameField.setPromptText("Game Name Here");

        numberOfPlayersChoiceBox.getItems().addAll("2", "3", "4");
        numberOfPlayersChoiceBox.setValue("2");
        numberOfPlayersChoiceBox.setOnAction(event -> {
            int selectedItem = Integer.parseInt(numberOfPlayersChoiceBox.getValue());
            System.out.println("Selected item: " + selectedItem);
        });
    }

    @FXML
    private void goBack() throws IOException {
        Stage stage = (Stage) backButton.getScene().getWindow();

        stage.setScene(sceneLoader.loadWelcomeScene());
        stage.show();
    }

    @FXML
    private void okPressed(){
        String gameName = gameNameField.getText();
        int numberOfPlayers = Integer.parseInt(numberOfPlayersChoiceBox.getValue());

        if ( gameName.contains(" ") || gameName.equals("\n")) {
            alertLabel.setText("Invalid Game Name (no spaces allowed!)");
        }
        else if (gameName.isEmpty()) {
            alertLabel.setText("Game Name cannot be empty!");
        }
        else{

            System.out.println("gameName: " + gameName + ", numberOfPlayers: " + numberOfPlayers);

            //TODO cambio scena

        }


    }

}
