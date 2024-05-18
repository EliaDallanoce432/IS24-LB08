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

class ObjectiveCardJPatternPPITest {

    private static GameController controller;
    private static Game game;
    private GameField gameField;
    private Player player;
    private static ObjectiveCard objectiveCard;

    @BeforeAll
    static void setUpBeforeClass() {
        controller = new GameController(null,4,"test");
        game = controller.getGame();
        objectiveCard = new ObjectiveCard(92);
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
            gameField.place(new ResourceCard(19), true, 1,1);
            gameField.place(new ResourceCard(20), true, 1,-1);
            gameField.place(new ResourceCard(32), true, 0,-2);
        } catch (CannotPlaceCardException e) {
            throw new RuntimeException(e);
        }
        assertEquals(3,objectiveCard.getEarnedPoints(gameField));
    }

    @Test
    void calculatePointsSimpleCaseWithNegativeCoordinates() {
        try {
            gameField.place(new ResourceCard(19), false, -1,-1);
            gameField.place(new ResourceCard(20), false, -1,1);
            gameField.place(new ResourceCard(32), false, -2,-2);
        } catch (CannotPlaceCardException e) {
            throw new RuntimeException(e);
        }
        assertEquals(3,objectiveCard.getEarnedPoints(gameField));
    }

    @Test
    void calculatePointsNoPattern() {
        try {
            gameField.place(new ResourceCard(19), true, 1,1);
            gameField.place(new ResourceCard(20), true, 1,-1);
            gameField.place(new ResourceCard(1), true, 0,-2);
        } catch (CannotPlaceCardException e) {
            throw new RuntimeException(e);
        }
        assertEquals(0,objectiveCard.getEarnedPoints(gameField));
    }

    @Test
    void calculatePointsConcatenatedCase() {
        try {
            gameField.place(new ResourceCard(19), true, 1,1);
            gameField.place(new ResourceCard(20), true, 1,-1);
            gameField.place(new ResourceCard(32), true, 0,-2);
            gameField.place(new ResourceCard(17), true, 1,-3);
            gameField.place(new ResourceCard(36), true, 0,-4);

        } catch (CannotPlaceCardException e) {
            throw new RuntimeException(e);
        }
        assertEquals(3,objectiveCard.getEarnedPoints(gameField));
    }

    @Test
    void calculatePointsDoubleVerticalJPatternCase() {
        try {
            gameField.place(new ResourceCard(19), true, 1,1);
            gameField.place(new ResourceCard(20), true, 1,-1);
            gameField.place(new ResourceCard(32), true, 0,-2);
            gameField.place(new ResourceCard(17), true, 1,-3);
            gameField.place(new ResourceCard(36), true, 0,-4);
            gameField.place(new ResourceCard(15), true, 1,-5);
            gameField.place(new ResourceCard(32), true, 0,-6);

        } catch (CannotPlaceCardException e) {
            throw new RuntimeException(e);
        }
        assertEquals(6,objectiveCard.getEarnedPoints(gameField));
    }

    @Test
    void calculatePointsDoubleHorizontalJPatternCase() {
        try {
            gameField.place(new ResourceCard(19), true, 1,1);
            gameField.place(new ResourceCard(20), true, 1,-1);
            gameField.place(new ResourceCard(32), true, 0,-2);
            gameField.place(new ResourceCard(18), true, 2,0);
            gameField.place(new ResourceCard(17), true, 2,-2);
            gameField.place(new ResourceCard(33), true, 1,-3);

        } catch (CannotPlaceCardException e) {
            throw new RuntimeException(e);
        }
        assertEquals(6,objectiveCard.getEarnedPoints(gameField));
    }
}