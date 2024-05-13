package it.polimi.ingsw.client.view.viewControllers;

import it.polimi.ingsw.client.view.StageManager;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class KickedFromGameViewController extends ViewController{



    @FXML
    private void backToMainScreen(){
        StageManager.loadWelcomeScene();
    }
}
