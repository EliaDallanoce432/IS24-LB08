package it.polimi.ingsw.server.model.ObjectiveCardStrategy;

import it.polimi.ingsw.server.model.GameField;
import it.polimi.ingsw.server.model.card.PlaceableCard;
import it.polimi.ingsw.util.supportclasses.Resource;

import java.util.ArrayList;

public class ObjectiveCardDiagonalAnimal extends ObjectiveStrategy {
    private int diagonalAnimalTriplets(GameField gameField, ArrayList<PlaceableCard> animalCards) {
        int triplets = 0;
        ArrayList<PlaceableCard> visited = new ArrayList<>();
        for (PlaceableCard card : animalCards) {
            if(arrayContainsCard(visited,card)) continue;
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

    public int calculatePoints(int pointsOnTheCard, GameField gamefield) {
        return pointsOnTheCard* diagonalAnimalTriplets(gamefield, gamefield.getAnimalCards());
    }
}
