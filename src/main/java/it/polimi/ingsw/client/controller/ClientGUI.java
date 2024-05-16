package it.polimi.ingsw.client.controller;

import it.polimi.ingsw.client.view.StageManager;
import it.polimi.ingsw.client.view.observers.*;
import javafx.application.Application;
import javafx.stage.Stage;

public class ClientGUI extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception{

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

    public static void main(String[] args) {
        launch(args);
    }











}