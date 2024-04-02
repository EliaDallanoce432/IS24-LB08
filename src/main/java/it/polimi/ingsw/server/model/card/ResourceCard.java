package it.polimi.ingsw.server.model.card;

import it.polimi.ingsw.server.model.GameField;
import it.polimi.ingsw.server.model.json.JsonCardsReader;
import it.polimi.ingsw.util.supportclasses.Resource;

public class ResourceCard extends PlaceableCard {

    public ResourceCard(int id) {
        JsonCardsReader.loadResourceCard(id, this);
        this.backTopLeftCorner=new Corner(Resource.none, true,this);
        this.backTopRightCorner=new Corner(Resource.none, true,this);
        this.backBottomLeftCorner=new Corner(Resource.none, true,this);
        this.backBottomRightCorner=new Corner(Resource.none, true,this);
        this.requiredAnimalResourceAmount=0;
        this.requiredFungiResourceAmount=0;
        this.requiredPlantResourceAmount=0;
        this.requiredInsectResourceAmount=0;
        this.facingUp=true;
        this.x=0;
        this.y=0;
    }

    public int placementPoints(GameField gameField) {
        return this.getPoints();
    }

}
