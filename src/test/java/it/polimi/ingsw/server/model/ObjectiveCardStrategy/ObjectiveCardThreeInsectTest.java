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

class ObjectiveCardThreeInsectTest {

    private static GameController controller;
    private static Game game;
    private GameField gameField;
    private Player player;
    private static ObjectiveCard objectiveCard;

    @BeforeAll
    static void setUpBeforeClass() {
        controller = new GameController(null,4,"test", false);
        game = controller.getGame();
        objectiveCard = new ObjectiveCard(98);
    }

    @BeforeEach
    void setUp() {
        player = new Player(game);
        gameField = new GameField(player);
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
    void calculatePointsCase1() {
        gameField.place(new StarterCard(84), true);
        try {
            gameField.place(new ResourceCard(31), true, 1,1);
            gameField.place(new ResourceCard(12), true, -1,-1);
        } catch (CannotPlaceCardException e) {
            throw new RuntimeException(e);
        }
        assertEquals(0,objectiveCard.getEarnedPoints(gameField));

    }

    @Test
    void calculatePointsCase2() {
        gameField.place(new StarterCard(84), false);
        try {
            gameField.place(new ResourceCard(36), true, 1,1);
            gameField.place(new ResourceCard(12), true, -1,-1);
            gameField.place(new ResourceCard(37), true, 2,0);
        } catch (CannotPlaceCardException e) {
            throw new RuntimeException(e);
        }
        assertEquals(2,objectiveCard.getEarnedPoints(gameField));
    }

    @Test
    void calculatePointsCase3() {
        gameField.place(new StarterCard(84), false);
        try {
            gameField.place(new ResourceCard(36), true, 1,1);
            gameField.place(new ResourceCard(12), true, 1,-1);
            gameField.place(new ResourceCard(37), true, 2,0);
            gameField.place(new ResourceCard(33), true, 0,2);
            gameField.place(new ResourceCard(33), true, -1,1);
        } catch (CannotPlaceCardException e) {
            throw new RuntimeException(e);
        }
        assertEquals(4,objectiveCard.getEarnedPoints(gameField));

    }

    @Test
    void calculatePointsCase4() {
        gameField.place(new StarterCard(84), true);
        try {
            gameField.place(new ResourceCard(36), true, 1,1);
            gameField.place(new ResourceCard(37), true, -1,-1);
            gameField.place(new ResourceCard(38), true, -2,-2);
            gameField.place(new ResourceCard(39), true, -3,-1);
        } catch (CannotPlaceCardException e) {
            throw new RuntimeException(e);
        }
        assertEquals(2,objectiveCard.getEarnedPoints(gameField));

    }
}