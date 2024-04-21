package it.polimi.ingsw.client.view;


import it.polimi.ingsw.client.model.ClientModel;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

import java.io.IOException;

import static java.lang.Math.random;

public class GameViewController {

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

    private ClientModel clientModel;
    private CardPlacementController cardPlacementController;

    @FXML
    private void initialize() {

        clientModel = new ClientModel("test");
        clientModel.setStarterCardId(81);

        cardPlacementController = new CardPlacementController(alertLabel,clientModel.getCardsInHand(),handPane,scrollPane,decksPane,
                commonObjectivesPane,secretObjectivePane);
        cardPlacementController.initializeBoard(clientModel.getStarterCardId());
        cardPlacementController.loadCommonObjectives(87,97);
        cardPlacementController.loadSecretObjective(102);

    }

    @FXML
    private void drawHand() {

        for(int i=0; i<3 ; i++) {
            int randomId = (int) (39*random()+1);
            clientModel.addCardToHand(new VirtualCard(randomId, true));

        }
        cardPlacementController.showCards(clientModel.getCardsInHand());
    }

    @FXML
    private void clearCards() {
        cardPlacementController.clearBoard();
        initialize();
    }

    @FXML
    private void loadDecks() throws IOException {
        cardPlacementController.loadDecks(clientModel.getDecks());

    }
    @FXML
    private void flipCardsInHand() {

        cardPlacementController.unshowCards();

        for(VirtualCard card : clientModel.getCardsInHand()) {
            card.flip();
        }

        cardPlacementController.showCards(clientModel.getCardsInHand());
    }




}



