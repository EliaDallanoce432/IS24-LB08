package it.polimi.ingsw.util.cli;
/**
 * This interface provides a standardized way to interact with user input in a command-driven application.
 */
public interface CommandParser {

    /**
     * Analyzes and processes the provided user command, extracting arguments and triggering appropriate methods.
     * @param command The command The user-entered command string.
     */
    void parse(String command);
}
