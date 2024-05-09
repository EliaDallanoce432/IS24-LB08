package it.polimi.ingsw.client.model;

import java.util.HashMap;

public class ScoreBoardModel extends ObservableModel{

    private static ScoreBoardModel istance;
    private HashMap<String, Integer> scores;

    public ScoreBoardModel() {
        scores = new HashMap<>();
    }

    public static ScoreBoardModel getIstance(){

        if (istance==null) istance = new ScoreBoardModel();
        return istance;

    }

    public void setScores(HashMap<String, Integer> scores){
        this.scores = scores;
        notifyObservers();
    }

    public HashMap<String, Integer> getScore(){
        return scores;
    }

}
