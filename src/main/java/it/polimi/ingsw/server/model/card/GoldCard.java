package it.polimi.ingsw.server.model.card;

import it.polimi.ingsw.server.model.GameField;
import it.polimi.ingsw.server.model.GoldCardStrategy.GoldCardContext;
import it.polimi.ingsw.server.model.json.JsonCardsReader;
import it.polimi.ingsw.util.supportclasses.Resource;
import javafx.scene.Parent;

public class GoldCard extends PlaceableCard {

    protected GoldCardContext context;


    public GoldCard(int id){
        JsonCardsReader.readerGoldCard(id,this);
        this.backTopLeftCorner=new Corner(Resource.none, true,this);
        this.backTopRightCorner=new Corner(Resource.none, true,this);
        this.backBottomLeftCorner=new Corner(Resource.none, true,this);
        this.backBottomRightCorner=new Corner(Resource.none, true,this);
        this.facingUp=true;
        this.x=0;
        this.y=0;
    }

    public int placementPoints(GameField gameField) {
        return context.executePointsCalculation(this.getPoints(),gameField,this);
    }

    public void setContext(GoldCardContext context) {
        this.context = context;
    }
}
