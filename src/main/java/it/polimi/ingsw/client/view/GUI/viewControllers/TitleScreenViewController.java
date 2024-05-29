package it.polimi.ingsw.client.view.GUI.viewControllers;

import it.polimi.ingsw.client.view.StageManager;
import it.polimi.ingsw.client.view.ViewController;
import javafx.fxml.FXML;

/**
 * This class is the controller for the Main Menu Scene.
 */
public class TitleScreenViewController extends ViewController {
    /**
     * Initializes the scene.
     */
    @FXML
    private void initialize() {}

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