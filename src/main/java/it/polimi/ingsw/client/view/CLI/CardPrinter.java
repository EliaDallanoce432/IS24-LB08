package it.polimi.ingsw.client.view.CLI;

import it.polimi.ingsw.server.model.card.*;
import it.polimi.ingsw.util.supportclasses.ConsoleColor;
import it.polimi.ingsw.util.supportclasses.Resource;
import java.util.ArrayList;

/**
 * This class is responsible for generating a text-based representation of a Card object for the Codex game client CLI.
 * It creates a two-dimensional character array representing the card's layout and populates it with symbols and borders based on the card's type and orientation.
 */
public class CardPrinter {
    private final int width;
    private final int height;
    private final int cornerHeight;
    private final int cornerWidth;
    private final String[][] cardMatrix;

    private String cardColor;

    public CardPrinter(int width, int height, int cornerHeight, int cornerWidth) {
        this.width = width;
        this.height = height;
        this.cornerHeight = cornerHeight;
        this.cornerWidth = cornerWidth;
        this.cardMatrix = new String[height][width];
        setCardColor(ConsoleColor.WHITE);
    }

    public String getCardColor() {
        return cardColor;
    }

    public String[][] getCardMatrix() {
        return cardMatrix;
    }

    /**
     * Loads the card representation based on the provided ID and orientation.
     * @param id The ID of the card to be printed.
     * @param facingUp True if the card should be printed face-up, False for face-down.
     */
    public void loadCardRepresentation(int id, boolean facingUp) {
        if (id <= 0 || id > 102) {
            Printer.printMessage("ERROR: invalid card id", ConsoleColor.RED);
            return;
        } 
        if (id <= 40) {
            loadResourceCard(id, facingUp);
        } else if (id <= 80) {
            loadGoldCard(id, facingUp);
        } else if (id <= 86) {
            loadStarterCard(id, facingUp);
        } else {
            loadObjectiveCard(id);
        }
    }

    private void loadObjectiveCard(int id) {
        System.out.println("\n//OBJECTIVE CARD #" + id);
        String [][] matrix = new String[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                matrix[i][j] = " ";
            }
        }

