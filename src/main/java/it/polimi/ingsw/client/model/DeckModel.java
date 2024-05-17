package it.polimi.ingsw.client.model;

/**
 * This class represents an ObservableModel that keeps track of the current DeckModel
 */
public class DeckModel extends ObservableModel{

    private int resourceDeckTopCardId;
    private int resourceDeckLeftCardId;
    private int resourceDeckRightCardId;
    private int goldDeckTopCardId;
    private int goldDeckLeftCardId;
    private int goldDeckRightCardId;

    private static DeckModel instance;

    /**
     * returns the singleton instance of DeckModel
     * @return The singleton instance of DeckModel
     */
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

    /**
     * updates the deck information for both the resource deck and the gold deck and notifies any registered observers that the data has changed.
     * @param resourceDeckTopCardId The identifier for the card in the top position of the resource deck.
     * @param resourceDeckLeftCardId The identifier for the card in the left position of the resource deck.
     * @param resourceDeckRightCardId The identifier for the card in the right position of the resource deck.
     * @param goldTopCardId The identifier for the card in the top position of the gold deck.
     * @param goldLeftCardId The identifier for the card in the left position of the gold deck.
     * @param goldRightCardId The identifier for the card in the right position of the gold deck.
     */
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
