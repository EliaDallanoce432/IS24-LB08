package it.polimi.ingsw.util.customexceptions;

/**
 * Exception thrown when a card cannot be placed on the game field.
 */
public class CannotPlaceCardException extends Exception {

    public CannotPlaceCardException(String message) { super (message);}
}
