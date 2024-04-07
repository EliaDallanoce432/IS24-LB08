package it.polimi.ingsw.server.model.ObjectiveCardStrategy;

import it.polimi.ingsw.server.model.GameField;
import it.polimi.ingsw.server.model.card.PlaceableCard;
import it.polimi.ingsw.util.supportclasses.Resource;

import java.util.HashMap;

public class ObjectiveCardDiagonalInsect implements ObjectiveStrategy {

    public int findInsectTriad(HashMap<String,PlaceableCard> grid, int x, int y)
    {/*
        int cont=0;
        int num_triad=0;
        for (int row = x-1, column=y-1 ; row>=0 && column >=0; --row, --column) {
            if(gamefield.lookAtCoordinates(x, d-x).getCardKingdom()==Resource.insect)
                cont++;
            else
                cont=0;
            if(cont==3) {
                num_triad++;
                cont = 0;
            }
        }
        return num_triad;
    */
    return 0;}
    public int calculatePoints(int pointsOnTheCard, GameField gamefield) {
    /*
        int tot_triads=0;
        int n=gamefield.getCardsGrid().length;
        int j=n-1;
        for (int i = 0; i < n; ++i) {
            tot_triads+=findInsectTriad(gamefield.getCardsGrid(), i, j);
        }
        int i=n-1;
        for (j = n-2; j>=0; --j) {
            tot_triads+=findInsectTriad(gamefield.getCardsGrid(), i, j);
        }
        return pointsOnTheCard*tot_triads;

    */
    return 0;}
}
