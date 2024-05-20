package it.polimi.ingsw.client.view;

import it.polimi.ingsw.client.view.viewControllers.ViewController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;

import static it.polimi.ingsw.util.supportclasses.ViewConstants.*;

/**
 * The StageManager class manages the current stage and view controller of the application.
 * It provides methods to load different scenes with specified backgrounds.
 */
public class StageManager {

    private static Stage currentStage;
    private static ViewController currentViewController;


    public static void setCurrentStage(Stage currentStage) {
        StageManager.currentStage = currentStage;
    }

    public static Stage getCurrentStage() {
        return currentStage;
    }


    public static ViewController getCurrentViewController() {
        return currentViewController;
    }

    /**
     * Loads an ImageView with the specified background image.
     *
     * @param path the path to the background image
     * @return an ImageView containing the background image
     */
    private static ImageView loadBackground(String path) {
        Image backgroundImage = new Image(StageManager.class.getResourceAsStream(path));
        ImageView backgroundImageView = new ImageView(backgroundImage);
        backgroundImageView.setPreserveRatio(false);
        return backgroundImageView;
    }

    /**
     * Creates a StackPane with a background image and loads the specified FXML.
     *
     * @param fxmlPath the path to the FXML file to be loaded
     * @return a StackPane containing the background image and loaded FXML content
     */
    private static StackPane createStackPaneWithBackground(String fxmlPath) {
        FXMLLoader loader = new FXMLLoader(StageManager.class.getResource(fxmlPath));
        Parent root;
        try {
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        currentViewController = loader.getController();
        StackPane stackPane = new StackPane();
        ImageView backgroundImageView = loadBackground("/Images/Backgrounds/wood_background.jpg");
        stackPane.getChildren().add(backgroundImageView);
        stackPane.getChildren().add(root);
        backgroundImageView.fitWidthProperty().bind(stackPane.widthProperty());
        backgroundImageView.fitHeightProperty().bind(stackPane.heightProperty());

        return stackPane;
    }

    public static void loadTitleScreenScene() {
        StackPane stackPane = createStackPaneWithBackground("TitleScreenView.fxml");
        currentStage.setWidth(SCENE_WIDTH);
        currentStage.setHeight(SCENE_HEIGHT);
        showScene(new Scene(stackPane));
    }

    public static void loadConnectToServerScene() {
        StackPane stackPane = createStackPaneWithBackground("ConnectToServerView.fxml");
        currentStage.setWidth(SCENE_WIDTH);
        currentStage.setHeight(SCENE_HEIGHT);
        showScene(new Scene(stackPane));
    }


    /**
     * Loads the GameBoard scene.
     */
    public static void loadGameBoardScene() {
        StackPane stackPane = createStackPaneWithBackground("GameBoardView.fxml");
        stackPane.prefWidthProperty().bind(currentStage.widthProperty());
        stackPane.prefHeightProperty().bind(currentStage.heightProperty());
        //centerStage(currentStage);
        showScene(new Scene(stackPane));
    }
    private static void centerStage(Stage stage)
    {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        //GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
        double anchorX, anchorY;
//        double screenWidth=graphicsEnvironment.getDefaultScreenDevice().getDisplayMode().getWidth();
//        double screenHeight=graphicsEnvironment.getDefaultScreenDevice().getDisplayMode().getHeight();
        if(screenSize.getWidth()<=currentStage.getWidth() || screenSize.getHeight()<=currentStage.getHeight())
        {
            anchorX=0;
            anchorY=0;
        }
        else {
            anchorX = (screenSize.getWidth() - currentStage.getWidth()) / 2;
            anchorY = (screenSize.getHeight() - currentStage.getHeight()) / 2;
        }
        stage.setX(anchorX);
        stage.setY(anchorY);
    }

    /**
     * Loads the Welcome scene.
     */
    public static void loadWelcomeScene() {
        StackPane stackPane = createStackPaneWithBackground("WelcomeView.fxml");
        currentStage.setWidth(SCENE_WIDTH);
        currentStage.setHeight(SCENE_HEIGHT);
        showScene(new Scene(stackPane));
    }

    /**
     * Loads the CreateGame scene.
     */
    public static void loadCreateGameScene() {
        StackPane stackPane = createStackPaneWithBackground("CreateGameView.fxml");
        showScene(new Scene(stackPane));
    }

    /**
     * Loads the JoinGame scene.
     */
    public static void loadJoinGameScene() {
        StackPane stackPane = createStackPaneWithBackground("JoinGameView.fxml");
        showScene(new Scene(stackPane));
    }

    /**
     * Loads the WaitForPlayers scene.
     */
    public static void loadWaitForPlayersScene() {
        StackPane stackPane = createStackPaneWithBackground("WaitForPlayersView.fxml");
        showScene(new Scene(stackPane));
    }

    /**
     * Loads the ChooseCards scene.
     */
    public static void loadChooseCardsScene() {
        StackPane stackPane = createStackPaneWithBackground("ChooseCardsView.fxml");
        showScene(new Scene(stackPane));
    }

    /**
     * Loads the LostConnection scene.
     */
    public static void loadLostConnectionScene() {
        StackPane stackPane = createStackPaneWithBackground("LostConnectionView.fxml");
        currentStage.setWidth(SCENE_WIDTH);
        currentStage.setHeight(SCENE_HEIGHT);
        showScene(new Scene(stackPane));
    }

    /**
     * Loads the KickedFromGame scene.
     */
    public static void loadKickedFromGameScene() {
        StackPane stackPane = createStackPaneWithBackground("KickedFromGameView.fxml");
        currentStage.setWidth(SCENE_WIDTH);
        currentStage.setHeight(SCENE_HEIGHT);
        showScene(new Scene(stackPane));
    }

    /**
     * Loads the Leaderboard scene.
     */
    public static void loadLeaderboardScene() {
        StackPane stackPane = createStackPaneWithBackground("LeaderboardView.fxml");
        currentStage.setWidth(SCENE_WIDTH);
        currentStage.setHeight(SCENE_HEIGHT);
        showScene(new Scene(stackPane));
    }

    /**
     * Shows the specified scene on the current stage.
     *
     * @param scene the scene to be shown
     */
    private static void showScene(Scene scene) {
        currentStage.setOnCloseRequest(event -> System.exit(0));
        currentStage.setScene(scene);
        currentStage.sizeToScene();
        currentStage.show();
    }
}
