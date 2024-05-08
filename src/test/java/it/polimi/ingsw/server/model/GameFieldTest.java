package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.controller.GameController;
import it.polimi.ingsw.server.model.card.GoldCard;
import it.polimi.ingsw.server.model.card.ObjectiveCard;
import it.polimi.ingsw.server.model.card.ResourceCard;
import it.polimi.ingsw.server.model.card.StarterCard;
import it.polimi.ingsw.util.customexceptions.CannotPlaceCardException;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class GameFieldTest {
    private static GameController controller;
    private static Game game;
    private GameField gameField;
    private Player player;
    private static ObjectiveCard objectiveCard;

    @BeforeAll
    static void setUpBeforeClass() {
        controller = new GameController(null,4,"test");
        game = controller.getGame();
        objectiveCard = new ObjectiveCard(89);
    }

    @BeforeEach
    void setUp() {
        player = new Player();
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
    void lookAtCoordinates() {

        StarterCard card = new StarterCard(82);
        player.place(card, true);
        assertEquals(card,player.getGamefield().lookAtCoordinates(0,0));
    }

    @Test
    void lookAtCoordinatesEmptyPosition() {

        assertNull(player.getGamefield().lookAtCoordinates(0,0));
    }

    @Test
    void lookAtCoordinatesOutOfBounds() {
        assertNull(gameField.lookAtCoordinates(90,-110));
    }

    @Test
    void placeStarterCard() {
        StarterCard card = new StarterCard(82);
        gameField.place(card, true);
        assertEquals(card,gameField.lookAtCoordinates(0,0));
    }

    @Test
    void placeStarterCardFungiResource() {
        gameField.place(new StarterCard(82), true);
        assertEquals(1, gameField.getFungiCount());
    }

    @Test
    void placeMultipleCardsFungiResource() {

        gameField.place(new StarterCard(82), true);
        try {
            gameField.place(new ResourceCard(1),true,1,1);
            gameField.place(new ResourceCard(3),true,2,2);
        } catch (CannotPlaceCardException e) {
            throw new RuntimeException(e);
        }
        assertEquals(5, gameField.getFungiCount());
    }

    @Test
    void placeGoldCardWithEnoughResources() {
        gameField.place(new StarterCard(82), true);
        try {
            gameField.place(new ResourceCard(1),true,1,1);
            gameField.place(new ResourceCard(3),true,2,2);
        } catch (CannotPlaceCardException e) {
            throw new RuntimeException(e);
        }
        assertAll(()->gameField.place(new GoldCard(49),true,-1,1));
    }

    @Test
    void placeCheckExceptionOccupiedSpot() {
        gameField.place(new StarterCard(82), true);
        assertThrows(CannotPlaceCardException.class, () -> gameField.place(new ResourceCard(1),true,0,0));
    }

    @Test
    void placeCheckExceptionImpossiblePosition() {
        gameField.place(new StarterCard(82), true);
        assertThrows(CannotPlaceCardException.class, () -> gameField.place(new ResourceCard(1),true,1,0));
    }

    @Test
    void placeCheckExceptionNoValidNeighbours() {
        gameField.place(new StarterCard(82), true);
        assertThrows(CannotPlaceCardException.class, () -> gameField.place(new ResourceCard(1),true,5,5));
    }

    @Test
    void placeCheckExceptionGoldCardNotEnoughRequirements() {
        gameField.place(new StarterCard(82), true);
        assertThrows(CannotPlaceCardException.class, () -> gameField.place(new GoldCard(72),true,1,1));
    }
}