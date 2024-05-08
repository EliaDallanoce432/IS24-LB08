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

class ObjectiveCardJPatternAAFTest {

    private static GameController controller;
    private static Game game;
    private GameField gameField;
    private Player player;
    private static ObjectiveCard objectiveCard;

    @BeforeAll
    static void setUpBeforeClass() {
        controller = new GameController(null,4,"test");
        game = controller.getGame();
        objectiveCard = new ObjectiveCard(93);
    }

    @BeforeEach
    void setUp() {
        player = new Player();
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
            gameField.place(new ResourceCard(28), true, 1,1);
            gameField.place(new ResourceCard(29), true, 1,-1);
            gameField.place(new ResourceCard(8), true, 2,2);
        } catch (CannotPlaceCardException e) {
            throw new RuntimeException(e);
        }
        assertEquals(3,objectiveCard.getEarnedPoints(gameField));
    }

    @Test
    void calculatePointsSimpleCaseWithNegativeCoordinates() {
        try {
            gameField.place(new ResourceCard(1), true, -1,-1);
            gameField.place(new ResourceCard(22), true, -2,-2);
            gameField.place(new ResourceCard(31), true, -1,-3);
            gameField.place(new ResourceCard(30), true, -2,-4);
        } catch (CannotPlaceCardException e) {
            throw new RuntimeException(e);
        }
        assertEquals(3,objectiveCard.getEarnedPoints(gameField));
    }

    @Test
    void calculatePointsNoPattern() {
        try {
            gameField.place(new ResourceCard(28), true, 1,1);
            gameField.place(new ResourceCard(29), true, 1,-1);
            gameField.place(new ResourceCard(8), true, 2,-2);
        } catch (CannotPlaceCardException e) {
            throw new RuntimeException(e);
        }
        assertEquals(0,objectiveCard.getEarnedPoints(gameField));
    }

    @Test
    void calculatePointsConcatenatedCase() {
        try {
            gameField.place(new ResourceCard(28), true, 1,1);
            gameField.place(new ResourceCard(29), true, 1,-1);
            gameField.place(new ResourceCard(8), true, 2,2);
            gameField.place(new ResourceCard(30), true, 1,3);
            gameField.place(new ResourceCard(2), true, 2,4);
        } catch (CannotPlaceCardException e) {
            throw new RuntimeException(e);
        }
        assertEquals(3,objectiveCard.getEarnedPoints(gameField));
    }

    @Test
    void calculatePointsDoubleVerticalJPatternCase() {
        try {
            gameField.place(new ResourceCard(28), true, 1,1);
            gameField.place(new ResourceCard(29), true, 1,-1);
            gameField.place(new ResourceCard(8), true, 2,2);
            gameField.place(new ResourceCard(30), true, 1,3);
            gameField.place(new ResourceCard(2), true, 2,4);
            gameField.place(new ResourceCard(24), true, 1,5);
            gameField.place(new ResourceCard(3), true, 2,6);
        } catch (CannotPlaceCardException e) {
            throw new RuntimeException(e);
        }
        assertEquals(6,objectiveCard.getEarnedPoints(gameField));
    }

    @Test
    void calculatePointsDoubleHorizontalJPatternCase() {
        try {
            gameField.place(new ResourceCard(28), true, 1,1);
            gameField.place(new ResourceCard(29), true, 1,-1);
            gameField.place(new ResourceCard(8), true, 2,2);
            gameField.place(new ResourceCard(34), true, 2,0);
            gameField.place(new ResourceCard(26), true, 3,1);
            gameField.place(new ResourceCard(25), true, 3,-1);
            gameField.place(new ResourceCard(4), true, 4,2);
        } catch (CannotPlaceCardException e) {
            throw new RuntimeException(e);
        }
        assertEquals(6,objectiveCard.getEarnedPoints(gameField));
    }
}