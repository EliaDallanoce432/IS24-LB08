package it.polimi.ingsw.client.model;

import it.polimi.ingsw.util.supportclasses.Token;
import org.json.simple.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class represents an ObservableModel that keeps track of the scoreboard, resources and final leaderboard information in a game
 */
public class ScoreBoardModel extends ObservableModel{

    private static ScoreBoardModel instance;
    private HashMap<String, Integer> scores;
    private HashMap<String, Token> tokens;
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
        leaderboard = new ArrayList<>();
        tokens = new HashMap<>();
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

    public void setTokens(HashMap<String, Token> tokens){
        this.tokens = tokens;
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

    public Token getToken(String username){
        if (tokens.get(username) == null) return Token.black;
        else return tokens.get(username);
    }

    public void clear(){
        insectResourceCount = 0;
        animalResourceCount = 0;
        fungiResourceCount = 0;
        plantResourceCount = 0;
        featherCount = 0;
        scrollCount = 0;
        inkPotCount = 0;
        if (scores != null) scores.clear();
        if (tokens != null) tokens.clear();
        if (leaderboard!= null) leaderboard.clear();
    }

}
