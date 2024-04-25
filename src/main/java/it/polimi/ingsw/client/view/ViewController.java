package it.polimi.ingsw.client.view;

import it.polimi.ingsw.client.controller.ClientController;

public abstract class ViewController {
    protected SceneLoader sceneLoader;
    protected ClientController clientController;

    public void setClientController(ClientController clientController) {
        this.clientController = clientController;
        sceneLoader = new SceneLoader(clientController);
    }

    public void showMessage(String message) {
        System.out.println(message);
    }
}
