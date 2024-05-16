package it.polimi.ingsw.server.model.card.ObjectiveCardStrategy;

import it.polimi.ingsw.server.model.GameField;
import it.polimi.ingsw.server.model.card.PlaceableCard;
import it.polimi.ingsw.util.supportclasses.Resource;

import java.util.ArrayList;

/**
 * This class is used to implement the diagonal animal strategy
 */
public class ObjectiveCardDiagonalAnimal implements ObjectiveStrategy {
    private int diagonalAnimalTriplets(GameField gameField, ArrayList<PlaceableCard> animalCards) {
        int triplets = 0;
        ArrayList<PlaceableCard> visited = new ArrayList<>();
        for (PlaceableCard card : animalCards) {
            //if(arrayContainsCard(visited,card)) continue;
            if(visited.contains(card)) continue;
            //moves from the current card to the top of the diagonal
            PlaceableCard nextCard = card; //next card in the diagonal pattern going upwards
            PlaceableCard currentCard = null;
            while(nextCard != null) {
                if(nextCard.getCardKingdom() == Resource.animal) {
                    currentCard = nextCard;
                    nextCard = gameField.lookAtCoordinates(nextCard.getX()+1, nextCard.getY()+1);
                }
                else nextCard = null;
            }
            //counts the triplets in the diagonal
            int counter = 0; //keeps track of consecutive cards in the pattern to detect triplets
            while(currentCard != null && currentCard.getCardKingdom() == Resource.animal) {
                if(counter < 2) {
                    counter++;
                }
                else {
                    counter = 0;
                    triplets ++;
                    gameField.getPlayer().increaseNumOfCompletedObjective();
                }
                visited.add(currentCard);
                currentCard = gameField.lookAtCoordinates(currentCard.getX() - 1, currentCard.getY() - 1);
            }
        }
        return triplets;
    }

    /**
     * Used to calculate the amount of points given for each diagonalAnimalTriplet completed on
     * the game-field
     * @param pointsOnTheCard points multiplier written on the card
     * @param gamefield game-field on which the objective has to be checked
     * @return the amount of calculated points
     */
    public int calculatePoints(int pointsOnTheCard, GameField gamefield) {
        return pointsOnTheCard* diagonalAnimalTriplets(gamefield, gamefield.getAnimalCards());
    }
}
