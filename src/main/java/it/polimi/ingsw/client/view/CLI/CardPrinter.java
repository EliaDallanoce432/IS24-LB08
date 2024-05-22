package it.polimi.ingsw.client.view.CLI;

import it.polimi.ingsw.util.supportclasses.ConsoleColor;

public class CardPrinter {

    private final int width;
    private final int height;
    private final int cornerHeight;
    private final int cornerWidth;
    private final String[][] matrix;

    public CardPrinter(int width, int height, int cornerHeight, int cornerWidth) {
        this.width = width;
        this.height = height;
        this.cornerHeight = cornerHeight;
        this.cornerWidth = cornerWidth;
        this.matrix = new String[height][width];
        setCardColor(ConsoleColor.WHITE);
    }

    public void setCardColor(String cardColor) {
        // Fill the matrix with spaces
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                matrix[i][j] = " ";
            }
        }

        // Create the top and bottom border
        for (int j = 0; j < width; j++) {
            matrix[0][j] = cardColor + "─" + ConsoleColor.RESET;
            matrix[height - 1][j] = cardColor + "─" + ConsoleColor.RESET;
        }

        // Create the left and right border
        for (int i = 0; i < height; i++) {
            matrix[i][0] =cardColor +  "│" + ConsoleColor.RESET;
            matrix[i][width - 1] =cardColor +  "│" + ConsoleColor.RESET;
        }

        // Create the corners
        matrix[0][0] = cardColor + "┌" + ConsoleColor.RESET;
        matrix[0][width - 1] = cardColor + "┐" + ConsoleColor.RESET;
        matrix[height - 1][0] =cardColor +  "└" + ConsoleColor.RESET;
        matrix[height - 1][width - 1] =cardColor +  "┘" + ConsoleColor.RESET; ;
    }

    public void setContent(String content, int contentLength) {
        // Place content in the center of the card
        int contentStartRow = height / 2;
        int contentStartCol = (width - contentLength) / 2;

        matrix[contentStartRow][contentStartCol] = content;

    }

    public void printCard() {
        for (String[] row : matrix) {
            for (String element : row) {
                System.out.print(element);
            }
            System.out.println();
        }
    }

    public void drawTopLeftCorner(String emoji) {
        matrix[cornerHeight][0] = "├";

        for (int i = 1; i < cornerWidth; i++) {
            matrix[cornerHeight][i] = "─";
        }

        matrix[cornerHeight][cornerWidth] = "┘";

        matrix[0][cornerWidth] = "┬";

        for (int i = 1; i < cornerHeight; i++) {
            matrix[i][cornerWidth] = "│";
        }
        matrix[cornerHeight/2][cornerWidth/2] = emoji;
    }

    public void drawTopRightCorner(String emoji) {
        int rightCol = width - cornerWidth - 1;

        matrix[cornerHeight][width - 1] = "┤";

        for (int i = width - 2; i > rightCol; i--) {
            matrix[cornerHeight][i] = "─";
        }

        matrix[cornerHeight][rightCol] = "└";

        matrix[0][rightCol] = "┬";

        for (int i = 1; i < cornerHeight; i++) {
            matrix[i][rightCol] = "│";
        }
        matrix[cornerHeight/2][width -  (cornerWidth/2) - 1] = emoji;
    }

    public void drawBottomLeftCorner(String emoji) {
        int bottomRow = height - cornerHeight - 1;

        matrix[bottomRow][0] = "├";

        for (int i = 1; i < cornerWidth; i++) {
            matrix[bottomRow][i] = "─";
        }

        matrix[bottomRow][cornerWidth] = "┐";

        matrix[height - 1][cornerWidth] = "┴";

        for (int i = bottomRow + 1; i < height - 1; i++) {
            matrix[i][cornerWidth] = "│";
        }
        matrix[height - (cornerHeight/2) - 1][cornerWidth/2] = emoji;
    }

    public void drawBottomRightCorner(String emoji) {
        int bottomRow = height - cornerHeight - 1;
        int rightCol = width - cornerWidth - 1;

        matrix[bottomRow][width - 1] = "┤";

        for (int i = width - 2; i > rightCol; i--) {
            matrix[bottomRow][i] = "─";
        }

        matrix[bottomRow][rightCol] = "┌";

        matrix[height - 1][rightCol] = "┴";

        for (int i = bottomRow + 1; i < height - 1; i++) {
            matrix[i][rightCol] = "│";
        }
        matrix[height - (cornerHeight/2) - 1][width -  (cornerWidth/2) - 1] = emoji;
    }
}