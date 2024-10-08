package it.polimi.ingsw.client.view.GUI.viewControllers;

import it.polimi.ingsw.client.controller.ClientController;
import it.polimi.ingsw.client.model.AvailableGamesModel;
import it.polimi.ingsw.client.model.ClientStateModel;
import it.polimi.ingsw.client.view.StageManager;
import it.polimi.ingsw.client.view.ViewController;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

/**
 * This class is the controller of the "Join Game" scene.
 */
public class JoinGameViewController extends ViewController {
    @FXML
    private Label alertLabel;
    @FXML
    private ComboBox<String> availableGamesComboBox;
    private String selectedGame;

    /**
     * Initializes the scene.
     */
    @FXML
    public void initialize() {
        availableGamesComboBox.setOnAction(event -> selectedGame = availableGamesComboBox.getSelectionModel().getSelectedItem());
        alertLabel.setVisible(false);
    }

    /**
     * Loads the Main Menu scene.
     */
    @FXML
    private void goBack() {
        StageManager.loadWelcomeScene();
    }

    /**
     * If the player has selected a game, sends a JoinGame message, then loads the "Waiting for players" scene.
     */
    @FXML
    private void okPressed() {
        if(selectedGame == null){
            showErrorMessage("Please select a game first");
        }
        else {
            String gameName = selectedGame.substring(0,selectedGame.length()-8);
            ClientController.getInstance().sendJoinGameMessage(gameName);
        }
    }

    /**
     * Updates the currently available games to join.
     */
    @Override
    public void updateAvailableGames(){
        Platform.runLater(() -> {
            availableGamesComboBox.getItems().clear();
            availableGamesComboBox.getItems().addAll(AvailableGamesModel.getInstance().getGames());
            availableGamesComboBox.setVisibleRowCount(Math.min(availableGamesComboBox.getItems().size(), 5));
        });
    }

    /**
     * Sends a "getAvailableGames" message.
     */
    @FXML
    private void refresh(){
        ClientController.getInstance().sendGetAvailableGamesMessage();
        Platform.runLater(() -> alertLabel.setVisible(false));
    }

    /**
     * Loads from the ClientState Model the current state and updates the GUI accordingly.
     */
    @Override
    public void updateSceneStatus(){
        Platform.runLater(()->{
            switch (ClientStateModel.getInstance().getClientState()){
                case GAME_SETUP_STATE -> StageManager.loadWaitForPlayersScene();
                case LOST_CONNECTION_STATE -> StageManager.loadLostConnectionScene();
                default -> {}
            }
        });
    }

    /**
     * Shows an error message in the alertLabel.
      * @param message The message to be shown.
     */
    @Override
    public void showErrorMessage(String message){
        Platform.runLater(() -> alertLabel.setText(message));
        alertLabel.setVisible(true);
    }
}
