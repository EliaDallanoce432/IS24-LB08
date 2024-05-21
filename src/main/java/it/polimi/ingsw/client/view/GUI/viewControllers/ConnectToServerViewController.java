package it.polimi.ingsw.client.view.GUI.viewControllers;

import it.polimi.ingsw.client.controller.ClientController;
import it.polimi.ingsw.client.model.ClientStateModel;
import it.polimi.ingsw.client.view.StageManager;
import it.polimi.ingsw.util.customexceptions.ServerUnreachableException;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * This class is the controller of the scene where the player creates a new game.
 */

public class ConnectToServerViewController extends ViewController {

    @FXML
    private TextField ipTextField;

    @FXML
    private TextField portTextField;

    @FXML
    private Button exitButton;

    @FXML
    private Label alertLabel;

    @FXML
    private Button okButton;



    /**
     * Initializes the scene.
     */


    @FXML
    public void initialize() {

        okButton.setOnMouseEntered(mouseEvent -> okButton.setCursor(Cursor.HAND));
        okButton.setOnMouseExited(mouseEvent -> okButton.setCursor(Cursor.DEFAULT));
        exitButton.setOnMouseEntered(mouseEvent -> exitButton.setCursor(Cursor.HAND));
        exitButton.setOnMouseExited(mouseEvent -> exitButton.setCursor(Cursor.DEFAULT));
    }

    /**
     * Closes the GUI.
     */

    @FXML
    private void exit(){
        StageManager.getCurrentStage().close();
    }

    /**
     * Checks the given game Name and sends setupGame message. Otherwise, displays error in the alertLabel.
     */

    @FXML
    private void okPressed() {

        String givenIp = ipTextField.getText();
        String givenPort = portTextField.getText();

        if (givenIp.isEmpty()) givenIp = "localhost";
        if (givenPort.isEmpty()) givenPort = "12345";


        try {
            ClientController.getInstance(givenIp, Integer.parseInt(givenPort));
        } catch (ServerUnreachableException e) {
            showErrorMessage("Server unreachable");
        }


    }

    @Override
    public void updateSceneStatus(){
        Platform.runLater(()->{
            switch (ClientStateModel.getInstance().getClientState()){
                case WELCOME_STATE -> StageManager.loadWelcomeScene();
                default -> {}
            }
        });
    }

    @Override
    public void showErrorMessage(String errorMessage) {
       Platform.runLater(()->alertLabel.setText(errorMessage));
    }

}
