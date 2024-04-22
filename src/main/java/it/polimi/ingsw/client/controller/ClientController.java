package it.polimi.ingsw.client.controller;

import it.polimi.ingsw.network.ClientSocket;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.Scanner;

public class ClientController implements Runnable {

    ClientSocket clientSocket;

    public ClientController(ClientSocket clientSocket) {

        this.clientSocket = clientSocket;

    }

    @Override
    public void run() {

            Scanner scanner = new Scanner(System.in);
            String username = scanner.nextLine();
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            clientSocket.send(MessageGenerator.generateSetUsernameMessage(username));

            clientSocket.send(MessageGenerator.generateGetAvailableGamesMessage());
            String gamename = scanner.nextLine();
            clientSocket.send(MessageGenerator.generateSetUpGameMessage(gamename,2));
           // clientSocket.sendMessage(MessageGenerator.generateLeaveLobbyMessage());
    }
}
