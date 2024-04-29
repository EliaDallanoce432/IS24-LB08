package it.polimi.ingsw.client.view;

import it.polimi.ingsw.client.controller.ClientController;

public abstract class ViewController {
    protected ClientController clientController;

    public void setClientController(ClientController clientController) {
        this.clientController = clientController;
    }

    public void showMessage(String message) {
        System.out.println(message);
    }

    public void loadDecks(){
        System.out.println("can't load deck here");
    }


}
