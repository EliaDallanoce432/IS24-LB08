package it.polimi.ingsw.server.model.ObjectiveCardStrategy;

import it.polimi.ingsw.server.model.GameField;
import it.polimi.ingsw.server.model.card.PlaceableCard;
import it.polimi.ingsw.util.supportclasses.Resource;

public class ObjectiveCardDiagonalAnimal implements ObjectiveStrategy{
    public int calculatePoints(int pointsOnTheCard, GameField gamefield) {
        PlaceableCard[][] grid = gamefield.getCardsGrid();
        int n= grid.length;
        int tot_triads=0;
        int cont=0;
        for (int d = 0; d < 2*n-1; d++) {
            for (int x = Math.max(0, d-n+1); x < Math.min(n, d+1); x++) {
                if(grid[x][d-x].getCardKingdom()== Resource.animal)
                    cont++;
                else
                    cont=0;
                if(cont==3) {
                    tot_triads++;
                    cont = 0;
                }
            }
        }
        return pointsOnTheCard*tot_triads;

    }
}
