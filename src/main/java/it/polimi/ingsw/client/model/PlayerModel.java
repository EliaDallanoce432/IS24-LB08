package it.polimi.ingsw.client.model;

import it.polimi.ingsw.util.supportclasses.Color;

public class PlayerModel extends ObservableModel {

    private static PlayerModel instance;
    private String username;
    private String turnPlayer;
    private Color token;

    private int insectResourceCount;
    private int animalResourceCount;
    private int fungiResourceCount;
    private int plantResourceCount;
    private int featherCount;
    private int scrollCount;
    private int inkPotCount;

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

    public Color getToken() {
        return token;
    }

    public void setToken(Color token) {
        this.token = token;
        notifyObservers();
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
