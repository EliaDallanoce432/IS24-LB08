package it.polimi.ingsw.client.view;

import it.polimi.ingsw.client.controller.ClientController;
import javafx.fxml.FXML;

import java.io.IOException;

public class LostConnectionViewController {

    @FXML
    private void exit() throws IOException {
        ClientController.getInstance().shutdown();
        StageManager.getCurrentStage().close();
    }
}
