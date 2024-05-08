package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.model.card.PlaceableCard;
import it.polimi.ingsw.server.model.card.StarterCard;
import it.polimi.ingsw.util.customexceptions.CannotPlaceCardException;
import it.polimi.ingsw.util.supportclasses.Resource;

import java.util.ArrayList;
import java.util.HashMap;

public class GameField {
    private final HashMap<String, PlaceableCard> cardsGrid;
    private final Player player;
    private int fungiCount;
    private int animalCount;
    private int plantCount;
    private int insectCount;
    private int scrollCount;
    private int inkPotCount;
    private int featherCount;
    private final ArrayList<PlaceableCard> plantCards;
    private final ArrayList<PlaceableCard> animalCards;
    private final ArrayList<PlaceableCard> fungiCards;
    private final ArrayList<PlaceableCard> insectCards;
    private final ArrayList<PlaceableCard> placementHistory;

    public GameField(Player player) {
        this.player = player;
        cardsGrid = new HashMap<>();
        animalCards = new ArrayList<>();
        insectCards = new ArrayList<>();
        fungiCards = new ArrayList<>();
        plantCards = new ArrayList<>();
        placementHistory = new ArrayList<>();
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

    public ArrayList<PlaceableCard> getPlacementHistory() {
        return placementHistory;
    }

    public void addToPlacementHistory(PlaceableCard card) {
        placementHistory.addLast(card);
    }

    public int getAnimalCount() {
        return animalCount;
    }

    public int getPlantCount() {
        return plantCount;
    }

    public int getInsectCount() {
        return insectCount;
    }

    public int getScrollCount() {
        return scrollCount;
    }

    public int getInkPotCount() {
        return inkPotCount;
    }

    public int getFeatherCount() {
        return featherCount;
    }

    /**
     * returns card at (x,y) coordinates or null
      * @param x x coordinate on the grid
     * @param y y coordinate on the grid
     * @return PlaceableCard
     */
    public PlaceableCard lookAtCoordinates(int x, int y){
        if(x<-40 || y<-40 || x>40 || y>40) return null;
        else return cardsGrid.get(coordinatesToString(x,y));
    }

    /**
     * places a card to the specified coordinates
     * @param card card to place
     * @param x x coordinate
     * @param y y coordinate
     */
    private void placeCardAtCoordinates(PlaceableCard card, int x, int y){
        cardsGrid.put(coordinatesToString(x,y),card);
    }

    /**
     * converts coordinates to string
     * @param x x coordinate
     * @param y y coordinate
     * @return String
     */
    private String coordinatesToString(int x, int y){
        return x + "," + y;
    }

    /**
     * places the starter card on the game field
     *
     * @param card     starter card that needs to be placed
     * @param facingUp optional value that chooses the side of the card that will be shown
     */
    public void place(StarterCard card, boolean facingUp) {
        card.setFacingUp(facingUp);
        this.placeCardAtCoordinates(card,0,0); //places card in the grid
        card.setX(0);
        card.setY(0);
        addResource(card.getTopRightCorner().getResource());
        addResource(card.getTopLeftCorner().getResource());
        addResource(card.getBottomRightCorner().getResource());
        addResource(card.getBottomLeftCorner().getResource());
        if(!card.isFacingUp()) {
            for (Resource res : card.getBackCentralResources()) {
                addResource(res);
            }
        }
        addToPlacementHistory(card);
    }

    /**
     * places the card on the game field
     *
     * @param card     card that needs to be placed
     * @param facingUp optional value that chooses the side of the card that will be shown
     * @param x        x coordinate on the grid
     * @param y        y coordinate on the grid
     */
    public void place(PlaceableCard card, boolean facingUp, int x, int y) throws CannotPlaceCardException {
        card.setFacingUp(facingUp);
        if (this.lookAtCoordinates(x,y)!=null) throw new CannotPlaceCardException("Card already placed at X: " + x + " Y: " + y + " !");
        if (!this.followsPlacementRules(x,y)) throw new CannotPlaceCardException("Doesn't follow placement rules!");
        if (!this.followsPlacementRequirements(card)) throw new CannotPlaceCardException("Placement requirements are not satisfied!");
        this.placeCardAtCoordinates(card,x,y);
        card.setX(x);
        card.setY(y);
        updateNeighboursAndResources(card, x,y); //updates the surrounding cards and resource state
        if(card.isFacingUp()) player.setScore(player.getScore()+card.placementPoints(this)); //gets the points earned from placing the card
        addToPlacementHistory(card);
    }

    /**
     * checks the placing rules between the new card and the neighbour cards
     * @param x x coordinate
     * @param y y coordinate
     * @return boolean
     */
    private boolean followsPlacementRules(int x, int y){

        return isInValidCoordinates(x, y) && hasValidNeighbours(x,y);

    }

    private boolean isInValidCoordinates(int x, int y) {
        return (Math.abs(x)%2==0 && Math.abs(y)%2==0) || (Math.abs(x)%2==1 && Math.abs(y)%2==1);
    }

    private boolean hasValidCorner(PlaceableCard neighbourCard, int[] offset) {
        return switch (offset[0]) {
            case 1 ->
                    neighbourCard.getBottomLeftCorner().isAttachable() || neighbourCard.getTopLeftCorner().isAttachable();
            case -1 ->
                    neighbourCard.getTopRightCorner().isAttachable() || neighbourCard.getBottomRightCorner().isAttachable();
            default -> false;
        };
    }
    private boolean hasValidNeighbours(int x, int y){
        int[][] offsets = {{1, 1}, {1, -1}, {-1, -1}, {-1, 1}};
        int validNeighbourCount = 0;
        for (int[] offset : offsets) {
            PlaceableCard neighbourCard = lookAtCoordinates(x + offset[0], y + offset[1]);
            if (neighbourCard != null) {
                if (!hasValidCorner(neighbourCard, offset)) return false;
                else validNeighbourCount++;
            }
        }
        return validNeighbourCount>0;
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
     * @param x x coordinate
     * @param y y coordinate
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
            case fungi -> this.fungiCards.add(card);
            case animal -> this.animalCards.add(card);
            case plant -> this.plantCards.add(card);
            case insect -> this.insectCards.add(card);
        }
    }

    /**
     * adds the given resource to the total resource amount
     * @param resource resource to add to the total visible amount
     */
    private void addResource(Resource resource){

        switch (resource){
            case fungi -> fungiCount++;
            case animal -> animalCount++;
            case plant -> plantCount++;
            case insect -> insectCount++;
            case scroll -> scrollCount++;
            case inkPot -> inkPotCount++;
            case feather -> featherCount++;
            default -> {}
        }

    }

    /**
     * removes the given resource to the total resource amount
     * @param resource resource to add to the total visible amount
     */
    private void removeResource(Resource resource){

        switch (resource){
            case fungi -> fungiCount--;
            case animal -> animalCount--;
            case plant -> plantCount--;
            case insect -> insectCount--;
            case scroll -> scrollCount--;
            case inkPot -> inkPotCount--;
            case feather -> featherCount--;
            default -> {}
        }

    }

}
