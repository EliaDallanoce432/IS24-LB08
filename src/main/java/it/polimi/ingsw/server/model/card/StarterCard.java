package it.polimi.ingsw.server.model.card;

import it.polimi.ingsw.server.model.json.JsonCardsReader;
import it.polimi.ingsw.util.supportclasses.*;
import java.util.ArrayList;

public class StarterCard extends PlaceableCard {
    protected ArrayList<Resource> backCentralResources;

    public StarterCard(int id) {
        JsonCardsReader.readerStarterCard(id,this);
    }

    @Override
    public int placementPoints() {
        return 0;
    }

    public void setBackCentralResources(ArrayList<Resource> backCentralResources) {
        this.backCentralResources = backCentralResources;
    }

    public ArrayList<Resource> getBackCentralResources() {
        return backCentralResources;
    }
}
