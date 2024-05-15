package it.polimi.ingsw.server.model.card;

import it.polimi.ingsw.server.model.GameField;
import it.polimi.ingsw.server.model.GoldCardStrategy.GoldCardContext;
import it.polimi.ingsw.server.model.json.JsonCardsReader;
import it.polimi.ingsw.util.customexceptions.CannotOpenJSONException;
import it.polimi.ingsw.util.customexceptions.InvalidIdException;
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
        catch (InvalidIdException e)
        {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof GoldCard other)) { return super.equals(obj); }
        if (!this.context.equals(other.context)) return false;
        return super.equals(obj);
    }

    /**
     * method returns the points earned with this gold card
     * @param gameField game field
     * @return int
     */
    public int placementPoints(GameField gameField) {
        return context.executePointsCalculation(this.getPoints(),gameField,this);
    }

    /**
     * set context of gold card (strategy pattern)
     * @param context context of gold card
     */
    public void setContext(GoldCardContext context) {
        this.context = context;
    }
}
