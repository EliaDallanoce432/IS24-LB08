package it.polimi.ingsw.client.view.viewControllers;

import it.polimi.ingsw.client.controller.ClientController;
import it.polimi.ingsw.client.model.PlayerModel;
import it.polimi.ingsw.client.view.StageManager;
import it.polimi.ingsw.client.view.observers.*;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

import java.io.IOException;


/**
 * This class is the controller for the Main Menu Scene.
 */

public class WelcomeViewController extends ViewController {

    @FXML
    private Button joinGameButton;
    @FXML
    private Button createGameButton;
    @FXML
    private Button setUsernameButton;
    @FXML
    private Button exitButton;
    @FXML
    private Pane setUsernamePane;
    @FXML
    private Label alertLabel;
    @FXML
    private Label errorLabel;
    @FXML
    private Button confirmUsernameButton;
    @FXML
    private TextField setUsernameTextField;
;

    /**
     * Initializes the scene.
     */
    @FXML
    private void initialize() {

        errorLabel.setVisible(false);
        setUsernameTextField.setVisible(false);
        confirmUsernameButton.setVisible(false);

        Platform.runLater(this::updatePlayerInfo);
    }


    /**
     * Loads the "Join Game" scene.
     */
    @FXML
    private void joinGame(){
        StageManager.loadJoinGameScene();
    }

    /**
     * Loads the "Create Game" scene.
     */
    @FXML
    private void createGame(){
        StageManager.loadCreateGameScene();

    }

    /**
     * Loads the text box and buttons to set a new username.
     * It also checks that the given name is valid (No spaces at the beginning/end and no empty name) before sending
     * a "setUsername" message.
     */
    @FXML
    private void setUsername() {


        joinGameButton.setVisible(false);
        createGameButton.setVisible(false);
        exitButton.setVisible(false);
        setUsernameButton.setVisible(false);


        setUsernameTextField.setVisible(true);
        confirmUsernameButton.setVisible(true);


        confirmUsernameButton.setOnAction(event -> {
            String username = setUsernameTextField.getText();
            if(username.startsWith(" ") || username.endsWith(" ") || username.isEmpty()) {
                showErrorMessage("Invalid Username");
                errorLabel.setVisible(true);
            }
            else {
                ClientController.getInstance().sendSetUsernameMessage(username);

            }
        });


    }

    /**
     * Unloads the setUsername text Box and buttons and loads the updated username on the screen.
     */
    @Override
    public void updatePlayerInfo(){

        Platform.runLater(()->{

            showMessage("User logged in as: " + PlayerModel.getInstance().getUsername());
            // Show other buttons
            joinGameButton.setVisible(true);
            joinGameButton.setOnMouseEntered(mouseEvent -> joinGameButton.setCursor(Cursor.HAND));
            joinGameButton.setOnMouseExited(mouseEvent -> joinGameButton.setCursor(Cursor.DEFAULT));
            createGameButton.setVisible(true);
            createGameButton.setOnMouseEntered(mouseEvent -> createGameButton.setCursor(Cursor.HAND));
            createGameButton.setOnMouseExited(mouseEvent -> createGameButton.setCursor(Cursor.DEFAULT));
            exitButton.setVisible(true);
            exitButton.setOnMouseEntered(mouseEvent -> exitButton.setCursor(Cursor.HAND));
            exitButton.setOnMouseExited(mouseEvent -> exitButton.setCursor(Cursor.DEFAULT));
            setUsernameButton.setVisible(true);
            setUsernameButton.setOnMouseEntered(mouseEvent -> setUsernameButton.setCursor(Cursor.HAND));
            setUsernameButton.setOnMouseExited(mouseEvent -> setUsernameButton.setCursor(Cursor.DEFAULT));

            setUsernameTextField.setVisible(false);
            confirmUsernameButton.setVisible(false);
            errorLabel.setVisible(false);

        });



    }

    /**
     * Closes the GUI and shuts down the ClientController.
     */
    @FXML
    private void exit(){
        ClientController.getInstance().sendLeaveMessage();
        ClientController.getInstance().shutdown();
        StageManager.getCurrentStage().close();
    }

    /**
     * Shows a message in the alertLabel.
     * @param message the message to be shown
     */
    @Override
    @FXML
    public void showMessage(String message) {
        Platform.runLater(()->alertLabel.setText(message));

    }

    /**
     * Shows a error message in the error message label.
     * @param message the message to be shown
     */
    @Override
    @FXML
    public void showErrorMessage(String message) {

        Platform.runLater(()->{
            errorLabel.setText(message);
            errorLabel.setVisible(true);
        });
    }






}
