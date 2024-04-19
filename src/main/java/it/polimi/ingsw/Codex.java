package it.polimi.ingsw;

import it.polimi.ingsw.network.ClientSocket;
import it.polimi.ingsw.network.Server;

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
                thread = new Thread(new ClientSocket(args[1],Integer.parseInt(args[2])));
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
