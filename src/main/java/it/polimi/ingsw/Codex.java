package it.polimi.ingsw;

import it.polimi.ingsw.network.ClientConnectionManager;
import it.polimi.ingsw.network.Server;
import it.polimi.ingsw.util.customexceptions.ServerUnreachableException;

public class Codex {
    private Thread thread;

    public Thread getThread() {
        return thread;
    }

    public void setThread(Thread thread) {
        this.thread = thread;
    }

    public static void main(String[] args) {
        Codex codex = new Codex();
        if(args.length == 0) {
            System.out.println("start up arguments required:");
            System.out.println("client [server address] [server port]");
            System.out.println("or");
            System.out.println("server [port number]");
        }
        else {
            if(args[0].equals("client") && args.length == 3) {
                try {
                    codex.setThread(new Thread(new ClientConnectionManager(args[1],Integer.parseInt(args[2]),codex)));
                } catch (ServerUnreachableException e) {
                    return;
                }
                codex.getThread().start();
            }
            else if(args[0].equals("server") && args.length == 2) {
                Server server = new Server(Integer.parseInt(args[1]), codex);
                codex.setThread(new Thread(server));
                codex.getThread().start();
                server.startServer();
            }
            else {
                System.out.println("unexpected arguments");
            }
        }
    }

    public void shutdown() {
        thread.interrupt();
    }
}
