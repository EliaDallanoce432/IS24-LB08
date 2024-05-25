package it.polimi.ingsw.client.view.CLI;

import it.polimi.ingsw.client.model.*;
import it.polimi.ingsw.client.view.GUI.viewControllers.utility.CardRepresentation;
import it.polimi.ingsw.server.model.card.GoldCard;
import it.polimi.ingsw.server.model.card.ResourceCard;
import it.polimi.ingsw.util.customexceptions.InvalidIdException;
import it.polimi.ingsw.util.supportclasses.ConsoleColor;
import it.polimi.ingsw.util.supportclasses.Resource;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

import static it.polimi.ingsw.util.supportclasses.Constants.MENU_HEADER;
import static it.polimi.ingsw.util.supportclasses.ViewConstants.*;

/**
 *  This class provides a variety of methods for printing information to the console.
 */
public class Printer {

    /**
     * Prints a message to the console with a border.
     * @param message The message to print.
     */
    public static void printMessage(String message) {
        System.out.println("-------------------------------------------------------------");
        System.out.println(message);
        System.out.println("-------------------------------------------------------------");
    }

    /**
     * Prints a message to the console with a border and a specified text color.
     * @param message The message to print.
     * @param textColor The ANSI escape code for the desired text color.
     */
    public static void printMessage(String message, String textColor) {
        System.out.println("-------------------------------------------------------------");
        System.out.println(textColor + message + ConsoleColor.RESET);
        System.out.println("-------------------------------------------------------------");
    }

    /**
     * Prints the "Codex" Logo in ASCII art.
     */

    public static void printCodexLogo() {
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream is = classloader.getResourceAsStream("Codex_logo.txt");
        if (is != null) {
            Scanner sc = new Scanner(is);
            ClientCLI.clearConsole();
            while (sc.hasNextLine()) {
                System.out.println(ConsoleColor.GREEN + sc.nextLine() + ConsoleColor.RESET);
            }
        }
    }

    /**
     *
     */

    public static void printAvailableGames() {
        AvailableGamesModel availableGamesModel = AvailableGamesModel.getInstance();
        System.out.println("-------------------------------------------------------------");
        for (String s :availableGamesModel.getGames()){
            System.out.println(s);
        }
        System.out.println("-------------------------------------------------------------");

    }


    /**
     * Displays the main menu options along with their descriptions.
     */
    public static void printMenu()
    {
        Map<String, String> menuOptions = new HashMap<>();
        menuOptions.put("setusername | su <username>", "Set your username");
        menuOptions.put("join | j <gameName>", "Join to a game");
        menuOptions.put("create | c <gameName> <players(2-4)>", "Create a game for 2 to 4 players");
        menuOptions.put("availablegames | ag", "View all available games");
        menuOptions.put("quit | q", "Exit from Codex");
        System.out.println(MENU_HEADER);
        for (Map.Entry<String, String> entry : menuOptions.entrySet()) {
            System.out.printf("%-50s %-20s", entry.getKey(), entry.getValue());
            System.out.println();
        }
        System.out.println("Enter your choice: ");
    }

    /**
     * Prints the card information for a single card, including its ID and facing state.
     * @param id The ID of the card to print.
     * @param facingUp True if the card is facing up, false otherwise.
     * @throws InvalidIdException If the provided card ID is invalid.
     */
    public static void printCardInfo(int id, boolean facingUp) throws InvalidIdException {

        CardPrinter cardPrinter = new CardPrinter(CLI_CARD_WIDTH, CLI_CARD_HEIGHT, CLI_CORNER_HEIGHT, CLI_CORNER_WIDTH);
        cardPrinter.loadCardRepresentation(id,facingUp);
        cardPrinter.printCard();

    }


    /**
     * Prints information about the top card and the two revealed cards from both the resource deck and the gold deck.
     */
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

