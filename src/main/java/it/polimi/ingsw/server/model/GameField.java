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

    public ArrayList<PlaceableCard> getPlantCards() {
        return plantCards;
    }

    public ArrayList<PlaceableCard> getAnimalCards() {
        return animalCards;
    }

    public ArrayList<PlaceableCard> getFungiCards() {
        return fungiCards;
    }

    public ArrayList<PlaceableCard> getInsectCards() {
        return insectCards;
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

    public PlaceableCard[][] getCardsGrid() {
        return cardsGrid;
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
      * @param x x coordinate on the grid (absolute)
     * @param y y coordinate on the grid  (absolute)
     * @return PlaceableCard
     */
    public PlaceableCard lookAtCoordinates(int x, int y){
        return cardsGrid[x][y];
    }

    /**
     * places the card on the game field
     * @param card card that needs to be placed
     * @param x x coordinate on the grid (relative)
     * @param y y coordinate on the grid (relative)
     */
    public void place(PlaceableCard card, int x, int y) throws CannotPlaceCardException {

        int absoluteX = x + GRIDOFFSET;
        int absoluteY = y + GRIDOFFSET;

        if (this.lookAtCoordinates(absoluteX,absoluteY)!=null) throw new CannotPlaceCardException();
        if (!this.followsPlacementRules(absoluteX,absoluteY)) throw new CannotPlaceCardException();
        if (!this.followsPlacementRequirements(card)) throw new CannotPlaceCardException();
        cardsGrid[absoluteX][absoluteY] = card; //places card in the grid
        card.setX(absoluteX);
        card.setY(absoluteY);
        updateNeighboursAndResources(card, absoluteX,absoluteY); //updates the surrounding cards and resource state
        player.setScore(player.getScore()+card.placementPoints()); //gets the points earned from placing the card
    }

    /**
     * checks the placing rules between the new card and the neighbour cards
     * @param x x coordinate (absolute)
     * @param y y coordinate (absolute)
     * @return boolean
     */
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

    /**
     * checks if the requirements for placing the card are matched
     * @param placeableCard card to check the requirements
     * @return  boolean
     */
    private boolean followsPlacementRequirements (PlaceableCard placeableCard){

        if(!placeableCard.isFacingUp()) return true;
        return placeableCard.getRequiredAnimalResourceAmount() <= this.animalCount &&
                placeableCard.getRequiredFungiResourceAmount() <= this.fungiCount &&
                placeableCard.getRequiredInsectResourceAmount() <= this.insectCount &&
                placeableCard.getRequiredPlantResourceAmount() <= this.plantCount;

    }

    /**
     * updates the state of the neighbour cards corners and resources visible on the gamefield after placing a card
     * @param card placed card
     * @param x x coordinate (absolute)
     * @param y y coordinate (absolute)
     */
    private void updateNeighboursAndResources(PlaceableCard card, int x, int y){

        PlaceableCard neighbourCard;

        neighbourCard = this.lookAtCoordinates(x+1,y+1);
        if( neighbourCard != null ) {
            this.removeResource(neighbourCard.getBottomLeftCorner().getResource());
            neighbourCard.getBottomLeftCorner().setVisible(false);
            neighbourCard.getBottomLeftCorner().setAttached(true);
            card.getTopRightCorner().setAttached(true);
        }

        neighbourCard = this.lookAtCoordinates(x+1,y-1);
        if( neighbourCard != null ) {
            this.removeResource(neighbourCard.getTopLeftCorner().getResource());
            neighbourCard.getTopLeftCorner().setVisible(false);
            neighbourCard.getTopLeftCorner().setAttached(true);
            card.getBottomRightCorner().setAttached(true);
        }

        neighbourCard = this.lookAtCoordinates(x-1,y-1);
        if( neighbourCard != null ) {
            this.removeResource(neighbourCard.getTopRightCorner().getResource());
            neighbourCard.getTopRightCorner().setVisible(false);
            neighbourCard.getTopRightCorner().setAttached(true);
            card.getBottomLeftCorner().setAttached(true);
        }

        neighbourCard = this.lookAtCoordinates(x-1,y+1);
        if( neighbourCard != null ) {
            this.removeResource(neighbourCard.getBottomRightCorner().getResource());
            neighbourCard.getBottomRightCorner().setVisible(false);
            neighbourCard.getBottomRightCorner().setAttached(true);
            card.getTopLeftCorner().setAttached(true);
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
        switch (card.getCardKingdom())
        {
            case fungi -> {
                this.fungiCards.add(card);
            }
            case animal -> {
                this.animalCards.add(card);
            }
            case plant -> {
                this.plantCards.add(card);
            }
            case insect -> {
                this.insectCards.add(card);
            }
        }
    }

    /**
     * adds the given resource to the total resource amount
     * @param resource resource to add to the total visible amount
     */
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

    /**
     * removes the given resource to the total resource amount
     * @param resource resource to add to the total visible amount
     */
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
