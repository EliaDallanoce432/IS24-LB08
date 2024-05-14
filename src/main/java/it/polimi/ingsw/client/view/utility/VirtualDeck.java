package it.polimi.ingsw.client.view.utility;

import it.polimi.ingsw.client.controller.ClientController;
import it.polimi.ingsw.client.model.DeckModel;
import javafx.scene.Cursor;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

import static it.polimi.ingsw.util.supportclasses.ViewConstants.*;

public class VirtualDeck {

    Pane decksPane;

    public VirtualDeck(Pane decksPane) {
        this.decksPane = decksPane;
    }

    public void loadDecks() {

        decksPane.getChildren().clear();

        DeckModel deckModel = DeckModel.getIstance();

        showDeckWithRevealedCards("resourceDeck",0, SPACING, deckModel.getResourceDeckTopCardId(),
                deckModel.getResourceDeckLeftCardId(), deckModel.getResourceDeckRightCardId());

        showDeckWithRevealedCards("goldDeck",Y_GOLD_DECK, SPACING, deckModel.getGoldDeckTopCardId(),
                deckModel.getGoldDeckLeftCardId(), deckModel.getGoldDeckRightCardId());
    }

    public void showDeckWithRevealedCards (String DeckID, double yOffset, double spacing, int topDeckID, int leftCardID, int rightCardID) {


        if(topDeckID != 0) {

            //generates the blank cards for the deck representation

            for(int i = 0; i < BLANK_CARDS_NUMBER; i++) {
                VirtualCard blankCard  = new VirtualCard(0,false);
                Rectangle blankCardRect = blankCard.getCard();
                blankCardRect.setLayoutX((BLANK_CARDS_NUMBER-i)*BLANK_CARDS_OFFSET);
                blankCardRect.setLayoutY(yOffset);
                decksPane.getChildren().add(blankCardRect);
            }


            VirtualCard topDeck = new VirtualCard(topDeckID, false);
            Rectangle topDeckNode = topDeck.getCard();
            topDeckNode.setLayoutY(yOffset);
            topDeckNode.setLayoutX(0);
            topDeckNode.setOnMouseClicked( e -> handleButtonClick(DeckID + "TopCard"));
            topDeckNode.setOnMouseEntered( e -> topDeckNode.setCursor(Cursor.HAND));
            topDeckNode.setOnMouseExited( e -> topDeckNode.setCursor(Cursor.DEFAULT));
            decksPane.getChildren().add(topDeckNode);

        }


        if(leftCardID != 0) {
            VirtualCard leftCard = new VirtualCard(leftCardID, true);
            Rectangle leftCardNode = leftCard.getCard();
            leftCardNode.setLayoutY(yOffset);
            leftCardNode.setLayoutX((2*spacing) + CARD_WIDTH + spacing);
            leftCardNode.setOnMouseClicked( e -> handleButtonClick(DeckID + "LeftCard"));
            leftCardNode.setOnMouseEntered( e -> leftCardNode.setCursor(Cursor.HAND));
            leftCardNode.setOnMouseExited( e -> leftCardNode.setCursor(Cursor.DEFAULT));
            decksPane.getChildren().add(leftCardNode);
        }


        if(rightCardID != 0) {
            VirtualCard rightCard = new VirtualCard(rightCardID, true);
            Rectangle rightCardNode = rightCard.getCard();
            rightCardNode.setLayoutY(yOffset);
            rightCardNode.setLayoutX( (2*spacing) + (2 * (CARD_WIDTH + spacing)));
            rightCardNode.setOnMouseClicked( e -> handleButtonClick(DeckID + "RightCard"));
            rightCardNode.setOnMouseEntered( e -> rightCardNode.setCursor(Cursor.HAND));
            rightCardNode.setOnMouseExited( e -> rightCardNode.setCursor(Cursor.DEFAULT));
            decksPane.getChildren().add(rightCardNode);
        }
    }


    public void handleButtonClick(String buttonID){
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