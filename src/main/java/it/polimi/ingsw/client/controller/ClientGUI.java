package it.polimi.ingsw.client.controller;

import it.polimi.ingsw.client.view.StageManager;
import it.polimi.ingsw.client.view.observers.*;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * This class is the primary application class responsible for
 * initializing and managing the GUI for the client-side
 */
public class ClientGUI extends Application {


    /**
     * starts JavaFX frameworks when the application starts
     * @param primaryStage The primary stage of the application which will be used to display all the scenes of the GUI
     */
    @Override
    public void start(Stage primaryStage) {

        //initializing observers
        new AvailableGamesModelObserver();
        new ClientStateModelObserver();
        new DeckObserver();
        new GameFieldObserver();
        new HandObserver();
        new ObjectivesObserver();
        new PlayerObserver();
        new ScoreBoardObserver();
        new SelectableCardsObserver();

        StageManager.setCurrentStage(primaryStage);
        StageManager.loadWelcomeScene();
        primaryStage.setTitle("Codex - Game Demo");
    }

}