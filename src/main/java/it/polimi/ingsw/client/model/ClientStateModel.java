package it.polimi.ingsw.client.model;

import it.polimi.ingsw.client.view.StageManager;
import it.polimi.ingsw.util.supportclasses.ClientState;

public class ClientStateModel extends ObservableModel{
    private static ClientStateModel istance;

    private ClientState clientState = ClientState.WELCOME_STATE;


    public static ClientStateModel getIstance(){

        if (istance==null) istance = new ClientStateModel();
        return istance;

    }

    public  ClientState getClientState() {
        return clientState;
    }

    public void setClientState(ClientState clientState) {
        this.clientState = clientState;
        notifyObservers();
    }

}
