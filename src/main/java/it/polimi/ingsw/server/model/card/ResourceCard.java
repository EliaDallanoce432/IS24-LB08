package it.polimi.ingsw.server.model.card;

import it.polimi.ingsw.server.model.GameField;
import it.polimi.ingsw.server.model.json.JsonCardsReader;
import it.polimi.ingsw.util.customexceptions.CannotOpenJSONException;
import it.polimi.ingsw.util.customexceptions.InvalidIdException;

/**
 * This class represents a Resource card
 */
public class ResourceCard extends PlaceableCard {

    public ResourceCard () {
        // for testing purpose only
    }
    public ResourceCard(int id) {
        try {
            JsonCardsReader.loadResourceCard(id, this);
        } catch (CannotOpenJSONException e) {
            throw new RuntimeException(e);
        }
        catch (InvalidIdException e)
        {
            System.err.println(e.getMessage());
        }
    }

    /**
     * returns the points earned by placing this resource card
     * @param gameField game field
     * @return score of placement
     */
    public int placementPoints(GameField gameField) {
        return this.getPoints();
    }

}
