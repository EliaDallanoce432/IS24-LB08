package it.polimi.ingsw.util.cli;

import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * This class represents a reader that is always ready to take inputs from the terminal
 */
public abstract class TerminalInputReader implements Runnable {
    protected CommandParser commandParser;
    protected boolean running = true;
    protected Scanner scanner = new Scanner(System.in);

    @Override
    public void run() {
        while (running) {
            try {
                    System.out.print("> ");
                    commandParser.parse(scanner.nextLine());
            }
            catch (NoSuchElementException e) {
                System.out.println("Error reading input. Please try again.");
            }
        }
    }

    /**
     * Terminates the TerminalInputReader execution
     */
    public void shutdown() {
        running = false;
        scanner.close();
    }
}
