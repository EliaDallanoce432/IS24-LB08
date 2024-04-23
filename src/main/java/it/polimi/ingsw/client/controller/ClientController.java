package it.polimi.ingsw.client.controller;

import it.polimi.ingsw.network.ClientConnectionManager;
import it.polimi.ingsw.network.ClientNetworkObserverInterface;

import java.util.Scanner;

public class ClientController implements Runnable, ClientNetworkObserverInterface {

    ClientConnectionManager clientConnectionManager;

    public ClientController(ClientConnectionManager clientConnectionManager) {

        this.clientConnectionManager = clientConnectionManager;

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

            clientConnectionManager.send(MessageGenerator.generateSetUsernameMessage(username));

            clientConnectionManager.send(MessageGenerator.generateGetAvailableGamesMessage());
            String gamename = scanner.nextLine();
            clientConnectionManager.send(MessageGenerator.generateSetUpGameMessage(gamename,2));
           // clientSocket.sendMessage(MessageGenerator.generateLeaveLobbyMessage());
    }


    @Override
    public void notifyIncomingMessage() {

    }

    @Override
    public void notifyConnectionLoss() {

    }
}
