package it.polimi.ingsw.client.view.viewControllers;

import it.polimi.ingsw.client.view.StageManager;
import javafx.fxml.FXML;

public class KickedFromGameViewController extends ViewController{

    @FXML
    private void backToMainScreen(){
        StageManager.loadWelcomeScene();
    }
}
