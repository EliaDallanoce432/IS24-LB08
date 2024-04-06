package it.polimi.ingsw.util.customexceptions;

public class CannotOpenJSONException extends Exception{
    String message;
    public CannotOpenJSONException(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return message;
    }
}
