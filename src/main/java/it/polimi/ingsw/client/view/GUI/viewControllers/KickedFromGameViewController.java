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
        ClientController.getInstance().resetModels();
        ClientController.getInstance().sendLeaveMessage();
    }

    /**
     * Loads from the ClientState Model the current state and updates the GUI accordingly.
     */
    @Override
    public void updateSceneStatus(){
        Platform.runLater(()->{
            switch (ClientStateModel.getInstance().getClientState()){
                case LOBBY_STATE -> StageManager.loadWelcomeScene();
                case LOST_CONNECTION_STATE -> StageManager.loadLostConnectionScene();
            }
        });
    }
}
