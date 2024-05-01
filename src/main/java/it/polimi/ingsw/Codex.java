package it.polimi.ingsw;

import it.polimi.ingsw.network.ClientConnectionManager;
import it.polimi.ingsw.network.Server;
import it.polimi.ingsw.util.customexceptions.ServerUnreachableException;

public class Codex {

    public static void main(String[] args) {
        Thread thread;
        if(args.length == 0) {
            System.out.println("start up arguments required:");
            System.out.println("client [server address] [server port]");
            System.out.println("or");
            System.out.println("server [port number]");
        }
        else {
            if(args[0].equals("client") && args.length == 3) {
                try {
                    thread = new Thread(new ClientConnectionManager(args[1],Integer.parseInt(args[2])));
                } catch (ServerUnreachableException e) {
                    return;
                }
                thread.start();
            }
            else if(args[0].equals("server") && args.length == 2) {
                Server server = new Server(Integer.parseInt(args[1]));
                thread = new Thread(server);
                thread.start();
                server.startServer();
            }
            else {
                System.out.println("unexpected arguments");
            }
        }
    }
}
