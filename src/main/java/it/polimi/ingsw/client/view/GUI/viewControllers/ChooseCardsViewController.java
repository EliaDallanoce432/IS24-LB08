package it.polimi.ingsw.client.view.GUI.viewControllers;

import it.polimi.ingsw.client.controller.ClientController;
import it.polimi.ingsw.client.model.ClientStateModel;
import it.polimi.ingsw.client.model.SelectableCardsModel;
import it.polimi.ingsw.client.view.StageManager;
import it.polimi.ingsw.client.view.utility.CardRepresentation;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import static it.polimi.ingsw.util.supportclasses.ViewConstants.*;

/**
 * This class is the controller of the scene where the player chooses the starter and objective cards.
 */
public class ChooseCardsViewController extends ViewController {

    @FXML
    private Label alertLabel;

    @FXML
    private Pane cardBox;

    /**
     * Initializes the scene.
     */
    @FXML
    public void initialize() {
        showMessage("Waiting for all players to be ready...");
        Platform.runLater(this::updateSelectableCards);
    }

    /**
     * Shows the starter card and possible secret objectives to be chosen by the player.
     */
    @Override
    public void updateSelectableCards() {
        Platform.runLater(()->{
            if(SelectableCardsModel.getInstance().getStarterCardId()!= 0) {
                showMessage("Choose the starter card side:");

                int starterCardId = SelectableCardsModel.getInstance().getStarterCardId();

                CardRepresentation starterCard = new CardRepresentation(starterCardId, true);
                Rectangle faceUpCard = starterCard.getCard(CHOOSE_CARDS_SCALE);
                starterCard.flip();
                Rectangle faceDownCard = starterCard.getCard(CHOOSE_CARDS_SCALE);

                faceDownCard.setLayoutX((CARD_WIDTH * CHOOSE_CARDS_SCALE) + CHOOSE_CARDS_OFFSET);
                faceUpCard.setOnMouseEntered(mouseEvent -> faceUpCard.setCursor(Cursor.HAND));
                faceUpCard.setOnMouseExited(mouseEvent -> faceUpCard.setCursor(Cursor.DEFAULT));
                faceDownCard.setOnMouseEntered(mouseEvent -> faceDownCard.setCursor(Cursor.HAND));
                faceDownCard.setOnMouseExited(mouseEvent -> faceDownCard.setCursor(Cursor.DEFAULT));

                faceUpCard.setOnMouseClicked(e -> {
                    ClientController.getInstance().sendChosenStarterCardSideMessage(starterCardId, true);
                    showObjectiveCards();
                });
                faceDownCard.setOnMouseClicked(e -> {
                    ClientController.getInstance().sendChosenStarterCardSideMessage(starterCardId, false);
                    showObjectiveCards();
                });

                cardBox.getChildren().addAll(faceUpCard, faceDownCard);
            }
        });
    }

    /**
     * Shows the objective cards to be chosen by the player.
     */
    private void showObjectiveCards() {

        showMessage("Choose your secret objective:");

        int[] ids = SelectableCardsModel.getInstance().getSelectableObjectiveCardsId();

        CardRepresentation objectiveCard1 = new CardRepresentation(ids[0],true);
        Rectangle card1 = objectiveCard1.getCard(CHOOSE_CARDS_SCALE);


        CardRepresentation objectiveCard2 = new CardRepresentation(ids[1],true);
        Rectangle card2 = objectiveCard2.getCard(CHOOSE_CARDS_SCALE);

        card2.setLayoutX((CARD_WIDTH * CHOOSE_CARDS_SCALE) + CHOOSE_CARDS_OFFSET);

        card1.setOnMouseClicked( e -> {
            ClientController.getInstance().sendChosenSecretObjectiveMessage(ids[0]);
            cardBox.getChildren().clear();
            StageManager.loadGameBoardScene();
        });
        card1.setOnMouseEntered(mouseEvent -> card1.setCursor(Cursor.HAND));
        card1.setOnMouseExited(mouseEvent -> card1.setCursor(Cursor.DEFAULT));

        card2.setOnMouseClicked( e -> {
            ClientController.getInstance().sendChosenSecretObjectiveMessage(ids[1]);
            cardBox.getChildren().clear();
            StageManager.loadGameBoardScene();
        });
        card2.setOnMouseEntered(mouseEvent -> card2.setCursor(Cursor.HAND));
        card2.setOnMouseExited(mouseEvent -> card2.setCursor(Cursor.DEFAULT));

        cardBox.getChildren().addAll(card1, card2);
    }

    /**
     * Shows a message in the alertLabel.
     * @param message message to be shown
     */
    @Override
    public void showMessage(String message){
        Platform.runLater(()-> alertLabel.setText(message));
    }

    /**
     * Loads from the ClientState Model the current state and updates the GUI accordingly.
     */
    @Override
    public void updateSceneStatus(){
        Platform.runLater(()->{
            switch (ClientStateModel.getInstance().getClientState()){
                case KICKED_STATE -> StageManager.loadKickedFromGameScene();
                case LOST_CONNECTION_STATE -> StageManager.loadLostConnectionScene();
                default -> {}
            }
        });
    }
}
