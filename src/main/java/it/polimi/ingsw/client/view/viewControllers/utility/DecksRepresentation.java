package it.polimi.ingsw.client.view.viewControllers.utility;

import it.polimi.ingsw.client.controller.ClientController;
import it.polimi.ingsw.client.model.DeckModel;
import javafx.scene.Cursor;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

import static it.polimi.ingsw.util.supportclasses.ViewConstants.*;

/**
 * This class manages the representation of the Decks
 */

public class DecksRepresentation {

    Pane decksPane;

    /**
     * Sets up the Decks Representation
     * @param decksPane the Pane where the decks will be shown
     */

    public DecksRepresentation(Pane decksPane) {
        this.decksPane = decksPane;
    }

    /**
     * Loads the decks from the DeckModel
     */

    public void loadDecks() {

        decksPane.getChildren().clear();

        DeckModel deckModel = DeckModel.getInstance();

        showDeckWithRevealedCards("resourceDeck",0, SPACING, deckModel.getResourceDeckTopCardId(),
                deckModel.getResourceDeckLeftCardId(), deckModel.getResourceDeckRightCardId());

        showDeckWithRevealedCards("goldDeck",Y_GOLD_DECK, SPACING, deckModel.getGoldDeckTopCardId(),
                deckModel.getGoldDeckLeftCardId(), deckModel.getGoldDeckRightCardId());
    }

    /**
     * Shows a Deck with the given proprieties in the Pane
     * @param DeckID String that identifies the type of deck
     * @param yOffset the y coordinate where the deck will be generated
     * @param spacing the spacing between the cards
     * @param topDeckID the ID of the card on top of the deck
     * @param leftCardID the ID of the left revealed card
     * @param rightCardID the ID of the right revealed card
     */

    private void showDeckWithRevealedCards (String DeckID, double yOffset, double spacing, int topDeckID, int leftCardID, int rightCardID) {


        if(topDeckID != 0) {

            //generates the blank cards for the deck representation

            for(int i = 0; i < BLANK_CARDS_NUMBER; i++) {
                CardRepresentation blankCard  = new CardRepresentation(0,false);
                Rectangle blankCardRect = blankCard.getCard();
                blankCardRect.setLayoutY(yOffset + (BLANK_CARDS_NUMBER-i)*BLANK_CARDS_OFFSET);
                blankCardRect.setLayoutX(0);
                decksPane.getChildren().add(blankCardRect);
            }


            CardRepresentation topDeck = new CardRepresentation(topDeckID, false);
            Rectangle topDeckNode = topDeck.getCard();
            topDeckNode.setLayoutY(yOffset);
            topDeckNode.setLayoutX(0);
            topDeckNode.setOnMouseClicked( e -> handleButtonClick(DeckID + "TopCard"));
            topDeckNode.setOnMouseEntered( e -> topDeckNode.setCursor(Cursor.HAND));
            topDeckNode.setOnMouseExited( e -> topDeckNode.setCursor(Cursor.DEFAULT));
            decksPane.getChildren().add(topDeckNode);

        }


        if(leftCardID != 0) {
            CardRepresentation leftCard = new CardRepresentation(leftCardID, true);
            Rectangle leftCardNode = leftCard.getCard();
            leftCardNode.setLayoutY(yOffset);
            leftCardNode.setLayoutX((2*spacing) + CARD_WIDTH + spacing);
            leftCardNode.setOnMouseClicked( e -> handleButtonClick(DeckID + "LeftCard"));
            leftCardNode.setOnMouseEntered( e -> leftCardNode.setCursor(Cursor.HAND));
            leftCardNode.setOnMouseExited( e -> leftCardNode.setCursor(Cursor.DEFAULT));
            decksPane.getChildren().add(leftCardNode);
        }


        if(rightCardID != 0) {
            CardRepresentation rightCard = new CardRepresentation(rightCardID, true);
            Rectangle rightCardNode = rightCard.getCard();
            rightCardNode.setLayoutY(yOffset);
            rightCardNode.setLayoutX( (2*spacing) + (2 * (CARD_WIDTH + spacing)));
            rightCardNode.setOnMouseClicked( e -> handleButtonClick(DeckID + "RightCard"));
            rightCardNode.setOnMouseEntered( e -> rightCardNode.setCursor(Cursor.HAND));
            rightCardNode.setOnMouseExited( e -> rightCardNode.setCursor(Cursor.DEFAULT));
            decksPane.getChildren().add(rightCardNode);
        }
    }

    /**
     * handles the click on a card by sending the relative draw message
     * @param buttonID ID of the button that has been pressed
     */



    private void handleButtonClick(String buttonID){
        switch(buttonID){
            case "resourceDeckTopCard" ->
                ClientController.getInstance().sendDirectDrawResourceCardMessage();
            case "resourceDeckLeftCard" ->
                ClientController.getInstance().sendDrawLeftResourceCardMessage();
            case "resourceDeckRightCard" ->
                ClientController.getInstance().sendDrawRightResourceCardMessage();
            case "goldDeckTopCard" ->
                ClientController.getInstance().sendDirectDrawGoldCardMessage();
            case "goldDeckLeftCard" ->
                ClientController.getInstance().sendDrawLeftGoldCardMessage();
            case "goldDeckRightCard" ->
                ClientController.getInstance().sendDrawRightGoldCardMessage();
            default->
                System.out.println("ERROR: Unknown buttonID: " + buttonID);
        }

    }

}
