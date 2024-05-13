package it.polimi.ingsw.client.model;

import it.polimi.ingsw.util.supportclasses.Color;

public class PlayerModel extends ObservableModel {

    private static PlayerModel istance;
    private String username;
    private String turnPlayer;
    private Color token;

    public static PlayerModel getInstance(){

        if (istance==null) istance = new PlayerModel();
        return istance;

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




}
