package it.polimi.ingsw.server.controller;

public interface GameObserver {

    void notifyReady();

    void notifyStarterCardAndSecretObjectiveSelected();

    void notifyClientConnectedCountChanged();

}
