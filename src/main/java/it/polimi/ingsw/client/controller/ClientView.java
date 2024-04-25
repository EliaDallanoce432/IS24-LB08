package it.polimi.ingsw.client.controller;

import it.polimi.ingsw.client.view.SceneLoader;
import javafx.application.Application;
import javafx.stage.Stage;

public class ClientView extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        SceneLoader sceneLoader = new SceneLoader();

        primaryStage.setScene(sceneLoader.loadWelcomeScene());
        primaryStage.setTitle("Codex - Game Demo");
        primaryStage.show();

    }


    public static void main(String[] args) {
        launch(args);
    }




}