package it.polimi.ingsw.server.model;

import it.polimi.ingsw.util.supportclasses.Resource;

public class Corner {
    private Resource resource;
    private boolean onTop;
    private boolean attachable;
    private PlaceableCard parentCard;

    public Corner(Resource resource, boolean attachable, PlaceableCard card) {
        this.resource = resource;
        this.attachable = attachable;
        this.parentCard = card;
        onTop = true;
    }

    /**
     * returns the resource value on the corner
     * @return Resource
     */
    public Resource getResource() {
        return resource;
    }

    /**
     * on default is true
     * attribute set on true if the corner is visible, false if the corner is covered
     * @param onTop new value
     */
    public void setOnTop(boolean onTop) {
        this.onTop = onTop;
    }

    /**
     * returns true is the corner is on top (visible), false otherwise
     * @return boolean
     */
    public boolean isOnTop() {
        return onTop;
    }

    /**
     * returns true if the corner is marked on the card and other cards can be connected to it (on top)
     * @return boolean
     */
    public boolean isAttachable() {
        return attachable;
    }

    /**
     * return the card that owns this corner
     * @return PlaceableCard
     */
    public PlaceableCard getParentCard() {
        return parentCard;
    }
}
