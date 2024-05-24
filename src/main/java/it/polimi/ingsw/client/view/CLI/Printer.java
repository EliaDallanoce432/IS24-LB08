package it.polimi.ingsw.client.view.CLI;

import it.polimi.ingsw.client.model.DeckModel;
import it.polimi.ingsw.client.model.GameFieldModel;
import it.polimi.ingsw.client.model.HandModel;
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

    public static void printMessage(String message, String textColor) {
        System.out.println("-------------------------------------------------------------");
        System.out.println(textColor + message + ConsoleColor.RESET);
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

        System.out.println("1) Resource deck top card: #?? (" +  new ResourceCard(resourceDeckTopCardId).getCardKingdom().toSymbol() + ")");
        System.out.println("2) Left revealed resource card: #"+ resourceDeckLeftCardId + " ("+ new ResourceCard(resourceDeckLeftCardId).getCardKingdom().toSymbol() +")");
        System.out.println("3) Right revealed resource card: #"+ resourceDeckRightCardId + " ("+ new ResourceCard(resourceDeckRightCardId).getCardKingdom().toSymbol() +")");
        System.out.println("4) Gold deck top card: #?? (" +  new GoldCard(goldDeckTopCardId).getCardKingdom().toSymbol() + ")");
        System.out.println("5) Left revealed gold card: #"+ goldDeckLeftCardId + " ("+ new GoldCard(goldDeckLeftCardId).getCardKingdom().toSymbol() +")");
        System.out.println("6) Right revealed gold card: #"+ goldDeckRightCardId + " ("+ new GoldCard(goldDeckRightCardId).getCardKingdom().toSymbol() +")");
        System.out.println();
    }

    public static void printGameBoard(){
        ArrayList<CardRepresentation> placementHistory = GameFieldModel.getInstance().getPlacementHistory();

        int negativeXBound = -3;
        int positiveXBound = 3;
        int negativeYBound = -3;
        int positiveYBound = 3;

        for(CardRepresentation cardRepresentation : placementHistory){
            if (cardRepresentation.getX() > positiveXBound) positiveXBound = cardRepresentation.getX();
            if (cardRepresentation.getX() < negativeXBound) negativeXBound = cardRepresentation.getX();
            if (cardRepresentation.getY() > positiveYBound) positiveYBound = cardRepresentation.getY();
            if (cardRepresentation.getY() < negativeYBound) negativeYBound = cardRepresentation.getY();
        }

        int width = positiveXBound - negativeXBound;
        int height = positiveYBound - negativeYBound;

        String[][] gameField = new String[width][height];

        for(CardRepresentation cardRepresentation : placementHistory){
            int matrixX = width/2 + cardRepresentation.getX();
            int matrixY = height/2 - cardRepresentation.getY();

            int cardId = cardRepresentation.getId();
            String cardColor = getColor(cardId);


            String faceUpstate;
            if(cardRepresentation.isFacingUp()) faceUpstate = "FRONT";
            else faceUpstate = "BACK";

            gameField[matrixY][matrixX] = cardColor + "|#" + cardId + "(" + faceUpstate  +")|" + ConsoleColor.RESET;
        }

        printMatrix(gameField);


    }

    public static void printHand(){
        ArrayList<CardRepresentation> handArray = HandModel.getInstance().getCardsInHand();
        StringBuilder handString  = new StringBuilder("Your Hand: ");

        for (CardRepresentation c : handArray){
            handString.append("|#" + getColor(c.getId()) + c.getId() + ConsoleColor.RESET +  "| " );
        }

        Printer.printMessage(handString.toString());
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
        System.out.println("(FRONT): the card is placed face up on the field.");
        System.out.println("(BACK): the card is placed face down on the field.");

    }

    public static void printMatrix(String[][] matrix) {
        for (String[] strings : matrix) {
            for (int j = 0; j < strings.length; j++) {
                if (strings[j] != null) System.out.print(strings[j]);
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

    private static String getColor(int id){
        String cardColor;
        if(id > 0 && id <= 40){
            cardColor = new ResourceCard(id).getCardKingdom().toColor();
        }
        else if(id > 40 && id <= 80){
            cardColor = new GoldCard(id).getCardKingdom().toColor();
        }
        else cardColor = ConsoleColor.WHITE;

        return cardColor;
    }
}
