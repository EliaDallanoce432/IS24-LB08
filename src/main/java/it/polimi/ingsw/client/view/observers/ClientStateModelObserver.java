package it.polimi.ingsw.client.view.observers;

import it.polimi.ingsw.client.model.ClientStateModel;
import it.polimi.ingsw.client.model.SelectableCardsModel;
import it.polimi.ingsw.client.view.StageManager;
import it.polimi.ingsw.util.supportclasses.ClientState;

public class ClientStateModelObserver implements ModelObserver{

    public ClientStateModelObserver() {
        ClientStateModel.getIstance().addObserver(this);
    }

    @Override
    public void update() {
        StageManager.getViewController().updateSceneStatus();

    }
}
