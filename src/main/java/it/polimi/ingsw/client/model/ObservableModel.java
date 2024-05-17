package it.polimi.ingsw.client.model;

import it.polimi.ingsw.client.view.observers.ModelObserver;
import java.util.ArrayList;
import java.util.List;

/**
 * This abstract class provides a foundation for implementing observable models in your application.
 */
public abstract class ObservableModel {

    private final List<ModelObserver> observers;

    public ObservableModel() {
        observers = new ArrayList<>();
    }

    /**
     * registers an observer with this model. The observer will be notified whenever the model's data changes
     * @param observer The ModelObserver to unregister
     */
    public synchronized void addObserver(ModelObserver observer) {
        observers.add(observer);
    }

    public synchronized void removeObserver(ModelObserver observer) {
        observers.remove(observer);
    }

    /**
     * notifies all registered observers that the model's data has changed. This method iterates through the list of observers
     */
    protected void notifyObservers() {
        for (ModelObserver observer : observers) {
            System.out.println("Observer: " + observer);
            observer.update();
        }
    }
}
