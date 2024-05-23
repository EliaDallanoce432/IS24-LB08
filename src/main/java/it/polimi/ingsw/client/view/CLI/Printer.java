package it.polimi.ingsw.client.view.CLI;

import it.polimi.ingsw.client.model.DeckModel;
import it.polimi.ingsw.client.model.GameFieldModel;
import it.polimi.ingsw.client.view.GUI.viewControllers.utility.CardRepresentation;
import it.polimi.ingsw.server.model.card.*;
import it.polimi.ingsw.util.customexceptions.InvalidIdException;
import it.polimi.ingsw.util.supportclasses.ConsoleColor;
import it.polimi.ingsw.util.supportclasses.Resource;

import java.util.ArrayList;

import static it.polimi.ingsw.util.supportclasses.ViewConstants.*;

public class Printer {

    public static void printMessage(String message) {
        System.out.println("-------------------------------------------------------------");
        System.out.println(message);
        System.out.println("-------------------------------------------------------------");
    }

    public static void printCardInfo(int id, boolean facingUp) throws InvalidIdException {

        CardPrinter cardPrinter = new CardPrinter(CLI_CARD_WIDTH, CLI_CARD_HEIGHT, CLI_CORNER_HEIGHT, CLI_CORNER_WIDTH);
        cardPrinter.loadCardRepresentation(id,facingUp);
        cardPrinter.printCard();

    }

    public static void printSelectableCards(int id1, boolean facingup1, int id2, boolean facingup2) throws InvalidIdException {
        CardPrinter cardPrinter1 = new CardPrinter(CLI_CARD_WIDTH, CLI_CARD_HEIGHT, CLI_CORNER_HEIGHT, CLI_CORNER_WIDTH);
        CardPrinter cardPrinter2 = new CardPrinter(CLI_CARD_WIDTH, CLI_CARD_HEIGHT, CLI_CORNER_HEIGHT, CLI_CORNER_WIDTH);

        cardPrinter1.loadCardRepresentation(id1,facingup1);
        cardPrinter2.loadCardRepresentation(id2,facingup2);

        printMatricesHorizontally(cardPrinter1.getCardMatrix(), cardPrinter2.getCardMatrix());
    }

    public static void printDeckInfo () {
        DeckModel deckModel = DeckModel.getInstance();
        int resourceDeckTopCardId = deckModel.getResourceDeckTopCardId();
        int resourceDeckLeftCardId = deckModel.getResourceDeckLeftCardId();
        int resourceDeckRightCardId= deckModel.getResourceDeckRightCardId();
        int goldDeckTopCardId = deckModel.getGoldDeckTopCardId();
        int goldDeckLeftCardId = deckModel.getGoldDeckLeftCardId();
        int goldDeckRightCardId = deckModel.getGoldDeckRightCardId();

        System.out.println("1) Resource deck top card: " +  new ResourceCard(resourceDeckTopCardId).getCardKingdom().toSymbol());
        System.out.println("2) Left revealed resource card: #"+ resourceDeckLeftCardId + " ("+ new ResourceCard(resourceDeckLeftCardId).getCardKingdom().toSymbol() +")");
        System.out.println("3) Right revealed resource card: #"+ resourceDeckRightCardId + " ("+ new ResourceCard(resourceDeckRightCardId).getCardKingdom().toSymbol() +")");
        System.out.println("4) Gold deck top card: " +  new GoldCard(goldDeckTopCardId).getCardKingdom().toSymbol());
        System.out.println("5) Left revealed gold card: #"+ goldDeckLeftCardId + " ("+ new GoldCard(goldDeckLeftCardId).getCardKingdom().toSymbol() +")");
        System.out.println("6) Right revealed gold card: #"+ goldDeckRightCardId + " ("+ new GoldCard(goldDeckRightCardId).getCardKingdom().toSymbol() +")");
        System.out.println();
    }

    public static void printGameBoard(){
        ArrayList<CardRepresentation> placementHistory = GameFieldModel.getInstance().getPlacementHistory();

        int negativeXBound = 0;
        int positiveXBound = 0;
        int negativeYBound = 0;
        int positiveYBound = 0;

        for(CardRepresentation cardRepresentation : placementHistory){
            if (cardRepresentation.getX() > positiveXBound) positiveXBound = cardRepresentation.getX();
            if (cardRepresentation.getX() < negativeXBound) negativeXBound = cardRepresentation.getX();
            if (cardRepresentation.getY() > positiveYBound) positiveYBound = cardRepresentation.getY();
            if (cardRepresentation.getY() < negativeYBound) negativeYBound = cardRepresentation.getY();
        }

        int width = positiveXBound - negativeXBound;
        int height = positiveYBound - negativeYBound;

        String[][] gameField = new String[width][height];
        CardPrinter cardPrinter = new CardPrinter(0,0,0,0);

        for(CardRepresentation cardRepresentation : placementHistory){
            int matrixX = cardRepresentation.getX() + width/2;
            int matrixY = cardRepresentation.getY() + height/2;

            int cardId = cardRepresentation.getId();

            try {
                cardPrinter.loadCardRepresentation(cardId,true);
            } catch (InvalidIdException e) {
                System.out.println("can't load card representation");
            }
            String cardColor = cardPrinter.getCardColor();
            String faceUpstate;
            if(cardRepresentation.isFacingUp()) faceUpstate = "F";
            else faceUpstate = "B";

            gameField[matrixX][matrixY] = cardColor + "|#" + cardId + "(" + faceUpstate  +")|" + ConsoleColor.RESET;
        }

        printMatrix(gameField);


    }

    public static void printGuide() {
        System.out.println(
                        Resource.fungi.toSymbol() + ": fungi | " +
                        Resource.animal.toSymbol() + ": animal | " +
                        Resource.insect.toSymbol() + ": insect | " +
                        Resource.plant.toSymbol() + ": plant | " +
                        Resource.scroll.toSymbol() + ": scroll | " +
                        Resource.inkPot.toSymbol() + ": inkPot | " +
                        Resource.feather.toSymbol() + ": feather"
        );
    }

    public static void printMatrix(String[][] matrix) {
        for (String[] strings : matrix) {
            for (int j = 0; j < strings.length; j++) {
                System.out.print(strings[j]);
                if (j < strings.length - 1) {
                    System.out.print("\t");
                }
            }
            System.out.println();
        }
    }


    private static void printMatricesHorizontally(String[][] matrix1, String[][] matrix2) {

        int rows1 = matrix1.length;
        int rows2 = matrix2.length;

        if (rows1 != rows2) {
            return;
        }

        for (int i = 0; i < rows1; i++) {
            for (int j = 0; j < matrix1[i].length; j++) {
                System.out.print(matrix1[i][j]);
            }

            System.out.print("\t\t");

            for (int j = 0; j < matrix2[i].length; j++) {
                System.out.print(matrix2[i][j]);
            }

            System.out.println();
        }
    }
}
