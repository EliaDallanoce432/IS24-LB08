package it.polimi.ingsw.server.model.ObjectiveCardStrategy;

import it.polimi.ingsw.server.controller.GameController;
import it.polimi.ingsw.server.model.Game;
import it.polimi.ingsw.server.model.GameField;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.card.ObjectiveCard;
import it.polimi.ingsw.server.model.card.ResourceCard;
import it.polimi.ingsw.server.model.card.StarterCard;
import it.polimi.ingsw.util.customexceptions.CannotPlaceCardException;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class ObjectiveCardDiagonalFungiTest {

    private static GameController controller;
    private static Game game;
    private GameField gameField;
    private Player player;
    private static ObjectiveCard objectiveCard;

    @BeforeAll
    static void setUpBeforeClass() {
        controller = new GameController(null,4,"test");
        game = controller.getGame();
        objectiveCard = new ObjectiveCard(87);
    }

    @BeforeEach
    void setUp() {
        player = new Player(game);
        gameField = new GameField(player);
        gameField.place(new StarterCard(81),true);
    }
    @AfterEach
    void tearDown() {
        game.reinsertToken(player.getToken());
        gameField = null;
    }

    @AfterAll
    static void tearDownAfterClass() {
        controller = null;
        objectiveCard = null;
    }

    @Test
    void calculatePointsSimpleCase() {
        try {
            gameField.place(new ResourceCard(4), true, 1,1);
            gameField.place(new ResourceCard(4), true, 2,2);
            gameField.place(new ResourceCard(4), true, 3,3);
        } catch (CannotPlaceCardException e) {
            throw new RuntimeException(e);
        }
        assertEquals(2,objectiveCard.getEarnedPoints(gameField));
    }

    @Test
    void calculatePointsDiagonalWith4CardsCase() {

        try {
            gameField.place(new ResourceCard(4), true, 1,1);
            gameField.place(new ResourceCard(4), true, 2,2);
            gameField.place(new ResourceCard(4), true, 3,3);
            gameField.place(new ResourceCard(4), true, 4,4);
        } catch (CannotPlaceCardException e) {
            throw new RuntimeException(e);
        }
        assertEquals(2,objectiveCard.getEarnedPoints(gameField));
    }

    @Test
    void calculatePointsNoTripletsCase() {
        try {
            gameField.place(new ResourceCard(4), true, 1,1);
            gameField.place(new ResourceCard(4), true, 2,2);
        } catch (CannotPlaceCardException e) {
            throw new RuntimeException(e);
        }
        assertEquals(0,objectiveCard.getEarnedPoints(gameField));
    }

    @Test
    void calculatePointsDoubleTripletCase() {
        try {
            gameField.place(new ResourceCard(4), true, 1,1);
            gameField.place(new ResourceCard(4), true, 2,2);
            gameField.place(new ResourceCard(4), true, 3,3);
            gameField.place(new ResourceCard(4), true, 2,4);
            gameField.place(new ResourceCard(4), true, 3,5);
            gameField.place(new ResourceCard(4), true, 4,6);
        } catch (CannotPlaceCardException e) {
            throw new RuntimeException(e);
        }
        assertEquals(4,objectiveCard.getEarnedPoints(gameField));
    }
}