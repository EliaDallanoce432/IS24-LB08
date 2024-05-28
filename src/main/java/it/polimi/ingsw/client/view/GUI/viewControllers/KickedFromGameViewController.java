package it.polimi.ingsw.client.view.GUI.viewControllers;

import it.polimi.ingsw.client.controller.ClientController;
import it.polimi.ingsw.client.model.ClientStateModel;
import it.polimi.ingsw.client.view.StageManager;
import it.polimi.ingsw.util.supportclasses.ClientState;
import javafx.application.Platform;
import javafx.fxml.FXML;

import java.util.Objects;

/**
 * This class is the controller of the "Kicked from game" scene.
 */
public class KickedFromGameViewController extends ViewController{
    /**
     * Loads the Main Menu scene and updates the clientState model.
     */
    @FXML
    private void backToMainScreen(){
        ClientStateModel.getInstance().setClientState(ClientState.LOBBY_STATE);
        ClientController.getInstance().resetModels();
        StageManager.loadWelcomeScene();
    }

    /**
     * Loads from the ClientState Model the current state and updates the GUI accordingly.
     */
    @Override
    public void updateSceneStatus(){
        Platform.runLater(()->{
            if (Objects.requireNonNull(ClientStateModel.getInstance().getClientState()) == ClientState.LOST_CONNECTION_STATE) {
                StageManager.loadLostConnectionScene();
            }
        });
    }
}
