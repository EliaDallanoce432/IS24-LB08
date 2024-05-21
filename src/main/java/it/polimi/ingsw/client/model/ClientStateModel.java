package it.polimi.ingsw.client.model;

import it.polimi.ingsw.util.supportclasses.ClientState;

/**
 * This class represents an ObservableModel that keeps track of the current client state and optionally a reason for the state change
 */
public class ClientStateModel extends ObservableModel{
    private static ClientStateModel instance;
    private String reason;

    private ClientState clientState = ClientState.LOBBY_STATE;

    /**
     * returns the singleton instance of ClientStateModel
     * @return The singleton instance of ClientStateModel
     */
    public static ClientStateModel getInstance(){

        if (instance ==null) instance = new ClientStateModel();
        return instance;

    }

    public  ClientState getClientState() {
        return clientState;
    }

    public void setClientState(ClientState clientState) {
        if (this.clientState != ClientState.LOST_CONNECTION_STATE) this.clientState = clientState;
        notifyObservers();
    }

    public void setClientState(ClientState clientState , String reason) {
        this.reason = reason;
        setClientState(clientState);
    }

    public String getReason(){
        return reason;
    }

}
