package it.polimi.ingsw.client.model;

public class DeckModel extends ObservableModel{

    private int resourceDeckTopCardId;
    private int resourceDeckLeftCardId;
    private int resourceDeckRightCardId;
    private int goldDeckTopCardId;
    private int goldDeckLeftCardId;
    private int goldDeckRightCardId;

    private static DeckModel instance;

    public static DeckModel getInstance(){

        if (instance ==null) instance = new DeckModel();
        return instance;

    }

    public int getResourceDeckTopCardId() {
        return resourceDeckTopCardId;
    }

    public int getResourceDeckLeftCardId() {
        return resourceDeckLeftCardId;
    }

    public int getResourceDeckRightCardId() {
        return resourceDeckRightCardId;
    }

    public int getGoldDeckTopCardId() {
        return goldDeckTopCardId;
    }

    public int getGoldDeckLeftCardId() {
        return goldDeckLeftCardId;
    }

    public int getGoldDeckRightCardId() {
        return goldDeckRightCardId;
    }

    public void updateDecks(int resourceDeckTopCardId, int resourceDeckLeftCardId, int resourceDeckRightCardId, int goldTopCardId, int goldLeftCardId, int goldRightCardId) {
        this.resourceDeckTopCardId = resourceDeckTopCardId;
        this.resourceDeckLeftCardId = resourceDeckLeftCardId;
        this.resourceDeckRightCardId = resourceDeckRightCardId;
        this.goldDeckTopCardId = goldTopCardId;
        this.goldDeckLeftCardId = goldLeftCardId;
        this.goldDeckRightCardId = goldRightCardId;
        notifyObservers();
    }
}
