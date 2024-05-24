package it.polimi.ingsw.client.view.observers;

/**
 * This interface should be implemented by any class that
 * wants to be notified of changes in the model it observes.
 * This interface follows the Observer design pattern, where the observer
 * will be updated when there is a change in the observable model.
 */
public interface ModelObserver {

    /**
     * This method is called when the observed model is updated.
     * Implementing classes should define the specific behavior
     * that should occur when the model changes.
     */
    public void update();
}
