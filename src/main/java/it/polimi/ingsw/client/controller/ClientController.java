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

        while (true) {

            Scanner scanner = new Scanner(System.in);
            String username = scanner.nextLine();
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }


            JSONObject jsonObject = new JSONObject();

            jsonObject.put("command", "setUsername");
            JSONArray parameters = new JSONArray();
            parameters.add(username);
            jsonObject.put("parameters", parameters);


            clientSocket.sendMessage(jsonObject);


        }
    }
}
