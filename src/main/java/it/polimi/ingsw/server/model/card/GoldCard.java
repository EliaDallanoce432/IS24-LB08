package it.polimi.ingsw.server.model.card;

import it.polimi.ingsw.server.model.GameField;
import it.polimi.ingsw.server.model.card.GoldCardStrategy.GoldCardContext;
import it.polimi.ingsw.server.model.json.JsonCardsReader;
import it.polimi.ingsw.util.customexceptions.CannotOpenJSONException;
import it.polimi.ingsw.util.customexceptions.InvalidIdException;

/**
 * This class represents a Gold card.
 */
public class GoldCard extends PlaceableCard {
    protected GoldCardContext context;

    public GoldCard() {
        // for testing purpose only
    }
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
     * Calculates the points earned with this gold card.
     * @param gameField Reference to the game field the card is placed on.
     * @return Points earned by placing the card.
     */
    public int placementPoints(GameField gameField) {
        return context.executePointsCalculation(this.getPoints(),gameField,this);
    }

    public void setContext(GoldCardContext context) {
        this.context = context;
    }
}
