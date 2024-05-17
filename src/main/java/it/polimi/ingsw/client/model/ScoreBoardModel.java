package it.polimi.ingsw.client.model;

import org.json.simple.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class represents an ObservableModel that keeps track of the scoreboard information in a game
 */
public class ScoreBoardModel extends ObservableModel{

    private static ScoreBoardModel instance;
    private HashMap<String, Integer> scores;
    private ArrayList<JSONObject> leaderboard;

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

    public ArrayList<JSONObject> getLeaderboard(){
        return leaderboard;
    }

    public void setLeaderboard(ArrayList<JSONObject> leaderboard){
        this.leaderboard = leaderboard;
        notifyObservers();
    }

    public void setScores(HashMap<String, Integer> scores){
        this.scores = scores;
        notifyObservers();
    }

    public HashMap<String, Integer> getScore(){
        return scores;
    }

}
