package it.polimi.ingsw.client.view;

import it.polimi.ingsw.client.controller.ClientController;
import javafx.fxml.FXML;

import java.io.IOException;

public class LostConnectionViewController extends ViewController {

    @FXML
    private void exit() throws IOException {
        StageManager.getCurrentStage().close();
        ClientController.getInstance().shutdown();
    }
}