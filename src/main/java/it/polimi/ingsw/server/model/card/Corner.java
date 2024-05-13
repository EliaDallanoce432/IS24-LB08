package it.polimi.ingsw.server.model.card;

import it.polimi.ingsw.util.supportclasses.Resource;

public class Corner {
    protected Resource resource; //resource present in the corner
    protected boolean visible; //true if the corner is visible
    protected boolean attached; //true if it's connected to another corner
    protected boolean attachable; //true if corner is present on the card for connection of other cards on top of it
    protected PlaceableCard parentCard; //card owning the corner

    public Corner(Resource resource, boolean attachable, PlaceableCard card) {
        this.resource = resource;
        this.attachable = attachable;
        this.parentCard = card;
        attached = false;
        visible = true;
    }

    @Override
    public boolean equals(Object obj) {
        Corner other = (Corner) obj;
        if (this.resource != other.resource) return false;
        if (this.attached != other.attached) return false;
        if (this.attachable != other.attachable) return false;
        if (this.visible != other.visible) return false;
        return true;
    }

    /**
     * returns the resource value on the corner
     * @return Resource
     */
    public Resource getResource() {
        if(this.isAttachable()) return resource;
        else return Resource.none;
    }

    /**
     * on default is true
     * attribute set on true if the corner is visible, false if the corner is covered
     * @param visible new value
     */
    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    /**
     * returns true is the corner is on top (visible), false otherwise
     * @return boolean
     */
    public boolean isVisible() {
        return visible;
    }

    /**
     * returns true if the corner is marked on the card and other cards can be connected to it (on top)
     * @return boolean
     */
    public boolean isAttachable() {
        return attachable;
    }

    public boolean isAttached() {
        return attached;
    }

    public void setAttached(boolean attached) {
        this.attached = attached;
    }
}
