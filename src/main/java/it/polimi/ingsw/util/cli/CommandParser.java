package it.polimi.ingsw.util.cli;

public interface CommandParser {

    /**
     * The method parses the command that was read on the terminal and parses its arguments to invoke other methods that will perform the desired function.
     * @param command The command to be parsed.
     */
    void parse(String command);
}