        switch (id) {
            case 87 -> {
                System.out.println("2 points for each inverse diagonal of 3 fungi cards");
                matrix[0][2] = Resource.fungi.toSymbol();
                matrix[1][1] = Resource.fungi.toSymbol();
                matrix[2][0] = Resource.fungi.toSymbol();
                printMatrix(matrix);
            }
            case 88 -> {
                System.out.println("2 points for each direct diagonal of 3 plant cards");
                matrix[0][0] = Resource.plant.toSymbol();
                matrix[1][1] = Resource.plant.toSymbol();
                matrix[2][2] = Resource.plant.toSymbol();
                printMatrix(matrix);
            }
            case 89 -> {
                System.out.println("2 points for each inverse diagonal of 3 animal cards");
                matrix[0][2] = Resource.animal.toSymbol();
                matrix[1][1] = Resource.animal.toSymbol();
                matrix[2][0] = Resource.animal.toSymbol();
                printMatrix(matrix);
            }
            case 90 -> {
                System.out.println("2 points for each direct diagonal of 3 insect cards");
                matrix[0][0] = Resource.insect.toSymbol();
                matrix[1][1] = Resource.insect.toSymbol();
                matrix[2][2] = Resource.insect.toSymbol();
                printMatrix(matrix);
            }
            case 91 -> {
                System.out.println("3 points for each L pattern (Fungi, Fungi, Plant) completed");
                matrix[0][1] = Resource.fungi.toSymbol();
                matrix[1][1] = Resource.fungi.toSymbol();
                matrix[2][2] = Resource.plant.toSymbol();
                printMatrix(matrix);
            }

            case 92 -> {
                System.out.println("3 points for each J pattern (Plant, Plant, Insect) completed");
                matrix[0][1] = Resource.plant.toSymbol();
                matrix[1][1] = Resource.plant.toSymbol();
                matrix[2][0] = Resource.insect.toSymbol();
                printMatrix(matrix);
            }
            case 93 -> {
                System.out.println("3 points for each reversed L pattern (Fungi, Animal, Animal) completed");
                matrix[0][2] = Resource.fungi.toSymbol();
                matrix[1][1] = Resource.animal.toSymbol();
                matrix[2][1] = Resource.animal.toSymbol();
                printMatrix(matrix);
            }
            case 94 -> {
                System.out.println("3 points for each reversed J pattern (Animal, Insect, Insect) completed");
                matrix[0][0] = Resource.animal.toSymbol();
                matrix[1][1] = Resource.insect.toSymbol();
                matrix[2][1] = Resource.insect.toSymbol();
                printMatrix(matrix);
            }
            case 95 -> System.out.println("2 points for each triplet of fungi visible on the game-field");
            case 96 -> System.out.println("2 points for each triplet of plants visible on the game-field");
            case 97 -> System.out.println("2 points for each triplet of animals visible on the game-field");
            case 98 -> System.out.println("2 points for each triplet of insect visible on the game-field");
            case 99 -> System.out.println("3 points for each triplet composed of 1 inkPot, 1 scroll, 1 feather visible on the game-field");
            case 100 -> System.out.println("2 points for each couple of scrolls visible on the game-field");
            case 101 -> System.out.println("2 points for each couple of inkPots visible on the game-field");
            case 102 -> System.out.println("2 points for each couple of feathers visible on the game-field");
        }
    }

    private void loadStarterCard(int id, boolean facingUp) {
        System.out.println("\n//STARTER CARD #" + id);

        StarterCard starterCard = new StarterCard(id);
        starterCard.setFacingUp(facingUp);

        if(starterCard.getCardKingdom()!=null) {
            setCardColor(starterCard.getCardKingdom().toColor());
            if (!starterCard.isFacingUp()) setCenterResources(starterCard.getCardKingdom().toSymbol());
        }
        drawCorners(starterCard.getTopLeftCorner(), starterCard.getTopRightCorner(), starterCard.getBottomRightCorner(), starterCard.getBottomLeftCorner());

        if (!facingUp) setCenterResources(starterCard.getBackCentralResources());
    }

    private void loadGoldCard(int id, boolean facingUp) {
        System.out.println("\n//GOLD CARD #" + id);

        GoldCard goldCard = new GoldCard(id);
        goldCard.setFacingUp(facingUp);

        setCardColor(goldCard.getCardKingdom().toColor());
        if (!goldCard.isFacingUp()) setCenterResources(goldCard.getCardKingdom().toSymbol());

        drawCorners(goldCard.getTopLeftCorner(), goldCard.getTopRightCorner(), goldCard.getBottomRightCorner(), goldCard.getBottomLeftCorner());

        if (!facingUp) setCenterResources(goldCard.getCardKingdom().toSymbol());

        if (facingUp) {
            System.out.println("Resources needed to place this card: \n" +
                    Resource.fungi.toSymbol() + " : "+ goldCard.getRequiredFungiResourceAmount() + "  " +
                    Resource.plant.toSymbol()+ " : " + goldCard.getRequiredPlantResourceAmount() + "  " +
                    Resource.animal.toSymbol()+ " : " + goldCard.getRequiredAnimalResourceAmount() + "  "+
                    Resource.insect.toSymbol()+ " : " + goldCard.getRequiredInsectResourceAmount());
            printGoldBonus(id);
        }
    }

    private void loadResourceCard(int id, boolean facingUp) {
        System.out.println("//RESOURCE CARD #" + id);

        ResourceCard resourceCard = new ResourceCard(id);
        resourceCard.setFacingUp(facingUp);

        setCardColor(resourceCard.getCardKingdom().toColor());
        if (!resourceCard.isFacingUp()) setCenterResources(resourceCard.getCardKingdom().toSymbol());

        drawCorners(resourceCard.getTopLeftCorner(), resourceCard.getTopRightCorner(), resourceCard.getBottomRightCorner(), resourceCard.getBottomLeftCorner());

        if (resourceCard.getPoints() != 0 && facingUp)
            System.out.println("Bonus placement points: " + resourceCard.getPoints());
    }

    /**
     * Prints a 3x3 matrix.
     * @param matrix containing the explanation of the objective card.
     */
    private void printMatrix(String[][] matrix) {
        for (String[] row : matrix) {
            for (String element : row) {
                System.out.print(element);
            }
            System.out.println();
        }
    }

    /**
     * Prints the earned points from the gold card.
     * @param id ID of the gold card.
     */
    private void printGoldBonus (int id) {
        System.out.println("Earned placement points: ");
        switch (id) {
            case 41,51,63,71 -> System.out.println("1 point for each feather");
            case 42,53,61,73 -> System.out.println("1 point for each inkPot");
            case 43,52,62,72 -> System.out.println("1 point for each scroll");
            case 44,45,46,54,55,56,64,65,66,74,75,76 -> System.out.println("2 points for each covered corner");
            case 47,48,49,57,58,59,67,68,69,77,78,79 -> System.out.println("3 points");
            case 50,60,70,80 -> System.out.println("5 points");
        }
    }

    /**
     * Sets the card's border and background color.
     * @param cardColor The color string to be used for the card's borders and background.
     */
    private void setCardColor(String cardColor) {
        this.cardColor = cardColor;

        //Fill the matrix with spaces
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                cardMatrix[i][j] = " ";
            }
        }

        //Create the top and bottom border
        for (int j = 0; j < width; j++) {
            cardMatrix[0][j] = cardColor + "─" + ConsoleColor.RESET;
            cardMatrix[height - 1][j] = cardColor + "─" + ConsoleColor.RESET;
        }

        //Create the left and right border
        for (int i = 0; i < height; i++) {
            cardMatrix[i][0] =cardColor +  "│" + ConsoleColor.RESET;
            cardMatrix[i][width - 1] =cardColor +  "│" + ConsoleColor.RESET;
        }

        //Create the corners
        cardMatrix[0][0] = cardColor + "┌" + ConsoleColor.RESET;
        cardMatrix[0][width - 1] = cardColor + "┐" + ConsoleColor.RESET;
        cardMatrix[height - 1][0] =cardColor +  "└" + ConsoleColor.RESET;
        cardMatrix[height - 1][width - 1] =cardColor +  "┘" + ConsoleColor.RESET;
    }

    /**
     * Sets the central area of the card to display resources or other content.
     * @param content The string representing the content to be displayed in the center of the card.
     */
    private void setCenterResources(String content) {
        int contentStartRow = height / 2;
        int contentStartCol = width / 2 ;
        cardMatrix[contentStartRow][contentStartCol] = content;
    }

    /**
     * Sets the central area of the card to display multiple resources.
     * @param content The `ArrayList` of `Resource` objects representing the resources to be displayed in the center of the card.
     */
    private void setCenterResources(ArrayList<Resource> content) {
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
    /**
     * Prints the text-based representation of the card to the console.
     */
    public void printCard() {
        for (String[] row : cardMatrix) {
            for (String element : row) {
                System.out.print(element);
            }
            System.out.println();
        }
    }

    /**
     * Draws the corners of the card.
     * @param topLeftCorner The `Corner` object representing the top-left corner of the card.
     * @param topRightCorner The `Corner` object representing the top-right corner of the card.
     * @param bottomRightCorner The `Corner` object representing the bottom-right corner of the card.
     * @param bottomLeftCorner The `Corner` object representing the bottom-left corner of the card.
     */
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

    /**
     * Draws the top-left corner of the card.
     * @param content The resource symbol to be displayed in the corner.
     */
    private void drawTopLeftCorner(String content) {
        cardMatrix[cornerHeight][0] = "├";

        for (int i = 1; i < cornerWidth; i++) {
            cardMatrix[cornerHeight][i] = "─";
        }

        cardMatrix[cornerHeight][cornerWidth] = "┘";

        cardMatrix[0][cornerWidth] = "┬";

        for (int i = 1; i < cornerHeight; i++) {
            cardMatrix[i][cornerWidth] = "│";
        }
        cardMatrix[cornerHeight/2][cornerWidth/2] = content;
    }

    /**
     * Draws the top-right corner of the card.
     * @param content The resource symbol to be displayed in the corner.
     */
    private void drawTopRightCorner(String content) {
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
        cardMatrix[cornerHeight/2][width -  (cornerWidth/2) - 1] = content;
    }

    /**
     * Draws the bottom-left corner of the card.
     * @param content The resource symbol to be displayed in the corner.
     */
    private void drawBottomLeftCorner(String content) {
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
        cardMatrix[height - (cornerHeight/2) - 1][cornerWidth/2] = content;
    }
    /**
     * Draws the bottom-right corner of the card.
     * @param content The resource symbol to be displayed in the corner.
     */
    private void drawBottomRightCorner(String content) {
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
        cardMatrix[height - (cornerHeight/2) - 1][width -  (cornerWidth/2) - 1] = content;
    }
}