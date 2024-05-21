package it.polimi.ingsw.client.view.GUI.viewControllers;

import it.polimi.ingsw.client.controller.ClientController;
import it.polimi.ingsw.client.view.StageManager;
import javafx.fxml.FXML;

/**
 * This class is the controller of the "Lost Connection With the Server" Scene.
 */
public class LostConnectionViewController extends ViewController {

    /**
     * Closes the current window and shuts down the ClientController.
     */
    @FXML
    private void exit(){
        ClientController.getInstance().resetModels();
        StageManager.getCurrentStage().close();
        ClientController.getInstance().shutdown();
    }
}
