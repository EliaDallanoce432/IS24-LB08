package it.polimi.ingsw.client.model;

import java.util.ArrayList;

public class AvailableGamesModel extends ObservableModel{

    private static AvailableGamesModel istance;

    private ArrayList<String> games;


    public static AvailableGamesModel getIstance(){

        if (istance==null) istance = new AvailableGamesModel();
        return istance;

    }

    public ArrayList<String> getGames() {
        return games;
    }

    public void setGames(ArrayList<String> games) {
        this.games = games;
        notifyObservers();
    }
}
