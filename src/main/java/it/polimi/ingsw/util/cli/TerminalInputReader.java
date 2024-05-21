package it.polimi.ingsw.util.cli;

import java.util.Scanner;

public abstract class TerminalInputReader implements Runnable {
    protected CommandParser commandParser;
    protected boolean running = true;
    protected Scanner scanner = new Scanner(System.in);

    @Override
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
