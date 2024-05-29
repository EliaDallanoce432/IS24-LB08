package it.polimi.ingsw.client.view.GUI.viewControllers;

import it.polimi.ingsw.client.controller.ClientController;
import it.polimi.ingsw.client.model.*;
import it.polimi.ingsw.client.view.StageManager;
import it.polimi.ingsw.client.view.ViewController;
import it.polimi.ingsw.client.view.utility.*;
import it.polimi.ingsw.util.supportclasses.ClientState;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import java.util.Objects;

/**
 * This class controls the GUI scene of the game board.
 * It initializes and updates various elements of the scene such as the game board, objectives,
 * scoreboards, the player's hand, and other UI components.
 */
public class GameFieldViewController extends ViewController {
    //FXML Nodes
    @FXML
    private Pane handPane;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private Label alertLabel;
    @FXML
    private Label specialAlertsLabel;
    @FXML
    private Label errorLabel;
    @FXML
    private Pane scoreBoardPane;
    @FXML
    private VBox scoreBoardVBox;
    @FXML
    private Pane decksPane;
    @FXML
    private HBox commonObjectivesPane;
    @FXML
    private HBox secretObjectivePane;
    @FXML
    private Button flipButton;
    @FXML
    private Button leaveGameButton;
    @FXML
    private Pane scoreTrackPane;
    @FXML
    private ImageView blueToken;
    @FXML
    private ImageView yellowToken;
    @FXML
    private ImageView redToken;
    @FXML
    private ImageView greenToken;
    @FXML
    private Pane resourcesPane;
    @FXML
    private Label plantResLabel;
    @FXML
    private Label animalResLabel;
    @FXML
    private Label fungiResLabel;
    @FXML
    private Label insectResLabel;
    @FXML
    private Label featherResLabel;
    @FXML
    private Label inkPotResLabel;
    @FXML
    private Label scrollResLabel;

    private HandAndBoardRepresentation handAndBoardRepresentation;
    private DecksRepresentation decksRepresentation;
    private ScoreBoardRepresentation scoreBoardRepresentation;
    private ObjectivesRepresentation objectivesRepresentation;
    private ScoreTrackRepresentation scoreTrackRepresentation;

    /**
     * Initializes the Panes, the relative representation utility classes and fills the panes with the pattern.
     * It also performs an additional update of all the UI elements, in case the observer updates the scene before it's
     * loaded.
     */
    @FXML
    private void initialize() {
        flipButton.setOnMouseEntered(mouseEvent -> flipButton.setCursor(Cursor.HAND));
        flipButton.setOnMouseExited(mouseEvent -> flipButton.setCursor(Cursor.DEFAULT));
        leaveGameButton.setOnMouseEntered(mouseEvent -> leaveGameButton.setCursor(Cursor.HAND));
        leaveGameButton.setOnMouseExited(mouseEvent -> leaveGameButton.setCursor(Cursor.DEFAULT));

        handAndBoardRepresentation = new HandAndBoardRepresentation(handPane,scrollPane);
        objectivesRepresentation = new ObjectivesRepresentation(commonObjectivesPane, secretObjectivePane);
        decksRepresentation = new DecksRepresentation(decksPane);
        scoreBoardRepresentation = new ScoreBoardRepresentation(scoreBoardVBox);
        scoreTrackRepresentation = new ScoreTrackRepresentation(scoreTrackPane,blueToken,redToken,greenToken,yellowToken);

        specialAlertsLabel.setVisible(false);
        errorLabel.setVisible(false);

        fillPaneWithPattern(resourcesPane);
        fillPaneWithPattern(scoreBoardPane);

        showMessage("Waiting for all players to choose the cards...");

        Platform.runLater(() -> {
            updateGameBoard();
            updateObjectives();
            updateHand();
            updateDecks();
            updateSceneStatus();
            updatePlayerInfo();
            updateScoreBoard();
        });
    }

    /**
     * Center the visual of game field (scrollPane).
     */
    @FXML
    private void centerVisual() {
        scrollPane.setHvalue(0.5);
        scrollPane.setVvalue(0.503);
    }

    /**
     * Updates the HandModel so that the cards get flipped.
     */
    @FXML
    private void flipCardsInHand() {
        HandModel.getInstance().flipCardsInHand();
    }

    /**
     * Loads the Main Menu and sends a "leaveGame" message.
     */
    @FXML
    private void leaveGame(){
        ClientController.getInstance().sendLeaveMessage();
        ClientStateModel.getInstance().setClientState(ClientState.LOBBY_STATE);
        ClientController.getInstance().resetModels();
        StageManager.loadWelcomeScene();
    }

