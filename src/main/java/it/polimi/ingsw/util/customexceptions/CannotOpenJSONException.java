package it.polimi.ingsw.util.customexceptions;

/**
 * Exception thrown when the JSON file can't be opened.
 */
public class CannotOpenJSONException extends Exception{
    String message;
    public CannotOpenJSONException(String message) {
        this.message = message;
    }
}
