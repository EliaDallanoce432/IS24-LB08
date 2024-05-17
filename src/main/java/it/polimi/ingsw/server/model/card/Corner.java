package it.polimi.ingsw.server.model.card;

import it.polimi.ingsw.util.supportclasses.Resource;

/**
 * This class represents a Corner on the card
 */
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
        if(!(obj instanceof Corner other)) {return false;}
        else if (this.resource != other.resource) return false;
        else if (this.attached != other.attached) return false;
        else if (this.attachable != other.attachable) return false;
        else if (this.visible != other.visible) return false;
        return true;
    }

    public Resource getResource() {
        if(this.isAttachable()) return resource;
        else return Resource.none;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public boolean isVisible() {
        return visible;
    }

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
