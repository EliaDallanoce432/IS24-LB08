package it.polimi.ingsw.server.model;

import java.util.ArrayList;

public class GameField {
    private PlaceableCard[][] cardsGrid;
    private Player player;
    private int fungiCount;
    private int animalCount;
    private int plantCount;
    private int insectCount;
    private int scrollCount;
    private int inkPotCount;
    private int featherCount;
    private ArrayList<PlaceableCard> plantCards;
    private ArrayList<PlaceableCard> animalCards;
    private ArrayList<PlaceableCard> fungiCards;
    private ArrayList<PlaceableCard> insectCards;

    public GameField(Player player) {
        this.player = player;
        cardsGrid = new PlaceableCard[81][81];
        for(int i=0; i<81; i++) {
            for(int j=0; j<81; j++) {
                cardsGrid[i][j] = null;
            }
        }
        animalCount = 0;
        plantCount = 0;
        insectCount = 0;
        fungiCount = 0;
        inkPotCount = 0;
        scrollCount = 0;
        featherCount = 0;
        animalCards = new ArrayList<PlaceableCard>();
        insectCards = new ArrayList<PlaceableCard>();
        fungiCards = new ArrayList<PlaceableCard>();
        plantCards = new ArrayList<PlaceableCard>();
    }

    public Player getPlayer() {
        return player;
    }

    public int getFungiCount() {
        return fungiCount;
    }

    /**
     * updates the total visible resource counter
     * @param resourceChange variation in the amount of the resource (positive or negative)
     */
    public void updateFungiCount(int resourceChange) {
        this.fungiCount = this.fungiCount+resourceChange;
    }

    public int getAnimalCount() {
        return animalCount;
    }
    /**
     * updates the total visible resource counter
     * @param resourceChange variation in the amount of the resource (positive or negative)
     */
    public void setAnimalCount(int resourceChange) {
        this.animalCount = this.animalCount+resourceChange;
    }

    public int getPlantCount() {
        return plantCount;
    }
    /**
     * updates the total visible resource counter
     * @param resourceChange variation in the amount of the resource (positive or negative)
     */
    public void setPlantCount(int resourceChange) {
        this.plantCount = this.plantCount+resourceChange;
    }

    public int getInsectCount() {
        return insectCount;
    }
    /**
     * updates the total visible resource counter
     * @param resourceChange variation in the amount of the resource (positive or negative)
     */
    public void setInsectCount(int resourceChange) {
        this.insectCount = this.insectCount+resourceChange;
    }

    public int getScrollCount() {
        return scrollCount;
    }
    /**
     * updates the total visible resource counter
     * @param resourceChange variation in the amount of the resource (positive or negative)
     */
    public void setScrollCount(int resourceChange) {
        this.scrollCount = this.scrollCount+resourceChange;
    }

    public int getInkPotCount() {
        return inkPotCount;
    }
    /**
     * updates the total visible resource counter
     * @param resourceChange variation in the amount of the resource (positive or negative)
     */
    public void setInkPotCount(int resourceChange) {
        this.inkPotCount = this.inkPotCount+resourceChange;
    }

    public int getFeatherCount() {
        return featherCount;
    }
    /**
     * updates the total visible resource counter
     * @param resourceChange variation in the amount of the resource (positive or negative)
     */
    public void setFeatherCount(int resourceChange) {
        this.featherCount = this.featherCount+resourceChange;
    }


    /**
     * places the card on the game field
     * @param card card that needs to be placed
     * @param x x coordinate on the grid
     * @param y y coordinate on the field
     */
    public void place(PlaceableCard card, int x, int y) {

    }
}
