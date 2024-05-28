package it.polimi.ingsw.client.model;

import it.polimi.ingsw.util.supportclasses.ClientState;

/**
 * This class represents an ObservableModel that keeps track of the current client state and optionally a reason for the state change.
 */
public class ClientStateModel extends ObservableModel{
    private static ClientStateModel instance;
    private String reason;

    private ClientState clientState = ClientState.LOBBY_STATE;

    /**
     * Returns the singleton instance of ClientStateModel.
     * @return The singleton instance of ClientStateModel.
     */
    public static ClientStateModel getInstance(){

        if (instance ==null) instance = new ClientStateModel();
        return instance;

    }

    public  ClientState getClientState() {
        return clientState;
    }

    /**
     * Changes the client state unless the current state is LOST_CONNECTION_STATE and notifies any registered observers that the data has changed.
     * @param clientState The new client state.
     */
    public void setClientState(ClientState clientState) {
        if (this.clientState != ClientState.LOST_CONNECTION_STATE) this.clientState = clientState;
        notifyObservers();
    }

    /**
     * Changes the client state with a reason.
     * @param clientState The new client state.
     * @param reason The reason for the change.
     */
    public void setClientState(ClientState clientState , String reason) {
        this.reason = reason;
        setClientState(clientState);
    }

    public String getReason(){
        return reason;
    }
}
