package it.polimi.ingsw.client.controller;

import it.polimi.ingsw.client.view.StageManager;
import javafx.application.Application;
import javafx.stage.Stage;

public class ClientGUI extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception{

        StageManager.setCurrentStage(primaryStage);

        StageManager.loadWelcomeScene();
        primaryStage.setTitle("Codex - Game Demo");
    }

    public static void main(String[] args) {
        launch(args);
    }











}