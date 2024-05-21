package it.polimi.ingsw.client.view.GUI.viewControllers;

import it.polimi.ingsw.client.controller.ClientController;
import it.polimi.ingsw.client.model.ClientStateModel;
import it.polimi.ingsw.client.view.StageManager;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * This class is the controller of the scene where the player creates a new game.
 */

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
    private Label errorLabel;

    /**
     * Initializes the scene.
     */

    @FXML
    public void initialize() {

        okButton.setOnMouseEntered(mouseEvent -> okButton.setCursor(Cursor.HAND));
        okButton.setOnMouseExited(mouseEvent -> okButton.setCursor(Cursor.DEFAULT));
        backButton.setOnMouseEntered(mouseEvent -> backButton.setCursor(Cursor.HAND));
        backButton.setOnMouseExited(mouseEvent -> backButton.setCursor(Cursor.DEFAULT));

        errorLabel.setVisible(false);
        gameNameField.setOnMouseClicked(mouseEvent -> {errorLabel.setVisible(false);});

        gameNameField.setPromptText("Game Name Here");

        numberOfPlayersChoiceBox.getItems().addAll("2", "3", "4");
        numberOfPlayersChoiceBox.setValue("2");
        numberOfPlayersChoiceBox.setOnMouseEntered(mouseEvent -> numberOfPlayersChoiceBox.setCursor(Cursor.HAND));
        numberOfPlayersChoiceBox.setOnMouseExited(mouseEvent -> numberOfPlayersChoiceBox.setCursor(Cursor.DEFAULT));
        numberOfPlayersChoiceBox.setOnAction(event -> {
            int selectedItem = Integer.parseInt(numberOfPlayersChoiceBox.getValue());
        });
    }

    /**
     * Loads the Main Menu scene.
     */

    @FXML
    private void goBack(){

        StageManager.loadWelcomeScene();
    }

    /**
     * Checks the given game Name and sends setupGame message. Otherwise, displays error in the alertLabel.
     */

    @FXML
    private void okPressed() {
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

            ClientController.getInstance().sendSetUpGameMessage(gameName, numberOfPlayers);

        }
    }

    @Override
    public void updateSceneStatus() {
        Platform.runLater(()->{
            switch (ClientStateModel.getInstance().getClientState()){
                case SETUP_STATE -> StageManager.loadWaitForPlayersScene();
                case LOST_CONNECTION_STATE -> StageManager.loadLostConnectionScene();
                default -> {}
            }
        });
    }

    @Override
    public void showErrorMessage(String message) {
        Platform.runLater(()->{
            errorLabel.setText(message);
            errorLabel.setVisible(true);
        });
    }
}
