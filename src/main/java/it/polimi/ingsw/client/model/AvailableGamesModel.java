package it.polimi.ingsw.client.model;

import java.util.ArrayList;

public class AvailableGamesModel extends ObservableModel{

    private static AvailableGamesModel instance;

    private ArrayList<String> games;


    public static AvailableGamesModel getInstance(){

        if (instance ==null) instance = new AvailableGamesModel();
        return instance;

    }

    private AvailableGamesModel(){
        games = new ArrayList<>();
    }

    public ArrayList<String> getGames() {
        return games;
    }

    public void setGames(ArrayList<String> games) {
        this.games = games;
        notifyObservers();
    }
}
