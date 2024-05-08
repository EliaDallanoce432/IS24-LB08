package it.polimi.ingsw.client.view;

import it.polimi.ingsw.client.controller.ClientController;
import it.polimi.ingsw.client.model.DeckModel;
import it.polimi.ingsw.server.model.deck.Deck;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

import static it.polimi.ingsw.util.supportclasses.ViewConstants.*;

public class VirtualDeck {

    Pane decksPane;

    public VirtualDeck(Pane decksPane) {
        this.decksPane = decksPane;
    }

    public void loadDecks(boolean showButtons) {

        decksPane.getChildren().clear();

        DeckModel deckModel = DeckModel.getIstance();

        showDeckWithRevealedCards("resourceDeck",0, SPACING, deckModel.getResourceDeckTopCardId(),
                deckModel.getResourceDeckLeftCardId(), deckModel.getResourceDeckRightCardId(), showButtons);

        showDeckWithRevealedCards("goldDeck",Y_GOLD_DECK, SPACING, deckModel.getGoldDeckTopCardId(),
                deckModel.getGoldDeckLeftCardId(), deckModel.getGoldDeckRightCardId(), showButtons );
    }

    public void showDeckWithRevealedCards (String DeckID, double yOffset, double spacing, int topDeckID, int leftCardID, int rightCardID, boolean showButtons) {


        if(topDeckID != 0) {
            VirtualCard topDeck = new VirtualCard(topDeckID, false);
            Rectangle topDeckNode = topDeck.getCard();
            topDeckNode.setLayoutY(yOffset);
            topDeckNode.setLayoutX(spacing);
            decksPane.getChildren().add(topDeckNode);
            if(showButtons) {
                Button button = new Button("Draw");
                button.setOnAction(e -> {
                   handleButtonClick(DeckID + "TopCard");
                });
            }

        }


        if(leftCardID != 0) {
            VirtualCard leftCard = new VirtualCard(leftCardID, true);
            Rectangle leftCardNode = leftCard.getCard();
            leftCardNode.setLayoutY(yOffset);
            leftCardNode.setLayoutX(spacing + CARD_WIDTH + spacing);
            decksPane.getChildren().add(leftCardNode);
            if(showButtons) {
                Button button = new Button("Draw");
                button.setOnAction(e -> {
                    handleButtonClick(DeckID + "LeftCard");
                });
            }
        }


        if(rightCardID != 0) {
            VirtualCard rightCard = new VirtualCard(rightCardID, true);
            Rectangle rightCardNode = rightCard.getCard();
            rightCardNode.setLayoutY(yOffset);
            rightCardNode.setLayoutX(spacing + (2 * (CARD_WIDTH + spacing)));
            decksPane.getChildren().add(rightCardNode);
            if(showButtons) {
                Button button = new Button("Draw");
                button.setOnAction(e -> {
                    handleButtonClick(DeckID + "RightCard");
                });
            }
        }
    }


    public void handleButtonClick(String buttonID){
        switch(buttonID){
            case "resourceDeckTopCard":
                ClientController.getInstance().sendDirectDrawResourceCardMessage();
            case "resourceDeckLeftCard":
                ClientController.getInstance().sendDrawLeftResourceCardMessage();
            case "resourceDeckRightCard":
                ClientController.getInstance().sendDrawRightResourceCardMessage();
            case "goldDeckTopCard":
                ClientController.getInstance().sendDirectDrawGoldCardMessage();
            case "goldDeckLeftCard":
                ClientController.getInstance().sendDrawLeftGoldCardMessage();
            case "goldDeckRightCard":
                ClientController.getInstance().sendDrawRightGoldCardMessage();
            default:
                System.out.println("ERROR: Unknown buttonID: " + buttonID);
        }

    }

}
