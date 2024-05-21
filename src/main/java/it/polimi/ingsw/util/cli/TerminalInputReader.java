package it.polimi.ingsw.util.cli;

import java.util.Scanner;

public class TerminalInputReader implements Runnable {
    private final CommandParser commandParser;
    private boolean running;
    private final Scanner scanner;

    public TerminalInputReader(CommandParser commandParser) {
        this.commandParser = commandParser;
        scanner = new Scanner(System.in);
        running = true;
    }

    public void run() {
        while (running) {
            if (scanner.hasNextLine()) {
                commandParser.parse(scanner.nextLine());
            }
        }
    }

    public void shutdown() {
        running = false;
    }

}
