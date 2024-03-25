package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.model.GoldCardStrategy.GoldCardContext;
import it.polimi.ingsw.util.supportclasses.*;
import java.util.ArrayList;

public class GoldCard extends PlaceableCard{
    private ArrayList<Resource> requirements;
    private GoldCardContext context;

    public GoldCard(){

    }
}
