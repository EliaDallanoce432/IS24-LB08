package it.polimi.ingsw.client.view.viewControllers;


import it.polimi.ingsw.client.controller.ClientController;
import it.polimi.ingsw.client.model.*;
import it.polimi.ingsw.client.view.utility.HandAndBoardRepresentation;
import it.polimi.ingsw.client.view.utility.ObjectivesRepresentation;
import it.polimi.ingsw.client.view.utility.ScoreBoardRepresentation;
import it.polimi.ingsw.client.view.StageManager;
import it.polimi.ingsw.client.view.utility.DecksRepresentation;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class GameFieldViewController extends ViewController {

    @FXML
    private Pane handPane;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private Label alertLabel;
    @FXML
    private Label specialAlertsLabel;
    @FXML
    private Label errorLabel;
    @FXML
    private Pane scoreBoardPane;
    @FXML
    private Pane decksPane;
    @FXML
    private HBox commonObjectivesPane;
    @FXML
    private HBox secretObjectivePane;
    @FXML
    private Button flipButton;
    @FXML
    private Button leaveGameButton;


    private HandAndBoardRepresentation handAndBoardRepresentation;
    private DecksRepresentation decksRepresentation;
    private ScoreBoardRepresentation scoreBoardRepresentation;
    private ObjectivesRepresentation objectivesRepresentation;

    @FXML
    private void initialize() {

        flipButton.setOnMouseEntered(mouseEvent -> flipButton.setCursor(Cursor.HAND));
        flipButton.setOnMouseExited(mouseEvent -> flipButton.setCursor(Cursor.DEFAULT));
        leaveGameButton.setOnMouseEntered(mouseEvent -> leaveGameButton.setCursor(Cursor.HAND));
        leaveGameButton.setOnMouseExited(mouseEvent -> leaveGameButton.setCursor(Cursor.DEFAULT));

        handAndBoardRepresentation = new HandAndBoardRepresentation(handPane,scrollPane);
        objectivesRepresentation = new ObjectivesRepresentation(commonObjectivesPane, secretObjectivePane);
        decksRepresentation = new DecksRepresentation(decksPane);
        scoreBoardRepresentation = new ScoreBoardRepresentation(scoreBoardPane);

        specialAlertsLabel.setVisible(false);
        errorLabel.setVisible(false);

        showMessage("Waiting for all players to choose the cards...");

        Platform.runLater(() -> {
            updateGameBoard();
            updateObjectives();
            updateHand();
            updateDecks();
            updateSceneStatus();
        });

    }



    @FXML
    private void flipCardsInHand() {

        HandModel.getIstance().flipCardsInHand();

    }

    @FXML
    private void leaveGame() throws IOException {

        System.out.println("leaving game");
        ClientController.getInstance().sendLeaveMessage();
        StageManager.loadWelcomeScene();
    }


    @Override
    public void updateGameBoard(){
        Platform.runLater(()-> handAndBoardRepresentation.loadFromPlacementHistory());

    }

    @Override
    public void updateObjectives(){
        Platform.runLater(()->{
            objectivesRepresentation.loadCommonObjectives();
            objectivesRepresentation.loadSecretObjective();
        });
    }

    @Override
    public void updateDecks(){
        Platform.runLater(()-> decksRepresentation.loadDecks());
    }

    @Override
    public void updateHand(){
        Platform.runLater(()-> handAndBoardRepresentation.loadHand());
    }

    @Override
    public void updateScoreBoard(){Platform.runLater(() -> scoreBoardRepresentation.updateScores());}

    @Override
    public void updateSceneStatus(){
        Platform.runLater(()->{
            System.out.println("UPDATE STATUS: " + ClientStateModel.getInstance().getClientState());
            errorLabel.setVisible(false);
            switch (ClientStateModel.getInstance().getClientState()){
                case NOT_PLAYING_STATE -> {
                    showMessage("Waiting for " + PlayerModel.getInstance().getTurnPlayer() + " to finish their turn...");
                }
                case PLAYING_STATE -> {
                    showMessage("It's your Turn, please place a card!");
                }
                case DRAWING_STATE -> {
                    showMessage("Please Draw a Card from the decks!");
                    decksRepresentation.loadDecks();
                }
                case KICKED_STATE -> StageManager.loadKickedFromGameScene();
                case LOST_CONNECTION_STATE -> StageManager.loadLostConnectionScene();
                case LAST_TURN_STATE -> showSpecialMessage(" It's the last turn! " + ClientStateModel.getInstance().getReason() + "!" );
                case END_GAME_STATE -> StageManager.loadLeaderboardScene();
                default -> {}
            }
        });

    }

    @Override
    public void showMessage(String message){
        Platform.runLater(()-> alertLabel.setText(message));
    }

    public void showSpecialMessage(String message){
        Platform.runLater(() -> {
            specialAlertsLabel.setText(message);
            specialAlertsLabel.setVisible(true);
        });
    }

    @Override
    public void showErrorMessage(String message){
        Platform.runLater(() -> {
            errorLabel.setText("⚠" + message);
            errorLabel.setVisible(true);
        });
    }




}



