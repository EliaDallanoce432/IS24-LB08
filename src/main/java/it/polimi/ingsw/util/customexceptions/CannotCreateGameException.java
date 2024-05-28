package it.polimi.ingsw.util.customexceptions;

/**
 * Exception thrown when the game can't be created for some reason.
 */
public class CannotCreateGameException extends Exception {
    public CannotCreateGameException(String message) {super(message);}
}
