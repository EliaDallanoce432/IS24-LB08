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

    private TextField usernameTextField;
    private Button confirmButton;

    @FXML
    private void initialize() {

        //initializing observers

        new AvailableGamesModelObserver();
        new ClientStateModelObserver();
        new DeckObserver();
        new GameFieldObserver();
        new HandObserver();
        new ObjectivesObserver();
        new PlayerObserver();
        new ScoreBoardObserver();
        new SelectableCardsObserver();
        Platform.runLater(this::updatePlayerInfo);
    }



    @FXML
    private void joinGame() throws IOException {

        StageManager.loadJoinGameScene();
    }

    @FXML
    private void createGame() throws IOException {
        StageManager.loadCreateGameScene();

    }

    @FXML
    private void setUsername() throws IOException {


        joinGameButton.setVisible(false);
        createGameButton.setVisible(false);
        exitButton.setVisible(false);
        setUsernameButton.setVisible(false);

        // Create and configure username text field
        usernameTextField = new TextField();
        usernameTextField.setPromptText("Enter Username");
        setUsernamePane.getChildren().add(usernameTextField);

        confirmButton = new Button("Confirm");
        confirmButton.setOnMouseEntered(mouseEvent -> confirmButton.setCursor(Cursor.HAND));
        confirmButton.setOnMouseExited(mouseEvent -> confirmButton.setCursor(Cursor.DEFAULT));
        confirmButton.setLayoutY(100);
        confirmButton.setOnAction(event -> {
            saveUsername();
        });
        setUsernamePane.getChildren().add(confirmButton);


    }

    private void saveUsername() {

        String username = usernameTextField.getText();

        ClientController.getInstance().sendSetUsernameMessage(username);
    }

    @Override
    public void updatePlayerInfo(){

        Platform.runLater(()->{

            System.out.println("User logged in as: " + PlayerModel.getInstance().getUsername());
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

            // Clear the text field and remove it from the pane
            if(usernameTextField!=null) usernameTextField.clear();
            setUsernamePane.getChildren().remove(usernameTextField);

            // Remove the confirm button from the pane
            setUsernamePane.getChildren().remove(confirmButton);

        });



    }

    @FXML
    private void exit() throws IOException {
        ClientController.getInstance().sendLeaveMessage();
        ClientController.getInstance().shutdown();
        StageManager.getCurrentStage().close();
    }

    @Override
    @FXML
    public void showMessage(String message) {
        alertLabel.setText(message);

    }






}