package it.polimi.ingsw.util.cli;

import java.util.NoSuchElementException;
import java.util.Scanner;

public abstract class TerminalInputReader implements Runnable {
    protected CommandParser commandParser;
    protected boolean running = true;
    protected Scanner scanner = new Scanner(System.in);

    @Override
    public void run() {
        while (running) {
            try {
                if (scanner.hasNextLine()) {
                    System.out.print("> ");
                    commandParser.parse(scanner.nextLine());
                }
            }
            catch (NoSuchElementException e) {
                System.out.println("Error reading input. Please try again.");
            }
        }
    }

    public void shutdown() {
        running = false;
        scanner.close();
    }
}
