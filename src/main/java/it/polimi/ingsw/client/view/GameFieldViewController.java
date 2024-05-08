package it.polimi.ingsw.client.view;


import it.polimi.ingsw.client.model.GameFieldModel;
import it.polimi.ingsw.client.model.ObjectivesModel;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class GameFieldViewController extends ViewController {

    @FXML
    private Pane handPane;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private Label alertLabel;
    @FXML
    private ImageView scoreTrackImageView;
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

    @FXML
    private void initialize() {


        cardPlacementController = new CardPlacementController(alertLabel,handPane,scrollPane,
                commonObjectivesPane,secretObjectivePane);

        virtualDeck = new VirtualDeck(decksPane);

        showMessage("Waiting for all players to choose the cards...");

        Platform.runLater(() -> {
            updateGameBoard();
            updateObjectives();
            updateHand();
            updateDecks();
        });

    }



    @FXML
    private void flipCardsInHand() {

        //TODO implementare

    }

    @FXML
    private void leaveGame() throws IOException {

        System.out.println("leaving game");

        Stage stage = (Stage) leaveGameButton.getScene().getWindow();

        stage.setScene(StageManager.loadWelcomeScene());
        stage.show();
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
        Platform.runLater(()-> virtualDeck.loadDecks(false));
    }

    @Override
    public void updateHand(){
        Platform.runLater(()-> cardPlacementController.loadHand());
    }




}



