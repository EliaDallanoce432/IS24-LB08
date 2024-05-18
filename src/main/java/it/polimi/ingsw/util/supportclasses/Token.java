package it.polimi.ingsw.util.supportclasses;

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

    public static Token parseColor(String color) {
        return switch (color) {
            case "red" -> red;
            case "yellow" -> yellow;
            case "blue" -> blue;
            case "green" -> green;
            case "black" -> black;
            default -> throw new IllegalStateException("Unexpected value: " + color);
        };
    }
}
