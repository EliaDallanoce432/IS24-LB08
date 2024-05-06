package it.polimi.ingsw.client.view;

import it.polimi.ingsw.client.controller.ClientController;
import it.polimi.ingsw.client.view.observers.*;
import it.polimi.ingsw.server.controller.GameObserver;
import it.polimi.ingsw.server.model.deck.Deck;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

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

        new AvailableGamesModelObserver();
        new ClientStateModelObserver();
        new DeckObserver();
        new GameFieldObserver();
        new HandObserver();
        new ObjectivesObserver();
        new PlayerObserver();
        new ScoreBoardObserver();
        new SelectableCardsObserver();



    }



    @FXML
    private void joinGame() throws IOException {

        Stage stage = (Stage) joinGameButton.getScene().getWindow();

        stage.setScene(StageManager.loadJoinGameScene());
        stage.show();
    }

    @FXML
    private void createGame() throws IOException {
        Stage stage = (Stage) createGameButton.getScene().getWindow();

        stage.setScene(StageManager.loadCreateGameScene());
        stage.show();

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
        confirmButton.setLayoutY(100);
        confirmButton.setOnAction(event -> {
            saveUsername();
        });
        setUsernamePane.getChildren().add(confirmButton);

    }

    private void saveUsername() {
        String username = usernameTextField.getText();

        if (!ClientController.getInstance().sendSetUsernameMessage(username)){
            showMessage("Invalid Username, try again");
        }
        else {

            System.out.println("Username: " + username);


            // Show other buttons
            joinGameButton.setVisible(true);
            createGameButton.setVisible(true);
            exitButton.setVisible(true);
            setUsernameButton.setVisible(true);

            // Clear the text field and remove it from the pane
            usernameTextField.clear();
            setUsernamePane.getChildren().remove(usernameTextField);

            // Remove the confirm button from the pane
            setUsernamePane.getChildren().remove(confirmButton);
        }
    }

    @FXML
    private void exit() throws IOException {
        ClientController.getInstance().shutdown();
        StageManager.getCurrentStage().close();
    }

    @Override
    public void showMessage(String message) {
        alertLabel.setText(message);
    }




}
