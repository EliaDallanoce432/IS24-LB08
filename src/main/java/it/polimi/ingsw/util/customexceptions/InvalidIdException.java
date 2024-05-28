package it.polimi.ingsw.util.customexceptions;

/**
 * Exception thrown when the provided ID doesn't correspond to a valid card.
 */
public class InvalidIdException extends Exception{
    public InvalidIdException(String message)
    {
        super(message);
    }
}
