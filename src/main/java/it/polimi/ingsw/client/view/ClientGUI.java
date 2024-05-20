package it.polimi.ingsw.client.view;

import it.polimi.ingsw.client.view.observers.*;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.InputStream;

/**
 * This class is the primary application class responsible for
 * initializing and managing the GUI for the client-side
 */
public class ClientGUI extends Application {


    /**
     * Starts the JavaFX framework when the application begins. This method sets up the primary stage,
     * initializes all necessary observers, and loads the welcome scene.
     *
     * @param primaryStage the primary stage of the application which will be used to display all the scenes of the GUI
     */
    @Override
    public void start(Stage primaryStage) {
        //initializing observers
        new AvailableGamesObserver();
        new ClientStateObserver();
        new DeckObserver();
        new GameFieldObserver();
        new HandObserver();
        new ObjectivesObserver();
        new PlayerObserver();
        new ScoreBoardObserver();
        new SelectableCardsObserver();
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream is = classloader.getResourceAsStream("Images/codex_logo_crop.png");
        assert is != null;
        primaryStage.getIcons().add(new Image(is));
        StageManager.setCurrentStage(primaryStage);
        StageManager.loadTitleScreenScene();
        primaryStage.setTitle("Codex");
    }

}