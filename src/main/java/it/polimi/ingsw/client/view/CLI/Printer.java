package it.polimi.ingsw.client.view.CLI;

import it.polimi.ingsw.server.model.card.*;
import it.polimi.ingsw.util.customexceptions.InvalidIdException;
import it.polimi.ingsw.util.supportclasses.Resource;

import static it.polimi.ingsw.util.supportclasses.ViewConstants.*;

public class Printer {

    public static void printMessage(String message) {
        System.out.println("-----------------------------------------");
        System.out.println(message);
        System.out.println("-----------------------------------------");
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
