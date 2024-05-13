package it.polimi.ingsw.client.model;

import java.util.HashMap;

public class ScoreBoardModel extends ObservableModel{

    private static ScoreBoardModel instance;
    private HashMap<String, Integer> scores;
    private int playerNumber;

    public ScoreBoardModel() {
        scores = new HashMap<>();
    }

    public static ScoreBoardModel getInstance(){

        if (instance ==null) instance = new ScoreBoardModel();
        return instance;

    }

    public void setMyScore(int score){
        scores.put(PlayerModel.getInstance().getUsername(),score);
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
