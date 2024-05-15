package it.polimi.ingsw.server.model.card;

import it.polimi.ingsw.server.model.GameField;
import it.polimi.ingsw.server.model.json.JsonCardsReader;
import it.polimi.ingsw.util.customexceptions.CannotOpenJSONException;
import it.polimi.ingsw.util.customexceptions.InvalidIdException;
import it.polimi.ingsw.util.supportclasses.*;
import java.util.ArrayList;
import java.util.Collections;

/**
 * This class represents a Starter card
 */
public class StarterCard extends PlaceableCard {
    protected ArrayList<Resource> backCentralResources;

    public StarterCard() {}
    public StarterCard(int id) {
        try {
            JsonCardsReader.loadStarterCard(id,this);
        } catch (CannotOpenJSONException e) {
            throw new RuntimeException(e);
        }
        catch (InvalidIdException e)
        {
            System.err.println(e.getMessage());
        }
    }

    /**
     * method returns the points earned by placing this starter card
     * @param gameField game field
     * @return int
     */
    @Override
    public int placementPoints(GameField gameField) {
        return 0;
    }

    public void setBackCentralResources(ArrayList<Resource> backCentralResources) {
        this.backCentralResources = backCentralResources;
    }

    public ArrayList<Resource> getBackCentralResources() {
        return backCentralResources;
    }

    @Override
    public boolean equals(Object obj) {
        StarterCard other = (StarterCard) obj;
        if(this.getBackCentralResources().size() != other.getBackCentralResources().size()) return false;
        ArrayList<Resource> otherBackCentralResources = new ArrayList<>(other.getBackCentralResources());
        ArrayList<Resource> thisBackCentralResources = new ArrayList<>(this.getBackCentralResources());
        Collections.sort(thisBackCentralResources);
        Collections.sort(otherBackCentralResources);
        for(int i = 0; i < thisBackCentralResources.size(); i++) {
            if(thisBackCentralResources.get(i) != otherBackCentralResources.get(i)) return false;
        }
        return true;
    }
}
