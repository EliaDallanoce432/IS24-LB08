package it.polimi.ingsw.client.controller;

import it.polimi.ingsw.client.view.StageManager;
import javafx.application.Application;
import javafx.stage.Stage;

public class ClientGUI extends Application {

    static ClientController clientController;



    @Override
    public void start(Stage primaryStage) throws Exception{

        StageManager.setCurrentStage(primaryStage);

        primaryStage.setScene(StageManager.loadWelcomeScene());
        primaryStage.setTitle("Codex - Game Demo");
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }











}