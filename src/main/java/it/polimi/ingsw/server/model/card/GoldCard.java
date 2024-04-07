package it.polimi.ingsw.server.model.card;

import it.polimi.ingsw.server.model.GameField;
import it.polimi.ingsw.server.model.GoldCardStrategy.GoldCardContext;
import it.polimi.ingsw.server.model.json.JsonCardsReader;
import it.polimi.ingsw.util.customexceptions.CannotOpenJSONException;
import it.polimi.ingsw.util.supportclasses.Resource;

public class GoldCard extends PlaceableCard {
    protected GoldCardContext context;

    public GoldCard() {}
    public GoldCard(int id){
        try {
            JsonCardsReader.loadGoldCard(id,this);
        } catch (CannotOpenJSONException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean equals(Object obj) {
        GoldCard other = (GoldCard) obj;
        if(!this.context.equals(other.context))return false;
        return super.equals(obj);
    }

    public int placementPoints(GameField gameField) {
        return context.executePointsCalculation(this.getPoints(),gameField,this);
    }

    public void setContext(GoldCardContext context) {
        this.context = context;
    }
}
