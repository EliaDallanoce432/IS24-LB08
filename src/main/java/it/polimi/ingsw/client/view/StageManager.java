package it.polimi.ingsw.client.view;

import it.polimi.ingsw.client.view.CLI.CLIViewController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import java.io.IOException;
import static it.polimi.ingsw.util.supportclasses.ViewConstants.*;

/**
 * The StageManager class manages the current stage and view controller of the application.
 * It provides methods to load different scenes with different settings.
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
     * Sets a new instance of CLIViewController as currentViewController.
     */
    public static void enableCLIMode() {
        currentViewController = new CLIViewController();
    }

    /**
     * Loads an ImageView with the specified background image.
     * @param path The path to the background image.
     * @return The ImageView containing the background image.
     */
    @SuppressWarnings("all")
    private static ImageView loadBackground(String path) {
        Image backgroundImage = new Image(StageManager.class.getResourceAsStream(path));
        ImageView backgroundImageView = new ImageView(backgroundImage);
        backgroundImageView.setPreserveRatio(false);
        return backgroundImageView;
    }

    /**
     * Creates a StackPane with an optional background image and loads the specified FXML.
     * @param fxmlPath The path to the FXML file to be loaded.
     * @param withBackground True if the background has to be loaded, False otherwise.
     * @return The StackPane containing the background image and loaded FXML content.
     */
    private static StackPane createStackPane(String fxmlPath, boolean withBackground) {
        FXMLLoader loader = new FXMLLoader(StageManager.class.getResource(fxmlPath));
        Parent root;
        try {
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        currentViewController = loader.getController();
        StackPane stackPane = new StackPane();
        if (withBackground) {
            ImageView backgroundImageView = loadBackground("/Images/Backgrounds/wood_background.jpg");
            stackPane.getChildren().add(backgroundImageView);
            backgroundImageView.fitWidthProperty().bind(stackPane.widthProperty());
            backgroundImageView.fitHeightProperty().bind(stackPane.heightProperty());
        }
        stackPane.getChildren().add(root);

        return stackPane;
    }

    /**
     * Loads the main title scene.
     */
    public static void loadTitleScreenScene() {
        StackPane stackPane = createStackPane("TitleScreenView.fxml", false);
        currentStage.setWidth(SCENE_WIDTH);
        currentStage.setHeight(SCENE_HEIGHT);
        showScene(new Scene(stackPane));
    }

    /**
     * Loads the scene for connecting to the server.
     */
    public static void loadConnectToServerScene() {
        StackPane stackPane = createStackPane("ConnectToServerView.fxml", false);
        currentStage.setWidth(SCENE_WIDTH);
        currentStage.setHeight(SCENE_HEIGHT);
        showScene(new Scene(stackPane));
    }

    /**
     * Loads the GameBoard scene.
     */
    public static void loadGameBoardScene() {
        StackPane stackPane = createStackPane("GameBoardView.fxml", true);
        stackPane.prefWidthProperty().bind(currentStage.widthProperty());
        stackPane.prefHeightProperty().bind(currentStage.heightProperty());
        showScene(new Scene(stackPane));
    }

    /**
     * Loads the Welcome scene.
     */
    public static void loadWelcomeScene() {
        StackPane stackPane = createStackPane("WelcomeView.fxml", false);
        currentStage.setWidth(SCENE_WIDTH);
        currentStage.setHeight(SCENE_HEIGHT);
        showScene(new Scene(stackPane));
    }

    /**
     * Loads the CreateGame scene.
     */
    public static void loadCreateGameScene() {
        StackPane stackPane = createStackPane("CreateGameView.fxml", false);
        showScene(new Scene(stackPane));
    }

    /**
     * Loads the JoinGame scene.
     */
    public static void loadJoinGameScene() {
        StackPane stackPane = createStackPane("JoinGameView.fxml", false);
        showScene(new Scene(stackPane));
    }

    /**
     * Loads the WaitForPlayers scene.
     */
    public static void loadWaitForPlayersScene() {
        StackPane stackPane = createStackPane("WaitForPlayersView.fxml", false);
        showScene(new Scene(stackPane));
    }

    /**
     * Loads the ChooseCards scene.
     */
    public static void loadChooseCardsScene() {
        StackPane stackPane = createStackPane("ChooseCardsView.fxml", false);
        showScene(new Scene(stackPane));
    }

    /**
     * Loads the LostConnection scene.
     */
    public static void loadLostConnectionScene() {
        StackPane stackPane = createStackPane("LostConnectionView.fxml", false);
        currentStage.setWidth(SCENE_WIDTH);
        currentStage.setHeight(SCENE_HEIGHT);
        showScene(new Scene(stackPane));
    }

    /**
     * Loads the KickedFromGame scene.
     */
    public static void loadKickedFromGameScene() {
        StackPane stackPane = createStackPane("KickedFromGameView.fxml", false);
        currentStage.setWidth(SCENE_WIDTH);
        currentStage.setHeight(SCENE_HEIGHT);
        currentStage.centerOnScreen();
        showScene(new Scene(stackPane));
    }

    /**
     * Loads the Leaderboard scene.
     */
    public static void loadLeaderboardScene() {
        StackPane stackPane = createStackPane("LeaderboardView.fxml", false);
        currentStage.setWidth(SCENE_WIDTH);
        currentStage.setHeight(SCENE_HEIGHT);
        showScene(new Scene(stackPane));
    }

    /**
     * Shows the specified scene on the current stage.
     * @param scene the scene to be shown
     */
    private static void showScene(Scene scene) {
        currentStage.setOnCloseRequest(event -> System.exit(0));
        currentStage.setScene(scene);
        currentStage.sizeToScene();
        currentStage.show();
    }
}
