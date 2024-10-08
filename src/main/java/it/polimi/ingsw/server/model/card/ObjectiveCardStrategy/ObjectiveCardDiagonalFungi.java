package it.polimi.ingsw.server.model.card.ObjectiveCardStrategy;

import it.polimi.ingsw.server.model.GameField;
import it.polimi.ingsw.server.model.card.PlaceableCard;
import it.polimi.ingsw.util.supportclasses.Resource;
import java.util.ArrayList;

/**
 * This class is used to implement the diagonal fungi strategy.
 */
public class ObjectiveCardDiagonalFungi implements ObjectiveStrategy {

    private int diagonalFungiTriplets(GameField gameField, ArrayList<PlaceableCard> fungiCards) {
        int triplets = 0;
        ArrayList<PlaceableCard> visited = new ArrayList<>();
        for (PlaceableCard card : fungiCards) {
            //if(arrayContainsCard(visited,card)) continue;
            if(visited.contains(card)) continue;
            //moves from the current card to the top of the diagonal
            PlaceableCard nextCard = card; //next card in the diagonal pattern going upwards
            PlaceableCard currentCard = null;
            while(nextCard != null) {
                if(nextCard.getCardKingdom() == Resource.fungi) {
                    currentCard = nextCard;
                    nextCard = gameField.lookAtCoordinates(nextCard.getX()+1, nextCard.getY()+1);
                }
                else nextCard = null;
            }
            //counts the triplets in the diagonal
            int counter = 0; //keeps track of consecutive cards in the pattern to detect triplets
            while(currentCard != null && currentCard.getCardKingdom() == Resource.fungi) {
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
     * Used to calculate the amount of points given for each diagonalFungiTriplet completed on
     * the game-field.
     * @param pointsOnTheCard Points given by the objective card.
     * @param gamefield Reference to the game-field that is being analysed.
     * @return The amount of calculated points on the specific game-field.
     */
    public int calculatePoints(int pointsOnTheCard, GameField gamefield) {
        return pointsOnTheCard*diagonalFungiTriplets(gamefield, gamefield.getFungiCards());
    }
}
