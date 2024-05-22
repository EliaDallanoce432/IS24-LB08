package it.polimi.ingsw.client.view.CLI;

import it.polimi.ingsw.server.model.card.*;
import it.polimi.ingsw.util.customexceptions.InvalidIdException;
import it.polimi.ingsw.util.supportclasses.Resource;

public class Printer {

    public static void printMessage(String message) {
        System.out.println("--------------------------");
        System.out.println(message);
        System.out.println("--------------------------");
    }

    public static void printCard(int id, boolean facingUp) throws InvalidIdException {

        CardPrinter cardPrinter = new CardPrinter(40, 10, 4, 10);

        if (id <= 0 || id > 102) {
            throw new InvalidIdException("Invalid ID");
        } else if (id <= 40) {

            System.out.println("//RESOURCE CARD #" + id);

            ResourceCard resourceCard = new ResourceCard(id);
            resourceCard.setFacingUp(facingUp);
            cardPrinter.setCardColor(resourceCard.getCardKingdom().toColor());
            drawCorners(cardPrinter, resourceCard.getTopLeftCorner(), resourceCard.getTopRightCorner(), resourceCard.getBottomRightCorner(), resourceCard.getBottomLeftCorner());
            if (!facingUp) cardPrinter.setContent(resourceCard.getCardKingdom().toSymbol(),1);

            cardPrinter.printCard();
            printGuide();

            if (resourceCard.getPoints() != 0 && facingUp)
                System.out.println("Bonus placement points: " + resourceCard.getPoints());
        } else if (id <= 80) {

            System.out.println("\n//GOLD CARD #" + id);

            GoldCard goldCard = new GoldCard(id);
            goldCard.setFacingUp(facingUp);
            cardPrinter.setCardColor(goldCard.getCardKingdom().toColor());
            drawCorners(cardPrinter, goldCard.getTopLeftCorner(), goldCard.getTopRightCorner(), goldCard.getBottomRightCorner(), goldCard.getBottomLeftCorner());
            if (!facingUp) cardPrinter.setContent(goldCard.getCardKingdom().toSymbol(),1);

            cardPrinter.printCard();
            printGuide();

            if (facingUp) {
                System.out.println("Resources needed to place this card: ");
                //TODO inserire risorse e condizioni di placement points
                if (goldCard.getPoints() != 0 && facingUp)
                    System.out.println("Bonus placement points: " + goldCard.getPoints());
            }


        } else if (id <= 86) {

            System.out.println("\n//STARTER CARD #" + id);

            StarterCard starterCard = new StarterCard(id);
            starterCard.setFacingUp(facingUp);
            drawCorners(cardPrinter, starterCard.getTopLeftCorner(), starterCard.getTopRightCorner(), starterCard.getBottomRightCorner(), starterCard.getBottomLeftCorner());

            if(!facingUp) {
                StringBuilder centralResources = new StringBuilder();
                for (Resource r: starterCard.getBackCentralResources()){
                      centralResources.append(r.toSymbol());
                }
                cardPrinter.setContent(centralResources.toString(),starterCard.getBackCentralResources().size());
            }

            cardPrinter.printCard();
            printGuide();


        } else {

            System.out.println("\n//OBJECTIVE CARD #" + id);

        }


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
}