    /**
     * Prints information about the game board.
     */
    public static void printGameBoard(){
        ArrayList<CardRepresentation> placementHistory = GameFieldModel.getInstance().getPlacementHistory();

        int negativeXBound = -3;
        int positiveXBound = 3;
        int negativeYBound = -3;
        int positiveYBound = 3;

        for(CardRepresentation cardRepresentation : placementHistory){
            if (cardRepresentation.getX() >= positiveXBound) positiveXBound = cardRepresentation.getX() + 1;
            if (cardRepresentation.getX() <= negativeXBound) negativeXBound = cardRepresentation.getX() - 1;
            if (cardRepresentation.getY() >= positiveYBound) positiveYBound = cardRepresentation.getY() + 1;
            if (cardRepresentation.getY() <= negativeYBound) negativeYBound = cardRepresentation.getY() - 1;
        }

        int width = (positiveXBound - negativeXBound) + 1;
        int height = (positiveYBound - negativeYBound) + 1;

        String[][] gameField = new String[height][width];

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                gameField[i][j] = ConsoleColor.BLACK + "|________|" + ConsoleColor.RESET;
            }
        }


        for(CardRepresentation cardRepresentation : placementHistory){
            int xCenter = Math.abs(negativeXBound);
            int yCenter = Math.abs(positiveYBound);
            int row = yCenter - cardRepresentation.getY();
            int column = xCenter + cardRepresentation.getX();

            int cardId = cardRepresentation.getId();
            String cardColor = getColor(cardId);


            String faceUpstate;
            if(cardRepresentation.isFacingUp()) faceUpstate = "FRNT";
            else faceUpstate = "BACK";

            gameField[row][column] = cardColor + "|#" + cardId + "(" + faceUpstate  +")|" + ConsoleColor.RESET ;
        }

        printMatrix(gameField);
    }




    /**
     * Prints information about the cards hand.
     */
    public static void printHand(){
        ArrayList<CardRepresentation> handArray = HandModel.getInstance().getCardsInHand();
        StringBuilder handString  = new StringBuilder("Your Hand: ");

        for (CardRepresentation c : handArray){
            handString.append("|#").append(getColor(c.getId())).append(c.getId()).append(ConsoleColor.RESET).append("| ");
        }

        Printer.printMessage(handString.toString());
    }

    /**
     * Prints a guide to the resource symbols used in the game.
     */
    public static void printGuide() {
        System.out.println(
                        Resource.fungi.toSymbol() + ": fungi | " +
                        Resource.animal.toSymbol() + ": animal | " +
                        Resource.insect.toSymbol() + ": insect | " +
                        Resource.plant.toSymbol() + ": plant | " +
                        Resource.feather.toSymbol() + ": feather | " +
                        Resource.inkPot.toSymbol() + ": inkPot | " +
                        Resource.scroll.toSymbol() + ": scroll"

        );
        System.out.println("(FRNT): the FRONT of the card is showing on the field.");
        System.out.println("(BACK): the BACK of the card is showing on the field.");

    }

    /**
     * Prints the player's current resource counts.
     */
    public static void printResources(){
        ScoreBoardModel scoreBoardModel = ScoreBoardModel.getInstance();
        Printer.printMessage("Your resources: " +
                Resource.fungi.toSymbol() + ": " + scoreBoardModel.getFungiResourceCount() + " | " +
                Resource.animal.toSymbol() + ": " + scoreBoardModel.getAnimalResourceCount() + " | " +
                Resource.insect.toSymbol() + ": " + scoreBoardModel.getInsectResourceCount() + " | " +
                Resource.plant.toSymbol() + ": " + scoreBoardModel.getPlantResourceCount() + " || " +
                Resource.feather.toSymbol() + ": " + scoreBoardModel.getFeatherCount() + " | " +
                Resource.inkPot.toSymbol() + ": " + scoreBoardModel.getInkPotCount() + " | " +
                Resource.scroll.toSymbol() + ": " + scoreBoardModel.getScrollCount() + " |"
        );
    }

    /**
     * Prints the leaderboard showing usernames, scores, and solved objectives.
     * Highlights the player's position in the leaderboard.
     */
    public static void printLeaderboard(){
        ScoreBoardModel scoreBoardModel = ScoreBoardModel.getInstance();
        String[] positions = new String[]{"1st", "2nd", "3rd", "4th"};
        String result = "";

        int pos_index = 0;

        for(JSONObject obj : scoreBoardModel.getLeaderboard()){

            System.out.println( positions[pos_index] + " - " + obj.get("username").toString() + ": " + obj.get("score").toString() + " Points (" + obj.get("solvedObjectives").toString() + " solved Objectives)" );

            if(obj.get("username").toString().equals(PlayerModel.getInstance().getUsername())){

                if(pos_index == 0){
                    result = "You Won";
                }
                else {
                    result = "You came in " + positions[pos_index] + " place!";
                }
            }

            pos_index++;
        }

        Printer.printMessage(result,ConsoleColor.YELLOW);
    }

    /**
     * Prints the scores of all players.
     */
    public static void printScores(){
        ScoreBoardModel scoreBoardModel = ScoreBoardModel.getInstance();
        HashMap<String,Integer> scores = scoreBoardModel.getScore();

        for(String username : scores.keySet()){

            if (Objects.equals(PlayerModel.getInstance().getUsername(), username))
                System.out.println("You: " + scores.get(username));

            else System.out.println(username + ": " + scores.get(username));

        }
    }

    private static void printMatrix(String[][] matrix) {
        for (String[] strings : matrix) {
            for (String string : strings) {
                if (string != null) {
                    System.out.printf(string);
                } else System.out.print("\t");
            }
            System.out.println();
        }
    }


/**
 * Gets the ANSI code for the color corresponding to a card's kingdom based on its ID.
 * @param id The ID of the card.
 * @return The ANSI code for the card's kingdom color, or white if the ID corresponds to a card with no kingdom.
 */
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
