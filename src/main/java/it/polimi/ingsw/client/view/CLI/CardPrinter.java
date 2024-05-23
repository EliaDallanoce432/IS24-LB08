package it.polimi.ingsw.client.view.CLI;

import it.polimi.ingsw.server.model.card.*;
import it.polimi.ingsw.util.customexceptions.InvalidIdException;
import it.polimi.ingsw.util.supportclasses.ConsoleColor;
import it.polimi.ingsw.util.supportclasses.Resource;

import java.util.ArrayList;

public class CardPrinter {

    private final int width;
    private final int height;
    private final int cornerHeight;
    private final int cornerWidth;
    private final String[][] cardMatrix;

    public CardPrinter(int width, int height, int cornerHeight, int cornerWidth) {
        this.width = width;
        this.height = height;
        this.cornerHeight = cornerHeight;
        this.cornerWidth = cornerWidth;
        this.cardMatrix = new String[height][width];
        setCardColor(ConsoleColor.WHITE);
    }

    public String[][] getCardMatrix() {
        return cardMatrix;
    }

    public void loadCardRepresentation(int id, boolean facingUp) throws InvalidIdException {
        if (id <= 0 || id > 102) {
            throw new InvalidIdException("Invalid ID");
        } else if (id <= 40) {

            System.out.println("//RESOURCE CARD #" + id);

            ResourceCard resourceCard = new ResourceCard(id);
            resourceCard.setFacingUp(facingUp);

            setCardColor(resourceCard.getCardKingdom().toColor());
            if (!resourceCard.isFacingUp()) setCenterResources(resourceCard.getCardKingdom().toSymbol());

            drawCorners(resourceCard.getTopLeftCorner(), resourceCard.getTopRightCorner(), resourceCard.getBottomRightCorner(), resourceCard.getBottomLeftCorner());


            if (resourceCard.getPoints() != 0 && facingUp)
                System.out.println("Bonus placement points: " + resourceCard.getPoints());
        } else if (id <= 80) {

            System.out.println("\n//GOLD CARD #" + id);

            GoldCard goldCard = new GoldCard(id);
            goldCard.setFacingUp(facingUp);

            setCardColor(goldCard.getCardKingdom().toColor());
            if (!goldCard.isFacingUp()) setCenterResources(goldCard.getCardKingdom().toSymbol());

            drawCorners(goldCard.getTopLeftCorner(), goldCard.getTopRightCorner(), goldCard.getBottomRightCorner(), goldCard.getBottomLeftCorner());


            if (!facingUp) setCenterResources(goldCard.getCardKingdom().toSymbol());


            if (facingUp) {
                System.out.println("Resources needed to place this card: ");
                //TODO inserire risorse e condizioni di placement points
                if (goldCard.getPoints() != 0)
                    System.out.println("Bonus placement points: " + goldCard.getPoints());
            }


        } else if (id <= 86) {

            System.out.println("\n//STARTER CARD #" + id);

            StarterCard starterCard = new StarterCard(id);
            starterCard.setFacingUp(facingUp);

            if(starterCard.getCardKingdom()!=null) {
                setCardColor(starterCard.getCardKingdom().toColor());
                if (!starterCard.isFacingUp()) setCenterResources(starterCard.getCardKingdom().toSymbol());
            }
            drawCorners(starterCard.getTopLeftCorner(), starterCard.getTopRightCorner(), starterCard.getBottomRightCorner(), starterCard.getBottomLeftCorner());


            if (!facingUp) setCenterResources(starterCard.getBackCentralResources());



        } else {

            System.out.println("\n//OBJECTIVE CARD #" + id);

            System.out.println("Missing Info for this card...");

            //TODO scrivere info delle objective cards


        }
    }






