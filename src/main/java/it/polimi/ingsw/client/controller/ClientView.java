package it.polimi.ingsw.client.controller;

import it.polimi.ingsw.client.view.SceneLoader;
import it.polimi.ingsw.client.view.ViewController;
import it.polimi.ingsw.client.view.WelcomeViewController;
import javafx.application.Application;
import javafx.stage.Stage;

public class ClientView extends Application {

    static ClientController clientController;



    @Override
    public void start(Stage primaryStage) throws Exception{

        SceneLoader.setClientController(clientController);
        SceneLoader.setCurrentStage(primaryStage);

        primaryStage.setScene(SceneLoader.loadWelcomeScene());
        primaryStage.setTitle("Codex - Game Demo");
        primaryStage.show();

    }











}