    /**
     * Loads from the model the current placementHistory array and shows it.
     */
    @Override
    public void updateGameBoard(){
        Platform.runLater(()-> handAndBoardRepresentation.loadBoardFromPlacementHistory());
    }

    /**
     * Loads from the model the current common and secret objectives and shows them in the GUI.
     */
    @Override
    public void updateObjectives(){
        Platform.runLater(()->{
            objectivesRepresentation.loadCommonObjectives();
            objectivesRepresentation.loadSecretObjective();
        });
    }

    /**
     * Loads from the model the current top cards of the decks and shows them in the GUI.
     */
    @Override
    public void updateDecks(){
        Platform.runLater(()-> decksRepresentation.loadDecks());
    }

    /**
     * Loads from the model the current hand of the Player and shows it in the GUI.
     */
    @Override
    public void updateHand(){
        Platform.runLater(()-> handAndBoardRepresentation.loadHand());
    }

    /**
     * Loads form the model the updated scores and resources and shows them in the GUI.
     */
    @Override
    public void updateScoreBoard(){Platform.runLater(() -> {
        scoreBoardRepresentation.updateScores();
        scoreTrackRepresentation.updateTokenPosition();
        Platform.runLater(this::updateResources);
    });}

    /**
     * Loads from the ClientState Model the current state and updates the GUI accordingly.
     */
    @Override
    public void updateSceneStatus(){
        Platform.runLater(()->{
            errorLabel.setVisible(false);
            switch (ClientStateModel.getInstance().getClientState()){
                case NOT_PLAYING_STATE -> showMessage("Waiting for " + PlayerModel.getInstance().getTurnPlayer() + " to finish their turn...");
                case PLACING_STATE -> showMessage("It's your Turn, please place a card!");
                case DRAWING_STATE -> {
                    showMessage("Please Draw a Card from the decks!");
                    decksRepresentation.loadDecks();
                }
                case KICKED_STATE -> StageManager.loadKickedFromGameScene();
                case LOST_CONNECTION_STATE -> StageManager.loadLostConnectionScene();
                case LAST_ROUND_STATE -> showSpecialMessage(" It's the last turn! " + ClientStateModel.getInstance().getReason() + "!" );
                case END_GAME_STATE -> StageManager.loadLeaderboardScene();
                default -> {}
            }
        });
    }

    /**
     * Shows a message in the label.
     * @param message The message to be shown.
     */
    @Override
    public void showMessage(String message){
        Platform.runLater(()-> alertLabel.setText(message));
    }

    /**
     * Shows a message in the "special alerts" label.
     * @param message The message to be shown.
     */
    public void showSpecialMessage(String message){
        Platform.runLater(() -> {
            specialAlertsLabel.setText(message);
            specialAlertsLabel.setVisible(true);
        });
    }

    /**
     * Shows an error message in the error label.
     * @param message The message to be shown.
     */
    @Override
    public void showErrorMessage(String message){
        Platform.runLater(() -> {
            errorLabel.setText("⚠" + message);
            errorLabel.setVisible(true);
        });
    }

    /**
     * Fills a given pane with a textured tile.
     * @param pane The Pane to be filled.
     */
    private void fillPaneWithPattern (Pane pane){
        Image patternTile = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Images/background_tile.png")));

        BackgroundImage backgroundImage = new BackgroundImage(
                patternTile,
                BackgroundRepeat.REPEAT,   // Repeat horizontally
                BackgroundRepeat.REPEAT,   // Repeat vertically
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT
        );

        pane.setBackground(new Background(backgroundImage));
    }

    /**
     * Loads and shows the resources from the scoreBoardModel.
     */
    private void updateResources() {
        ScoreBoardModel scoreBoardModel = ScoreBoardModel.getInstance();

        Platform.runLater(()->{
            animalResLabel.setText(scoreBoardModel.getAnimalResourceCount() + "");
            fungiResLabel.setText(scoreBoardModel.getFungiResourceCount() + "");
            insectResLabel.setText(scoreBoardModel.getInsectResourceCount() + "");
            plantResLabel.setText(scoreBoardModel.getPlantResourceCount() + "");

            featherResLabel.setText(scoreBoardModel.getFeatherCount() + "");
            scrollResLabel.setText(scoreBoardModel.getScrollCount() + "");
            inkPotResLabel.setText(scoreBoardModel.getInkPotCount() + "");
        });
    }
}



