package it.polimi.ingsw.client.view;


import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

import java.io.IOException;

import static java.lang.Math.random;

public class ClientViewController {

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


    private CardPlacementController cardPlacementController;

    @FXML
    private void initialize() {

        cardPlacementController = new CardPlacementController(alertLabel,handPane,scrollPane,decksPane,
                commonObjectivesPane,secretObjectivePane);
        cardPlacementController.initializeBoard(81);
        cardPlacementController.loadCommonObjectives(87,97);
        cardPlacementController.loadSecretObjective(102);

    }

    @FXML
    private void drawHand() {

        for(int i=0; i<3 ; i++) {
            int randomId = (int) (39*random()+1);
            cardPlacementController.addCardToHand(new VirtualCard(randomId, true));

        }
        cardPlacementController.showCards();
    }

    @FXML
    private void clearCards() {
        cardPlacementController.clearBoard();
        initialize();
    }

    @FXML
    private void loadDecks() throws IOException {
        cardPlacementController.loadDecks(new int[]{1, 10, 22}, new int[]{41, 51, 62});

    }
    @FXML
    private void flipCardsInHand() {

        cardPlacementController.unshowCards();

        for(VirtualCard card : cardPlacementController.getCardsInHand()) {
            card.flip();
        }

        cardPlacementController.showCards();
    }




}



