package it.polimi.ingsw.client.model;

import it.polimi.ingsw.util.supportclasses.Token;

/**
 * This class represents an ObservableModel that keeps track of player information in a game
 */
public class PlayerModel extends ObservableModel {

    private static PlayerModel instance;
    private String username;
    private String turnPlayer;
    private Token token;
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

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
        notifyObservers();
    }

    public boolean isLastTurn() {
        return lastTurn;
    }

    public void setLastTurn(boolean lastTurn) {
        this.lastTurn = lastTurn;
    }

    /**
     * Resets the LastTurn attribute to false.
     */
    public void clear(){
        lastTurn = false;
    }
}
