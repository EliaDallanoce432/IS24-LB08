package it.polimi.ingsw.server.model.card;

import it.polimi.ingsw.server.model.GameField;
import it.polimi.ingsw.server.model.GoldCardStrategy.GoldCardContext;

public class GoldCard extends PlaceableCard {

    protected GoldCardContext context;
    protected GameField parentGameField;

    protected GoldCard(){

    }

    public int placementPoints() {
        return context.executePointsCalculation(this.getPoints(),this.getParentGameField(),this);
    }

    public GameField getParentGameField() {
        return parentGameField;
    }
}
