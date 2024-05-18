package it.polimi.ingsw.client.model;

import it.polimi.ingsw.util.supportclasses.Color;

/**
 * This class represents an ObservableModel that keeps track of player information in a game
 */
public class PlayerModel extends ObservableModel {

    private static PlayerModel instance;
    private String username;
    private String turnPlayer;
    private Color token;
    private boolean lastTurn;



    public static PlayerModel getInstance(){

        if (instance ==null) instance = new PlayerModel();
        return instance;

    }

    private PlayerModel(){
        username = "Guest";
        turnPlayer = " ";
    }

    public String getTurnPlayer(){return turnPlayer;}

    public void setTurnPlayer(String turnPlayer){this.turnPlayer = turnPlayer;}

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
        notifyObservers();
    }

    public Color getToken() {
        return token;
    }

    public void setToken(Color token) {
        this.token = token;
        notifyObservers();
    }

    public boolean isLastTurn() {
        return lastTurn;
    }

    public void setLastTurn(boolean lastTurn) {
        this.lastTurn = lastTurn;
    }
}
