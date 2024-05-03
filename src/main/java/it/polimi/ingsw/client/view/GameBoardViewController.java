package it.polimi.ingsw.client.view;


import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class GameBoardViewController extends ViewController {

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

    @FXML
    private void initialize() {


        cardPlacementController = new CardPlacementController(alertLabel,handPane,scrollPane,decksPane,
                commonObjectivesPane,secretObjectivePane);


    }

    @FXML
    private void drawHand() {

//        for(int i=0; i<3 ; i++) {
//            int randomId = (int) (39*random()+1);
//            clientModel.addCardToHand(new VirtualCard(randomId, true));
//
//        }
//        cardPlacementController.showCards(clientModel.getCardsInHand());
    }

    @FXML
    private void clearCards() {
        cardPlacementController.clearBoard();
        initialize();
    }

    @FXML
    public void loadDecks() {
        //cardPlacementController.loadDecks(clientModel.getDecks());
    }

    @FXML
    private void flipCardsInHand() {

        cardPlacementController.unshowCards();

//        for(VirtualCard card : clientModel.getCardsInHand()) {
//            card.flip();
//        }
//
//        cardPlacementController.showCards(clientModel.getCardsInHand());
    }

    @FXML
    private void leaveGame() throws IOException {

        System.out.println("leaving game");

        Stage stage = (Stage) leaveGameButton.getScene().getWindow();

        stage.setScene(SceneLoader.loadWelcomeScene());
        stage.show();
    }

    public void loadInitialBoardState (int starterCardId, int commonObjective1Id, int commonObjective2Id, int secretObjectiveId){
        cardPlacementController.initializeBoard(starterCardId);
        cardPlacementController.loadCommonObjectives(commonObjective1Id,commonObjective2Id);
        cardPlacementController.loadSecretObjective(secretObjectiveId);
    }




}



