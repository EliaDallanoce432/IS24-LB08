package it.polimi.ingsw.client.model;

import java.util.HashMap;

public class ScoreBoardModel extends ObservableModel{

    private static ScoreBoardModel istance;
    private HashMap<String, Integer> scores;
    private int playerNumber;

    public ScoreBoardModel() {
        scores = new HashMap<>();
    }

    public static ScoreBoardModel getInstance(){

        if (istance==null) istance = new ScoreBoardModel();
        return istance;

    }

    public int getPlayerNumber() {
        return playerNumber;
    }

    public void setScores(HashMap<String, Integer> scores){
        this.scores = scores;
        playerNumber = scores.size();
        notifyObservers();
    }

    public HashMap<String, Integer> getScore(){
        return scores;
    }

}
