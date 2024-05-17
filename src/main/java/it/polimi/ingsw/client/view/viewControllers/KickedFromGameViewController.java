package it.polimi.ingsw.client.view.viewControllers;

import it.polimi.ingsw.client.model.ClientStateModel;
import it.polimi.ingsw.client.view.StageManager;
import it.polimi.ingsw.util.supportclasses.ClientState;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

/**
 * This class is the controller of the "Kicked from game" scene.
 */
public class KickedFromGameViewController extends ViewController{


    /**
     * Loads the Main Menu scene and updates the clientState model.
     */
    @FXML
    private void backToMainScreen(){
        ClientStateModel.getInstance().setClientState(ClientState.WELCOME_STATE);
        StageManager.loadWelcomeScene();
    }
}
