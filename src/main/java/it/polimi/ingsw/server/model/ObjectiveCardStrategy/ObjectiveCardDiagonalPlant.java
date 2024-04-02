package it.polimi.ingsw.server.model.ObjectiveCardStrategy;

import it.polimi.ingsw.server.model.GameField;
import it.polimi.ingsw.server.model.card.PlaceableCard;
import it.polimi.ingsw.util.supportclasses.Resource;

public class ObjectiveCardDiagonalPlant implements ObjectiveStrategy{
    public int findPlantTriad(PlaceableCard[][] grid, int x, int y)
    {
        int cont=0;
        int num_triad=0;
        for (int row = x-1, column=y-1 ; row>=0 && column >=0; --row, --column) {
            if(grid[x][y].getCardKingdom()== Resource.plant)
                cont++;
            else
                cont=0;
            if(cont==3) {
                num_triad++;
                cont = 0;
            }
        }
        return num_triad;
    }
    public int calculatePoints(int pointsOnTheCard, GameField gamefield) {

        int tot_triads=0;
        int n=gamefield.getCardsGrid().length;
        int j=n-1;
        for (int i = 0; i < n; ++i) {
            tot_triads+=findPlantTriad(gamefield.getCardsGrid(), i, j);
        }
        int i=n-1;
        for (j = n-2; j>=0; --j) {
            tot_triads+=findPlantTriad(gamefield.getCardsGrid(), i, j);
        }
        return pointsOnTheCard*tot_triads;

    }
}