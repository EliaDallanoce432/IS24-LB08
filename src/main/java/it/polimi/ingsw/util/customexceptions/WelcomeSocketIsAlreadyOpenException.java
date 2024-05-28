package it.polimi.ingsw.util.customexceptions;

/**
 * Exception thrown when trying to open a server socket but the server is already listening on another port.
 */
public class WelcomeSocketIsAlreadyOpenException extends Exception {

    public WelcomeSocketIsAlreadyOpenException(String message) { super(message); }
}
