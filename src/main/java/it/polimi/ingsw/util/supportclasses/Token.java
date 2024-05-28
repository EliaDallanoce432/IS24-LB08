package it.polimi.ingsw.util.supportclasses;

/**
 * This class contains the possible token colors.
 */
public enum Token {
    red, yellow, blue, green, black;

    @Override
    public String toString() {
        return switch (this) {
            case red -> "red";
            case yellow -> "yellow";
            case blue -> "blue";
            case green -> "green";
            case black -> "black";
        };
    }

    /**
     * Converts a string to the corresponding token.
     * @param color The string to convert.
     * @return The token color
     */
    public static Token parseToken(String color) {
        return switch (color) {
            case "red" -> red;
            case "yellow" -> yellow;
            case "blue" -> blue;
            case "green" -> green;
            case "black" -> black;
            case null -> black;
            default -> throw new IllegalStateException("Unexpected value: " + color);
        };
    }

    /**
     * Converts the token color to a Javafx compatible color
     * @return The Javafx color.
     */
    public javafx.scene.paint.Color toColor() {
        return switch (this){
            case red -> javafx.scene.paint.Color.RED;
            case yellow -> javafx.scene.paint.Color.ORANGE;
            case blue -> javafx.scene.paint.Color.BLUE;
            case green -> javafx.scene.paint.Color.GREEN;
            case black -> javafx.scene.paint.Color.BLACK;
        };
    }
}
