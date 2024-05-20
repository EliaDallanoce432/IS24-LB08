package it.polimi.ingsw.client.view.viewControllers;

import it.polimi.ingsw.client.view.StageManager;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Button;


/**
 * This class is the controller for the Main Menu Scene.
 */

public class TitleScreenViewController extends ViewController {

    @FXML
    private Button playButton;

    @FXML
    private Button exitButton;

    /**
     * Initializes the scene.
     */
    @FXML
    private void initialize() {
        playButton.setOnMouseEntered(mouseEvent -> playButton.setCursor(Cursor.HAND));
        playButton.setOnMouseExited(mouseEvent -> playButton.setCursor(Cursor.DEFAULT));
        exitButton.setOnMouseEntered(mouseEvent -> exitButton.setCursor(Cursor.HAND));
        exitButton.setOnMouseExited(mouseEvent -> exitButton.setCursor(Cursor.DEFAULT));
    }

    /**
     * Loads the "Connect to server" scene.
     */
    @FXML
    private void playPressed() {
        StageManager.loadConnectToServerScene();
    }


    /**
     * Closes the GUI and shuts down the ClientController.
     */
    @FXML
    private void exit(){
        StageManager.getCurrentStage().close();
    }


}
