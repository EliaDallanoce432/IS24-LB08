package it.polimi.ingsw.util.supportclasses;

public enum Color {
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
}
