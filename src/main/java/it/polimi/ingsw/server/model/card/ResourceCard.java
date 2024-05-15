package it.polimi.ingsw.server.model.card;

import it.polimi.ingsw.server.model.GameField;
import it.polimi.ingsw.server.model.json.JsonCardsReader;
import it.polimi.ingsw.util.customexceptions.CannotOpenJSONException;
import it.polimi.ingsw.util.customexceptions.InvalidIdException;
import it.polimi.ingsw.util.supportclasses.Resource;

/**
 * This class represents a Resource card
 */
public class ResourceCard extends PlaceableCard {

    public ResourceCard () {}
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
     * method returns the points earned by placing this resource card
     * @param gameField game field
     * @return int
     */
    public int placementPoints(GameField gameField) {
        return this.getPoints();
    }

}
