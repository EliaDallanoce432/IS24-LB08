package it.polimi.ingsw.util.supportclasses;

import java.awt.*;

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

    public javafx.scene.paint.Color toColor() {
        return switch (this){
            case red -> javafx.scene.paint.Color.RED;
            case yellow -> javafx.scene.paint.Color.ORANGE;
            case blue -> javafx.scene.paint.Color.BLUE;
            case green -> javafx.scene.paint.Color.GREEN;
            case black -> javafx.scene.paint.Color.BLACK;
            default -> throw new IllegalStateException("Unexpected value: " + this);
        };
    }
}
