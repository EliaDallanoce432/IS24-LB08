package it.polimi.ingsw.client.view.GUI.viewControllers;

import it.polimi.ingsw.client.controller.ClientController;
import it.polimi.ingsw.client.model.ClientStateModel;
import it.polimi.ingsw.client.view.StageManager;
import it.polimi.ingsw.util.supportclasses.ClientState;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

/**
 * This class controls the "Waiting for players" Scene.
 */
public class WaitForPlayersViewController extends ViewController {

    @FXML
    private Button backButton;

    @FXML
    private Label alertLabel;

    @FXML
    private Button readyButton;


    /**
     * Initializes the scene.
     */
    @FXML
    public void initialize() {

        readyButton.setVisible(false);
        readyButton.setOnMouseEntered(mouseEvent -> readyButton.setCursor(Cursor.HAND));
        readyButton.setOnMouseExited(mouseEvent -> readyButton.setCursor(Cursor.DEFAULT));
        backButton.setVisible(false);
        backButton.setOnMouseEntered(mouseEvent -> backButton.setCursor(Cursor.HAND));
        backButton.setOnMouseExited(mouseEvent -> backButton.setCursor(Cursor.DEFAULT));
        showMessage("Joining Game...");

        Platform.runLater(this::updateSceneStatus); //ensures that the updateSceneStatus method is executed after the initialization


    }

    /**
     * Loads the Main Menu scene and sends a "Leave" message if the player had already successfully joined the game.
     */
    @FXML
    private void goBack(){

        if (ClientStateModel.getInstance().getClientState() == ClientState.GAME_SETUP_STATE) {
            ClientController.getInstance().sendLeaveMessage();
        }
        StageManager.loadWelcomeScene();
    }

    /**
     * Sends a "Ready" message and loads the "ChooseCards" scene.
     */
    @FXML
    private void readyPressed(){

        ClientController.getInstance().sendReadyMessage();
        StageManager.loadChooseCardsScene();

    }

    /**
     * Shows a message in the alertLabel.
     * @param message the message to be shown
     */
    @FXML
    public void showMessage(String message){
        alertLabel.setText(message);
    }

    /**
     * Updates the scene status in the GUI accordingly to the current ClientStateModel.
     */
    @Override
    public void updateSceneStatus(){

        Platform.runLater(()-> {
            switch (ClientStateModel.getInstance().getClientState()) {
                case GAME_SETUP_STATE -> loadGetReadyScene();
                case ERROR_JOINING_STATE -> loadErrorJoiningScene();
                case KICKED_STATE -> StageManager.loadKickedFromGameScene();
                case LOST_CONNECTION_STATE -> StageManager.loadLostConnectionScene();
                default -> {
                }
            }
        });

    }

    /**
     * Loads the "Ready" and "Back" buttons if the player has successfully joined the lobby
     */
    private void loadGetReadyScene(){
        Platform.runLater(()-> {
            showMessage("Waiting for other players to join...");
            readyButton.setVisible(true);
            backButton.setVisible(true);
        });
    }

    /**
     * Loads the "Back" button and shows an error message if something went wrong while trying to join the lobby.
     */
    private void loadErrorJoiningScene(){
        Platform.runLater(()-> {
            showMessage("Error while joining: " + ClientStateModel.getInstance().getReason());
            backButton.setVisible(true);
        });
    }
}
