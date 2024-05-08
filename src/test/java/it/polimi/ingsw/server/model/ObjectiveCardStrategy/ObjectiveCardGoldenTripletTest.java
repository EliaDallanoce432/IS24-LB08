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

class ObjectiveCardGoldenTripletTest {

    private static GameController controller;
    private static Game game;
    private GameField gameField;
    private Player player;
    private static ObjectiveCard objectiveCard;

    @BeforeAll
    static void setUpBeforeClass() {
        controller = new GameController(null,4,"test");
        game = controller.getGame();
        objectiveCard = new ObjectiveCard(99);
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
    void calculatePointsCase1() {
        try {
            gameField.place(new ResourceCard(5), true, 1,1);
        } catch (CannotPlaceCardException e) {
            throw new RuntimeException(e);
        }
        assertEquals(0,objectiveCard.getEarnedPoints(gameField));
    }

    @Test
    void calculatePointsCase2() {
        try {
            gameField.place(new ResourceCard(6), true, 1,1);
        } catch (CannotPlaceCardException e) {
            throw new RuntimeException(e);
        }
        assertEquals(0,objectiveCard.getEarnedPoints(gameField));
    }
    @Test
    void calculatePointsCase3() {
        try {
            gameField.place(new ResourceCard(7), true, 1,1);
        } catch (CannotPlaceCardException e) {
            throw new RuntimeException(e);
        }
        assertEquals(0,objectiveCard.getEarnedPoints(gameField));
    }

    @Test
    void calculatePointsCase4() {
        try {
            gameField.place(new ResourceCard(5), true, 1,1);
            gameField.place(new ResourceCard(6), true, 2,0);
        } catch (CannotPlaceCardException e) {
            throw new RuntimeException(e);
        }
        assertEquals(0,objectiveCard.getEarnedPoints(gameField));
    }

    @Test
    void calculatePointsCase5() {
        try {
            gameField.place(new ResourceCard(5), true, 1,1);
            gameField.place(new ResourceCard(7), true, 2,0);
        } catch (CannotPlaceCardException e) {
            throw new RuntimeException(e);
        }
        assertEquals(0,objectiveCard.getEarnedPoints(gameField));
    }

    @Test
    void calculatePointsCase6() {
        try {
            gameField.place(new ResourceCard(6), true, 1,1);
            gameField.place(new ResourceCard(7), true, 2,0);
        } catch (CannotPlaceCardException e) {
            throw new RuntimeException(e);
        }
        assertEquals(0,objectiveCard.getEarnedPoints(gameField));
    }

    @Test
    void calculatePointsCase7() {
        try {
            gameField.place(new ResourceCard(6), true, 1,1);
            gameField.place(new ResourceCard(7), true, 2,0);
            gameField.place(new ResourceCard(5), true, 2,2);
        } catch (CannotPlaceCardException e) {
            throw new RuntimeException(e);
        }
        assertEquals(3,objectiveCard.getEarnedPoints(gameField));
    }

    @Test
    void calculatePointsCase8() {
        try {
            gameField.place(new ResourceCard(6), true, 1,1);
            gameField.place(new ResourceCard(7), true, 2,0);
            gameField.place(new ResourceCard(5), true, 2,2);
            gameField.place(new ResourceCard(5), true, 3,1);
        } catch (CannotPlaceCardException e) {
            throw new RuntimeException(e);
        }
        assertEquals(3,objectiveCard.getEarnedPoints(gameField));
    }

    @Test
    void calculatePointsCase9() {
        try {
            gameField.place(new ResourceCard(6), true, 1,1);
            gameField.place(new ResourceCard(7), true, 2,0);
            gameField.place(new ResourceCard(5), true, 2,2);
            gameField.place(new ResourceCard(5), true, -1,-1);
            gameField.place(new ResourceCard(6), true, -2,-2);
            gameField.place(new ResourceCard(7), true, -1,-3);
        } catch (CannotPlaceCardException e) {
            throw new RuntimeException(e);
        }
        assertEquals(6,objectiveCard.getEarnedPoints(gameField));
    }

}