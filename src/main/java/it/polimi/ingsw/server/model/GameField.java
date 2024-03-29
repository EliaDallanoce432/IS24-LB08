package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.model.card.PlaceableCard;
import it.polimi.ingsw.util.customexeptions.CannotPlaceCardException;
import it.polimi.ingsw.util.supportclasses.Resource;

import java.util.ArrayList;

public class GameField {
    private PlaceableCard[][] cardsGrid;
    private Player player;

    final private int GRIDOFFSET = 40;
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
     * returns card at (x,y) coordinates or null
      * @param x x coordinate on the grid
     * @param y y coordinate on the grid
     * @return PlaceableCard
     */
    public PlaceableCard lookAtCoordinates(int x, int y){
        return cardsGrid[x+GRIDOFFSET][y+GRIDOFFSET];
    }

    /**
     * places the card on the game field
     * @param card card that needs to be placed
     * @param x x coordinate on the grid
     * @param y y coordinate on the grid
     */
    public void place(PlaceableCard card, int x, int y) throws CannotPlaceCardException {

        if (this.lookAtCoordinates(x,y)!=null) throw new CannotPlaceCardException();
        if (!this.followsPlacementRules(x,y)) throw new CannotPlaceCardException();
        if (!this.followsPlacementRequirements(card)) throw new CannotPlaceCardException();
        cardsGrid[x+GRIDOFFSET][y+GRIDOFFSET] = card; //places card in the grid
        updateNeighboursAndResources(card, x, y);



    }

    private boolean followsPlacementRules(int x, int y){

        if ((x%2==0 && y%2==1) || (x%2==1 && y%2==0)) return false; //impossible to place in odd/even and even/odd coordinates

        PlaceableCard neighbourCard;
        boolean hasValidNeighbours = false;


        neighbourCard = this.lookAtCoordinates(x+1,y+1);
        if( neighbourCard != null ) {
            if(!neighbourCard.getBottomLeftCorner().isAttachable()) return false;
            else hasValidNeighbours = true;
        }

        neighbourCard = this.lookAtCoordinates(x+1,y-1);
        if( neighbourCard != null ) {
            if(!neighbourCard.getTopLeftCorner().isAttachable()) return false;
            else hasValidNeighbours = true;
        }

        neighbourCard = this.lookAtCoordinates(x-1,y-1);
        if( neighbourCard != null ) {
            if(!neighbourCard.getTopRightCorner().isAttachable()) return false;
            else hasValidNeighbours = true;
        }

        neighbourCard = this.lookAtCoordinates(x-1,y+1);
        if( neighbourCard != null ) {
            if(!neighbourCard.getBottomRightCorner().isAttachable()) return false;
            else hasValidNeighbours = true;
        }

        return hasValidNeighbours;

    }

    private boolean followsPlacementRequirements (PlaceableCard placeableCard){

        if(!placeableCard.isFacingUp()) return true;
        return placeableCard.getRequiredAnimalResourceAmount() <= this.animalCount &&
                placeableCard.getRequiredFungiResourceAmount() <= this.fungiCount &&
                placeableCard.getRequiredInsectResourceAmount() <= this.insectCount &&
                placeableCard.getRequiredPlantResourceAmount() <= this.plantCount;

    }

    private void updateNeighboursAndResources(PlaceableCard card, int x, int y){

        PlaceableCard neighbourCard;


        neighbourCard = this.lookAtCoordinates(x+1,y+1);
        if( neighbourCard != null ) {
            this.removeResource(neighbourCard.getBottomLeftCorner().getResource());
            neighbourCard.getBottomLeftCorner().setOnTop(false);

        }

        neighbourCard = this.lookAtCoordinates(x+1,y-1);
        if( neighbourCard != null ) {
            this.removeResource(neighbourCard.getTopLeftCorner().getResource());
            neighbourCard.getTopLeftCorner().setOnTop(false);

        }

        neighbourCard = this.lookAtCoordinates(x-1,y-1);
        if( neighbourCard != null ) {
            this.removeResource(neighbourCard.getTopRightCorner().getResource());
            neighbourCard.getTopRightCorner().setOnTop(false);

        }

        neighbourCard = this.lookAtCoordinates(x-1,y+1);
        if( neighbourCard != null ) {
            this.removeResource(neighbourCard.getBottomRightCorner().getResource());
            neighbourCard.getBottomRightCorner().setOnTop(false);

        }

        if(card.isFacingUp()){
            this.addResource(card.getTopRightCorner().getResource());
            this.addResource(card.getBottomRightCorner().getResource());
            this.addResource(card.getBottomLeftCorner().getResource());
            this.addResource(card.getTopLeftCorner().getResource());
        }
        else{
            this.addResource(card.getCardKingdom());
        }



    }

    private void addResource(Resource resource){

        switch (resource){
            case fungi -> {
                fungiCount++;
            }
            case animal -> {
                animalCount++;
            }
            case plant -> {
                plantCount++;
            }
            case insect -> {
                insectCount++;
            }
            case scroll -> {
                scrollCount++;
            }
            case inkPot -> {
                inkPotCount++;
            }
            case feather -> {
                featherCount++;
            }
            default -> {
                return;
            }
        }

    }

    private void removeResource(Resource resource){

        switch (resource){
            case fungi -> {
                fungiCount--;
            }
            case animal -> {
                animalCount--;
            }
            case plant -> {
                plantCount--;
            }
            case insect -> {
                insectCount--;
            }
            case scroll -> {
                scrollCount--;
            }
            case inkPot -> {
                inkPotCount--;
            }
            case feather -> {
                featherCount--;
            }
            default -> {
                return;
            }
        }

    }

}
