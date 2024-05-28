package it.polimi.ingsw.util.customexceptions;

/**
 * Exception thrown when trying to add a card to an already full hand.
 */
public class FullHandException extends Exception{
    public FullHandException(){super();}
}
