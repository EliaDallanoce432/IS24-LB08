package it.polimi.ingsw.server.model.card;

import it.polimi.ingsw.server.model.GameField;
import it.polimi.ingsw.server.model.GoldCardStrategy.GoldCardContext;
import it.polimi.ingsw.server.model.json.JsonCardsReader;
import it.polimi.ingsw.util.supportclasses.Resource;
import javafx.scene.Parent;

public class GoldCard extends PlaceableCard {

    protected GoldCardContext context;
    protected GameField parentGameField;

    public GoldCard(int id, GameField parentGameField){
        JsonCardsReader.readerGoldCard(id,this);
        this.backTopLeftCorner=new Corner(Resource.none, true,this);
        this.backTopRightCorner=new Corner(Resource.none, true,this);
        this.backBottomLeftCorner=new Corner(Resource.none, true,this);
        this.backBottomRightCorner=new Corner(Resource.none, true,this);
        this.parentGameField = parentGameField;
        this.facingUp=true;
        this.x=0;
        this.y=0;
    }

    public int placementPoints() {
        return context.executePointsCalculation(this.getPoints(),this.getParentGameField(),this);
    }

    public GameField getParentGameField() {
        return parentGameField;
    }

    public void setContext(GoldCardContext context) {
        this.context = context;
    }
}
