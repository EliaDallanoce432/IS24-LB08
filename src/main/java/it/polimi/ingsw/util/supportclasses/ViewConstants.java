package it.polimi.ingsw.util.supportclasses;

public final class ViewConstants {

    //GUI CONSTANTS

    public static final double CARD_WIDTH = 113;
    public static final double CARD_HEIGHT = 75;
    public static final double SPACING = 10;

    public static final double PANE_WIDTH = 15000;
    public static final double PANE_HEIGHT = 15000;

    public static final double X_SNAP_INCREMENT = 0.75*CARD_WIDTH;
    public static final double Y_SNAP_INCREMENT = 0.6*CARD_HEIGHT;

    public static final double Y_GOLD_DECK = 100 ;

    public static final double SCENE_WIDTH = 600;
    public static final double SCENE_HEIGHT = 400;


    public static final double CHOOSE_CARDS_OFFSET = 50;
    public static final double CHOOSE_CARDS_SCALE = 1.5;
    public static final int BLANK_CARDS_NUMBER = 4;
    public static final double BLANK_CARDS_OFFSET = 4;

    public static final double TOKEN_OFFSET = 5;

    //CLI CONSTANTS

    public static final int CLI_CARD_HEIGHT = 6;
    public static final int CLI_CARD_WIDTH = 21;

    public static final int CLI_CORNER_HEIGHT = 2;
    public static final int CLI_CORNER_WIDTH = 5;

    public static String MENU_HEADER =
            ConsoleColor.RED+"╔╦╗"+ ConsoleColor.GREEN+"╔═╗"+ ConsoleColor.PURPLE+"╔╗╔"+ConsoleColor.CYAN+"╦ ╦\n" +
                    ConsoleColor.RED+"║║║"+ ConsoleColor.GREEN+"║╣ "+ ConsoleColor.PURPLE+"║║║"+ConsoleColor.CYAN+"║ ║\n" +
                    ConsoleColor.RED+"╩ ╩"+ ConsoleColor.GREEN+"╚═╝"+ ConsoleColor.PURPLE+"╝╚╝"+ConsoleColor.CYAN+"╚═╝\n"+ConsoleColor.RESET;
    public static final String INVALID_ARGUMENT_COUNT_MESSAGE = "Invalid number of arguments";
    public static final String INVALID_ARGUMENT_TYPE_MESSAGE = "Invalid argument type";

}
