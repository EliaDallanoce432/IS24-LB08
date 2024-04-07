package it.polimi.ingsw.server.model.card;

import it.polimi.ingsw.server.model.GameField;
import it.polimi.ingsw.server.model.json.JsonCardsReader;
import it.polimi.ingsw.util.customexceptions.CannotOpenJSONException;
import it.polimi.ingsw.util.supportclasses.Resource;

public class ResourceCard extends PlaceableCard {

    public ResourceCard () {

    }
    public ResourceCard(int id) {
        try {
            JsonCardsReader.loadResourceCard(id, this);
        } catch (CannotOpenJSONException e) {
            throw new RuntimeException(e);
        }
        this.backTopLeftCorner=new Corner(Resource.none, true,this);
        this.backTopRightCorner=new Corner(Resource.none, true,this);
        this.backBottomLeftCorner=new Corner(Resource.none, true,this);
        this.backBottomRightCorner=new Corner(Resource.none, true,this);
        this.facingUp=true;
    }

    public int placementPoints(GameField gameField) {
        return this.getPoints();
    }

}
