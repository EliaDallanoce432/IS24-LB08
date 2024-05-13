package it.polimi.ingsw.client.view;


import it.polimi.ingsw.client.controller.ClientController;
import it.polimi.ingsw.client.model.*;
import javafx.application.Platform;
import javafx.fxml.FXML;
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
    private Pane scoreBoardPane;
    @FXML
    private Pane decksPane;
    @FXML
    private HBox commonObjectivesPane;
    @FXML
    private HBox secretObjectivePane;
    @FXML
    private Button leaveGameButton;


    private CardPlacementController cardPlacementController;
    private VirtualDeck virtualDeck;
    private ScoreBoardController scoreBoardController;

    @FXML
    private void initialize() {


        cardPlacementController = new CardPlacementController(alertLabel,handPane,scrollPane,
                commonObjectivesPane,secretObjectivePane);

        virtualDeck = new VirtualDeck(decksPane);

        scoreBoardController = new ScoreBoardController(scoreBoardPane);

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
        Platform.runLater(()->cardPlacementController.loadFromPlacementHistory(GameFieldModel.getIstance().getPlacementHistory()));

    }

    @Override
    public void updateObjectives(){
        Platform.runLater(()->{
            cardPlacementController.loadCommonObjectives(ObjectivesModel.getIstance().getCommonObjectives());
            cardPlacementController.loadSecretObjective(ObjectivesModel.getIstance().getSecretObjectiveId());
        });
    }

    @Override
    public void updateDecks(){
        Platform.runLater(()-> virtualDeck.loadDecks());
    }

    @Override
    public void updateHand(){
        Platform.runLater(()-> cardPlacementController.loadHand());
    }

    @Override
    public void updateScoreBoard(){Platform.runLater(() -> scoreBoardController.updateScores());}

    @Override
    public void updateSceneStatus(){
        Platform.runLater(()->{
            System.out.println("UPDATE STATUS: " + ClientStateModel.getIstance().getClientState());

            switch (ClientStateModel.getIstance().getClientState()){
                case NOT_PLAYING_STATE -> showMessage("Waiting for " + PlayerModel.getInstance().getTurnPlayer() + " to finish their turn...");
                case PLAYING_STATE -> showMessage("It's your Turn, please place a card!");
                case DRAWING_STATE -> {
                    showMessage("Please Draw a Card from the decks!");
                    virtualDeck.loadDecks();
                }
                default -> {}
            }
        });

    }

    @Override
    public void showMessage(String message){
        Platform.runLater(()-> alertLabel.setText(message));

    }




}



