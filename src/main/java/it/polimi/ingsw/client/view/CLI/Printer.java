package it.polimi.ingsw.client.view.CLI;

import it.polimi.ingsw.server.model.card.*;
import it.polimi.ingsw.util.customexceptions.InvalidIdException;

public class Printer {

    public static void printMessage(String message) {
        System.out.println("--------------------------");
        System.out.println(message);
        System.out.println("--------------------------");
    }

    public static void printCard(int id) throws InvalidIdException {

        CardPrinter cardPrinter = new CardPrinter(40, 10, 4, 10);

        if (id <= 0 || id > 102) {
            throw new InvalidIdException("Invalid ID");
        }
        else if ( id <= 40 ) {
            ResourceCard resourceCard = new ResourceCard(id);

            drawCorners(cardPrinter, resourceCard.getTopLeftCorner(), resourceCard.getTopRightCorner(), resourceCard.getBottomRightCorner(), resourceCard.getBottomLeftCorner());
        }
        else if ( id <= 80 ) {

            GoldCard goldCard = new GoldCard(id);

            drawCorners(cardPrinter, goldCard.getTopLeftCorner(), goldCard.getTopRightCorner(), goldCard.getBottomRightCorner(), goldCard.getBottomLeftCorner());

        }
        else if ( id <= 86 ) {

            StarterCard starterCard = new StarterCard(id);

            drawCorners(cardPrinter, starterCard.getTopLeftCorner(), starterCard.getTopRightCorner(), starterCard.getBottomRightCorner(), starterCard.getBottomLeftCorner());


        }
        else {
            //TODO aggiungere spiegazione delle carte obbiettivo
        }

        cardPrinter.printCard();

        System.out.println("\"F\": fungi |" +
                "\"A\": animal |" +
                "\"P\": plant |" +
                "\"I\": insect |" +
                "\"s\": scroll |" +
                "\"i\": inkPot |" +
                "\"f\": feather |"
        );

    }

    private static void drawCorners(CardPrinter cardPrinter, Corner topLeftCorner, Corner topRightCorner, Corner bottomRightCorner, Corner bottomLeftCorner) {
        if (topLeftCorner.isAttachable())
            cardPrinter.drawTopLeftCorner(topLeftCorner.getResource().toSymbol());
        if (topRightCorner.isAttachable())
            cardPrinter.drawTopRightCorner(topRightCorner.getResource().toSymbol());
        if (bottomRightCorner.isAttachable())
            cardPrinter.drawBottomRightCorner(bottomRightCorner.getResource().toSymbol());
        if (bottomLeftCorner.isAttachable())
            cardPrinter.drawBottomLeftCorner(bottomLeftCorner.getResource().toSymbol());
    }
}