    public void setCardColor(String cardColor) {
        // Fill the matrix with spaces
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                cardMatrix[i][j] = " ";
            }
        }

        // Create the top and bottom border
        for (int j = 0; j < width; j++) {
            cardMatrix[0][j] = cardColor + "─" + ConsoleColor.RESET;
            cardMatrix[height - 1][j] = cardColor + "─" + ConsoleColor.RESET;
        }

        // Create the left and right border
        for (int i = 0; i < height; i++) {
            cardMatrix[i][0] =cardColor +  "│" + ConsoleColor.RESET;
            cardMatrix[i][width - 1] =cardColor +  "│" + ConsoleColor.RESET;
        }

        // Create the corners
        cardMatrix[0][0] = cardColor + "┌" + ConsoleColor.RESET;
        cardMatrix[0][width - 1] = cardColor + "┐" + ConsoleColor.RESET;
        cardMatrix[height - 1][0] =cardColor +  "└" + ConsoleColor.RESET;
        cardMatrix[height - 1][width - 1] =cardColor +  "┘" + ConsoleColor.RESET; ;
    }

    public void setCenterResources(String content) {
        int contentStartRow = height / 2;
        int contentStartCol = width / 2 ;

        cardMatrix[contentStartRow][contentStartCol] = content;

    }

    public void setCenterResources(ArrayList<Resource> content) {
        int contentStartRow = height / 2;
        int contentStartCol;

        if (content.size()>1) contentStartCol = (width - content.size()) / 2;
        else contentStartCol = width/2;

        int i=0;
        for(Resource r : content){
            cardMatrix[contentStartRow][contentStartCol+i] = r.toSymbol();
            i++;
        }


    }

    public void printCard() {
        for (String[] row : cardMatrix) {
            for (String element : row) {
                System.out.print(element);
            }
            System.out.println();
        }
    }


    private void drawCorners(Corner topLeftCorner, Corner topRightCorner, Corner bottomRightCorner, Corner bottomLeftCorner) {
        if (topLeftCorner.isAttachable())
            drawTopLeftCorner(topLeftCorner.getResource().toSymbol());
        if (topRightCorner.isAttachable())
            drawTopRightCorner(topRightCorner.getResource().toSymbol());
        if (bottomRightCorner.isAttachable())
            drawBottomRightCorner(bottomRightCorner.getResource().toSymbol());
        if (bottomLeftCorner.isAttachable())
            drawBottomLeftCorner(bottomLeftCorner.getResource().toSymbol());
    }


    public void drawTopLeftCorner(String emoji) {
        cardMatrix[cornerHeight][0] = "├";

        for (int i = 1; i < cornerWidth; i++) {
            cardMatrix[cornerHeight][i] = "─";
        }

        cardMatrix[cornerHeight][cornerWidth] = "┘";

        cardMatrix[0][cornerWidth] = "┬";

        for (int i = 1; i < cornerHeight; i++) {
            cardMatrix[i][cornerWidth] = "│";
        }
        cardMatrix[cornerHeight/2][cornerWidth/2] = emoji;
    }

    public void drawTopRightCorner(String emoji) {
        int rightCol = width - cornerWidth - 1;

        cardMatrix[cornerHeight][width - 1] = "┤";

        for (int i = width - 2; i > rightCol; i--) {
            cardMatrix[cornerHeight][i] = "─";
        }

        cardMatrix[cornerHeight][rightCol] = "└";

        cardMatrix[0][rightCol] = "┬";

        for (int i = 1; i < cornerHeight; i++) {
            cardMatrix[i][rightCol] = "│";
        }
        cardMatrix[cornerHeight/2][width -  (cornerWidth/2) - 1] = emoji;
    }

    public void drawBottomLeftCorner(String emoji) {
        int bottomRow = height - cornerHeight - 1;

        cardMatrix[bottomRow][0] = "├";

        for (int i = 1; i < cornerWidth; i++) {
            cardMatrix[bottomRow][i] = "─";
        }

        cardMatrix[bottomRow][cornerWidth] = "┐";

        cardMatrix[height - 1][cornerWidth] = "┴";

        for (int i = bottomRow + 1; i < height - 1; i++) {
            cardMatrix[i][cornerWidth] = "│";
        }
        cardMatrix[height - (cornerHeight/2) - 1][cornerWidth/2] = emoji;
    }

    public void drawBottomRightCorner(String emoji) {
        int bottomRow = height - cornerHeight - 1;
        int rightCol = width - cornerWidth - 1;

        cardMatrix[bottomRow][width - 1] = "┤";

        for (int i = width - 2; i > rightCol; i--) {
            cardMatrix[bottomRow][i] = "─";
        }

        cardMatrix[bottomRow][rightCol] = "┌";

        cardMatrix[height - 1][rightCol] = "┴";

        for (int i = bottomRow + 1; i < height - 1; i++) {
            cardMatrix[i][rightCol] = "│";
        }
        cardMatrix[height - (cornerHeight/2) - 1][width -  (cornerWidth/2) - 1] = emoji;
    }
}