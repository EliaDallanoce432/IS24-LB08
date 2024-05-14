package it.polimi.ingsw.client.view.viewControllers;

import it.polimi.ingsw.client.model.ClientStateModel;
import it.polimi.ingsw.client.view.StageManager;
import it.polimi.ingsw.util.supportclasses.ClientState;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class KickedFromGameViewController extends ViewController{



    @FXML
    private void backToMainScreen(){
        ClientStateModel.getInstance().setClientState(ClientState.WELCOME_STATE);
        StageManager.loadWelcomeScene();
    }
}
