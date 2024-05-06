package it.polimi.ingsw.client.model;

import it.polimi.ingsw.client.view.observers.ModelObserver;

import java.util.ArrayList;
import java.util.List;

public abstract class ObservableModel {

    protected final List<ModelObserver> observers;

    public ObservableModel() {
        observers = new ArrayList<ModelObserver>();
    }

    public synchronized void addObserver(ModelObserver observer) {
        observers.add(observer);
    }

    public synchronized void removeObserver(ModelObserver observer) {
        observers.remove(observer);
    }

    protected void notifyObservers() {
        for (ModelObserver observer : observers) {
            observer.update();
        }
    }



}