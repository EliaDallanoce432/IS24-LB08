package it.polimi.ingsw.client.model;

import org.json.simple.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class represents an ObservableModel that keeps track of the scoreboard, resources and final leaderboard information in a game
 */
public class ScoreBoardModel extends ObservableModel{

    private static ScoreBoardModel instance;
    private HashMap<String, Integer> scores;
    private ArrayList<JSONObject> leaderboard;

    private int insectResourceCount;
    private int animalResourceCount;
    private int fungiResourceCount;
    private int plantResourceCount;
    private int featherCount;
    private int scrollCount;
    private int inkPotCount;

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

    public void setResources (int animalResourceCount, int insectResourceCount,
                              int fungiResourceCount, int plantResourceCount, int featherCount,
                              int scrollCount, int inkPotCount) {
        this.animalResourceCount = animalResourceCount;
        this.insectResourceCount = insectResourceCount;
        this.fungiResourceCount = fungiResourceCount;
        this.plantResourceCount = plantResourceCount;
        this.featherCount = featherCount;
        this.scrollCount = scrollCount;
        this.inkPotCount = inkPotCount;
        notifyObservers();
    }

    public int getInsectResourceCount() {
        return insectResourceCount;
    }

    public int getAnimalResourceCount() {
        return animalResourceCount;
    }

    public int getFungiResourceCount() {
        return fungiResourceCount;
    }

    public int getPlantResourceCount() {
        return plantResourceCount;
    }

    public int getFeatherCount() {
        return featherCount;
    }

    public int getScrollCount() {
        return scrollCount;
    }

    public int getInkPotCount() {
        return inkPotCount;
    }

}
