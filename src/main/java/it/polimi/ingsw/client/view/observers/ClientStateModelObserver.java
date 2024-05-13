package it.polimi.ingsw.client.view.observers;

import it.polimi.ingsw.client.model.ClientStateModel;
import it.polimi.ingsw.client.view.StageManager;

public class ClientStateModelObserver implements ModelObserver{

    public ClientStateModelObserver() {
        ClientStateModel.getIstance().addObserver(this);
    }

    @Override
    public void update() {
        StageManager.getViewController().updateSceneStatus();
        System.out.println(ClientStateModel.getIstance().getClientState());

    }
}
